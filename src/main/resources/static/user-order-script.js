let cart = [];
let totalPrice = 0;

function loadMenus() {
    const menuList = document.getElementById('menu-list');
    menuList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载菜单...';
    menuList.appendChild(loadingElement);
    fetch('http://localhost:8080/menus/all')
        .then(response => response.json())
        .then(menus => {
            menuList.innerHTML = '';
            menus.forEach(menu => {
                const menuItem = document.createElement('div');
                menuItem.innerHTML = `
                <h3>${menu.dishName}</h3>
                <p>价格：￥${menu.price}</p>
                <p>库存：${menu.stock}</p>
                <button ${menu.isAvailable && menu.stock > 0? '' : 'disabled'} onclick="addToCart(${menu.menuID}, ${menu.price})">加入购物车</button>
            `;
                menuList.appendChild(menuItem);
            });
        })
        .catch(error => console.error('获取菜单出错：', error));
}

function addToCart(menuId, price) {
    const quantity = prompt('请输入数量：', 1);
    if (quantity &&!isNaN(quantity) && parseInt(quantity) > 0) {
        cart.push({ menuId, quantity, price });
        updateCart();
    }
}

function updateCart() {
    const cartItems = document.getElementById('cart-items');
    const totalPriceElement = document.getElementById('total-price');
    cartItems.innerHTML = '';
    totalPrice = 0;
    cart.forEach(item => {
        const listItem = document.createElement('li');
        listItem.textContent = `${getMenuItemName(item.menuId)} x ${item.quantity} = ￥${item.price * item.quantity}`;
        cartItems.appendChild(listItem);
        totalPrice += item.price * item.quantity;
    });
    totalPriceElement.textContent = `总价：￥${totalPrice.toFixed(2)}`;
}

function getMenuItemName(menuId) {
    // 简单示例，实际可根据菜单数据进行优化
    return `菜品${menuId}`;
}

function checkout() {
    if (cart.length === 0) {
        alert('购物车为空，请添加菜品后再下单');
        return;
    }
    const orderData = {
        customerId: 1, // 这里假设用户 ID 为 1，实际可修改
        menuIds: cart.map(item => item.menuId),
        quantities: cart.map(item => item.quantity)
    };
    fetch('http://localhost:8080/orders/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderData)
    })
        .then(response => response.json())
        .then(orderId => {
            if (orderId) {
                alert(`下单成功，订单 ID：${orderId}`);
                cart.length = 0;
                updateCart();
            } else {
                alert('下单失败，请重试');
            }
        })
        .catch(error => console.error('下单出错：', error));
}

// 页面加载时加载菜单
window.onload = loadMenus;