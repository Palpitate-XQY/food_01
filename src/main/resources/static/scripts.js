let currentUser = null;
let cart = [];
let totalPrice = 0;
let menuCache = [];

function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => section.style.display = 'none');
    const targetSection = document.getElementById(sectionId);
    targetSection.style.display = 'block';
    if (sectionId ==='menus') {
        loadMenus();
    } else if (sectionId === 'orders') {
        loadOrders();
    }
}

document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;
    fetch('http://localhost:8080/customers/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                currentUser = data.user;
                document.getElementById('login').style.display = 'none';
                document.getElementById('menusLink').style.display = 'block';
                document.getElementById('ordersLink').style.display = 'block';
                showSection('menus');
            } else {
                alert(data.message);
            }
        })
        .catch(error => console.error('登录出错：', error));
});

document.getElementById('registerForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const username = document.getElementById('register-username').value;
    const password = document.getElementById('register-password').value;
    const phone = document.getElementById('register-phone').value;
    fetch('http://localhost:8080/customers/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password, phone })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                currentUser = data.user;
                document.getElementById('register').style.display = 'none';
                document.getElementById('menusLink').style.display = 'block';
                document.getElementById('ordersLink').style.display = 'block';
                showSection('menus');
            } else {
                alert(data.message);
            }
        })
        .catch(error => console.error('注册出错：', error));
});

function loadMenus() {
    const menuList = document.getElementById('menu-list');
    menuList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载菜单...';
    menuList.appendChild(loadingElement);
    fetch('http://localhost:8080/menus/all')
        .then(response => response.json())
        .then(menus => {
            menuCache = menus;
            menuList.innerHTML = '';
            menus.forEach(menu => {
                const menuItem = document.createElement('div');
                menuItem.className ='menu-item';
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
    const menu = menuCache.find(menu => menu.menuID === menuId);
    return menu? menu.dishName : '未知菜品';
}

function checkout() {
    if (cart.length === 0) {
        alert('购物车为空，请添加菜品后再下单');
        return;
    }
    const orderData = {
        customerId: currentUser.customerID,
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

function loadOrders() {
    const orderList = document.getElementById('order-list');
    orderList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载订单...';
    orderList.appendChild(loadingElement);
    fetch(`http://localhost:8080/orders/customer/${currentUser.customerID}`)
        .then(response => response.json())
        .then(orders => {
            orderList.innerHTML = '';
            orders.forEach(order => {
                const listItem = document.createElement('li');
                listItem.textContent = `订单 ID：${order.orderID}，总价：￥${order.totalPrice}，下单时间：${new Date(order.orderTime).toLocaleString()}`;
                orderList.appendChild(listItem);
            });
        })
        .catch(error => console.error('获取订单出错：', error));
}