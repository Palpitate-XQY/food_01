function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => section.style.display = 'none');
    const targetSection = document.getElementById(sectionId);
    targetSection.style.display = 'block';
    if (sectionId ==='manage-menus') {
        loadMenuCategories();
        loadMenus();
    } else if (sectionId ==='manage-tables') {
        loadTables();
        loadReservations();
        loadQueue();
    } else if (sectionId ==='manage-orders') {
        loadOrders();
    } else if (sectionId ==='manage-suppliers') {
        loadSuppliers();
    } else if (sectionId ==='manage-inventory') {
        loadIngredients();
        loadPurchases();
    }
}

// 管理菜单相关函数
function loadMenuCategories() {
    const categoryList = document.getElementById('menu-category-list');
    categoryList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载菜单分类...';
    categoryList.appendChild(loadingElement);
    fetch('http://localhost:8080/menu-categories/all')
        .then(response => response.json())
        .then(categories => {
            categoryList.innerHTML = '';
            categories.forEach(category => {
                const categoryItem = document.createElement('div');
                categoryItem.innerHTML = `
                <h3>${category.categoryName}</h3>
                <button onclick="updateMenuCategory(${category.categoryID})">更新</button>
                <button onclick="deleteMenuCategory(${category.categoryID})">删除</button>
            `;
                categoryList.appendChild(categoryItem);
            });
        })
        .catch(error => console.error('获取菜单分类出错：', error));
}

function addMenuCategory() {
    const categoryName = document.getElementById('category-name').value;
    fetch('http://localhost:8080/menu-categories/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ categoryName })
    })
        .then(response => response.json())
        .then(() => {
            loadMenuCategories();
            document.getElementById('category-name').value = '';
        })
        .catch(error => console.error('添加菜单分类出错：', error));
}

function loadMenus() {
    const menuList = document.getElementById('menu-list');
    menuList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载菜品...';
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
                <p>是否可用：${menu.isAvailable? '是' : '否'}</p>
                <button onclick="updateMenu(${menu.menuID})">更新</button>
                <button onclick="deleteMenu(${menu.menuID})">删除</button>
            `;
                menuList.appendChild(menuItem);
            });
        })
        .catch(error => console.error('获取菜品出错：', error));
}

function addMenu() {
    const dishName = document.getElementById('dish-name').value;
    const price = parseFloat(document.getElementById('price').value);
    const stock = parseInt(document.getElementById('stock').value);
    const isAvailable = document.getElementById('availability').checked;
    const categoryId = parseInt(document.getElementById('category-id').value);
    fetch('http://localhost:8080/menus/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ dishName, price, stock, isAvailable, categoryId })
    })
        .then(response => response.json())
        .then(() => {
            loadMenus();
            document.getElementById('dish-name').value = '';
            document.getElementById('price').value = '';
            document.getElementById('stock').value = '';
            document.getElementById('availability').checked = false;
            document.getElementById('category-id').value = '';
        })
        .catch(error => console.error('添加菜品出错：', error));
}

function updateMenu(menuId) {
    const newPrice = prompt('请输入新价格：');
    const newStock = prompt('请输入新库存：');
    const newAvailability = confirm('是否可用？');
    const newCategoryId = parseInt(prompt('请输入新分类 ID：'));
    if (newPrice!== null && newStock!== null && newCategoryId!== null) {
        fetch(`http://localhost:8080/menus/update/${menuId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ price: parseFloat(newPrice), stock: parseInt(newStock), isAvailable: newAvailability, categoryId: newCategoryId })
        })
            .then(response => response.json())
            .then(() => loadMenus())
            .catch(error => console.error('更新菜品出错：', error));
    }
}

function deleteMenu(menuId) {
    if (confirm('确认删除该菜品？')) {
        fetch(`http://localhost:8080/menus/delete/${menuId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadMenus())
            .catch(error => console.error('删除菜品出错：', error));
    }
}

// 管理桌位相关函数
function loadTables() {
    const tableList = document.getElementById('table-list');
    tableList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载桌位信息...';
    tableList.appendChild(loadingElement);
    fetch('http://localhost:8080/tables/all')
        .then(response => response.json())
        .then(tables => {
            tableList.innerHTML = '';
            tables.forEach(table => {
                const tableItem = document.createElement('div');
                tableItem.innerHTML = `
                <h3>桌位 ID：${table.tableID}</h3>
                <p>容量：${table.tableCapacity} 人</p>
                <p>是否可用：${table.isAvailable? '是' : '否'}</p>
                <button onclick="updateTable(${table.tableID})">更新</button>
                <button onclick="deleteTable(${table.tableID})">删除</button>
            `;
                tableList.appendChild(tableItem);
            });
        })
        .catch(error => console.error('获取桌位信息出错：', error));
}

function updateTable(tableId) {
    const newCapacity = parseInt(prompt('请输入新容量：'));
    const newAvailability = confirm('是否可用？');
    if (newCapacity!== null) {
        fetch(`http://localhost:8080/tables/update/${tableId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ tableCapacity: newCapacity, isAvailable: newAvailability })
        })
            .then(response => response.json())
            .then(() => loadTables())
            .catch(error => console.error('更新桌位出错：', error));
    }
}

function deleteTable(tableId) {
    if (confirm('确认删除该桌位？')) {
        fetch(`http://localhost:8080/tables/delete/${tableId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadTables())
            .catch(error => console.error('删除桌位出错：', error));
    }
}

function loadReservations() {
    const reservationList = document.getElementById('reservation-list');
    reservationList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载预订记录...';
    reservationList.appendChild(loadingElement);
    fetch('http://localhost:8080/reservations/all')
        .then(response => response.json())
        .then(reservations => {
            reservationList.innerHTML = '';
            reservations.forEach(reservation => {
                const reservationItem = document.createElement('div');
                reservationItem.innerHTML = `
                <h3>预订 ID：${reservation.reservationID}</h3>
                <p>顾客姓名：${reservation.customerName}</p>
                <p>联系电话：${reservation.phoneNumber}</p>
                <p>预订时间：${new Date(reservation.reservationTime).toLocaleString()}</p>
                <p>桌位 ID：${reservation.tableID}</p>
                <button onclick="updateReservation(${reservation.reservationID})">更新</button>
                <button onclick="deleteReservation(${reservation.reservationID})">删除</button>
            `;
                reservationList.appendChild(reservationItem);
            });
        })
        .catch(error => console.error('获取预订记录出错：', error));
}

function addReservation() {
    const customerName = document.getElementById('customer-name').value;
    const phoneNumber = document.getElementById('phone-number').value;
    const reservationTime = document.getElementById('reservation-time').value;
    const tableId = parseInt(document.getElementById('table-id').value);
    fetch('http://localhost:8080/reservations/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ customerName, phoneNumber, reservationTime, tableId })
    })
        .then(response => response.json())
        .then(() => {
            loadReservations();
            document.getElementById('customer-name').value = '';
            document.getElementById('phone-number').value = '';
            document.getElementById('reservation-time').value = '';
            document.getElementById('table-id').value = '';
        })
        .catch(error => console.error('添加预订记录出错：', error));
}

function updateReservation(reservationId) {
    const newCustomerName = prompt('请输入新顾客姓名：');
    const newPhoneNumber = prompt('请输入新联系电话：');
    const newReservationTime = prompt('请输入新预订时间：');
    const newTableId = parseInt(prompt('请输入新桌位 ID：'));
    if (newCustomerName!== null && newPhoneNumber!== null && newReservationTime!== null && newTableId!== null) {
        fetch(`http://localhost:8080/reservations/update/${reservationId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ customerName: newCustomerName, phoneNumber: newPhoneNumber, reservationTime: newReservationTime, tableId: newTableId })
        })
            .then(response => response.json())
            .then(() => loadReservations())
            .catch(error => console.error('更新预订记录出错：', error));
    }
}

function deleteReservation(reservationId) {
    if (confirm('确认删除该预订记录？')) {
        fetch(`http://localhost:8080/reservations/delete/${reservationId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadReservations())
            .catch(error => console.error('删除预订记录出错：', error));
    }
}

function loadQueue() {
    const queueList = document.getElementById('queue-list');
    queueList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载排号信息...';
    queueList.appendChild(loadingElement);
    fetch('http://localhost:8080/queue/all')
        .then(response => response.json())
        .then(queue => {
            queueList.innerHTML = '';
            queue.forEach(item => {
                const queueItem = document.createElement('div');
                queueItem.innerHTML = `
                <h3>排号：${item.queueNumber}</h3>
                <p>顾客姓名：${item.customerName}</p>
                <p>联系电话：${item.phoneNumber}</p>
                <button onclick="updateQueue(${item.queueNumber})">更新</button>
                <button onclick="deleteQueue(${item.queueNumber})">删除</button>
            `;
                queueList.appendChild(queueItem);
            });
        })
        .catch(error => console.error('获取排号信息出错：', error));
}

function addQueue() {
    const customerName = document.getElementById('queue-customer-name').value;
    const phoneNumber = document.getElementById('queue-phone-number').value;
    fetch('http://localhost:8080/queue/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ customerName, phoneNumber })
    })
        .then(response => response.json())
        .then(() => {
            loadQueue();
            document.getElementById('queue-customer-name').value = '';
            document.getElementById('queue-phone-number').value = '';
        })
        .catch(error => console.error('添加排号出错：', error));
}

function updateQueue(queueNumber) {
    const newCustomerName = prompt('请输入新顾客姓名：');
    const newPhoneNumber = prompt('请输入新联系电话：');
    if (newCustomerName!== null && newPhoneNumber!== null) {
        fetch(`http://localhost:8080/queue/update/${queueNumber}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ customerName: newCustomerName, phoneNumber: newPhoneNumber })
        })
            .then(response => response.json())
            .then(() => loadQueue())
            .catch(error => console.error('更新排号出错：', error));
    }
}

function deleteQueue(queueNumber) {
    if (confirm('确认删除该排号？')) {
        fetch(`http://localhost:8080/queue/delete/${queueNumber}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadQueue())
            .catch(error => console.error('删除排号出错：', error));
    }
}

// 管理订单相关函数
function loadOrders() {
    const orderList = document.getElementById('order-list');
    orderList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载订单信息...';
    orderList.appendChild(loadingElement);
    fetch('http://localhost:8080/orders/all')
        .then(response => response.json())
        .then(orders => {
            orderList.innerHTML = '';
            orders.forEach(order => {
                const orderItem = document.createElement('div');
                orderItem.innerHTML = `
                <h3>订单 ID：${order.orderID}</h3>
                <p>总价：￥${order.totalPrice}</p>
                <p>下单时间：${new Date(order.orderTime).toLocaleString()}</p>
                <p>顾客 ID：${order.customerID}</p>
                <p>支付状态：${order.paymentStatus}</p>
                <p>订单状态：${order.orderStatus}</p>
                <button onclick="updateOrder(${order.orderID})">更新</button>
                <button onclick="deleteOrder(${order.orderID})">删除</button>
            `;
                orderList.appendChild(orderItem);
            });
        })
        .catch(error => console.error('获取订单出错：', error));
}

function processOrder() {
    const orderId = parseInt(document.getElementById('order-id').value);
    const paymentStatus = document.getElementById('payment-status').value;
    const orderStatus = document.getElementById('order-status').value;
    fetch(`http://localhost:8080/orders/update/${orderId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ paymentStatus, orderStatus })
    })
        .then(response => response.json())
        .then(() => loadOrders())
        .catch(error => console.error('处理订单出错：', error));
}

function generateInvoice() {
    const orderId = parseInt(prompt('请输入订单 ID：'));
    if (orderId!== null) {
        fetch(`http://localhost:8080/invoices/generate/${orderId}`, { method: 'POST' })
            .then(response => response.json())
            .then(() => alert('发票已开具'))
            .catch(error => console.error('开具发票出错：', error));
    }
}

// 供应商管理相关函数
function loadSuppliers() {
    const supplierList = document.getElementById('supplier-list');
    supplierList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载供应商信息...';
    supplierList.appendChild(loadingElement);
    fetch('http://localhost:8080/suppliers/all')
        .then(response => response.json())
        .then(suppliers => {
            supplierList.innerHTML = '';
            suppliers.forEach(supplier => {
                const supplierItem = document.createElement('div');
                supplierItem.innerHTML = `
                <h3>供应商名称：${supplier.supplierName}</h3>
                <p>联系人：${supplier.contactPerson}</p>
                <p>联系电话：${supplier.contactPhone}</p>
                <button onclick="updateSupplier(${supplier.supplierID})">更新</button>
                <button onclick="deleteSupplier(${supplier.supplierID})">删除</button>
            `;
                supplierList.appendChild(supplierItem);
            });
        })
        .catch(error => console.error('获取供应商信息出错：', error));
}

function addSupplier() {
    const supplierName = document.getElementById('supplier-name').value;
    const contactPerson = document.getElementById('contact-person').value;
    const contactPhone = document.getElementById('contact-phone').value;
    fetch('http://localhost:8080/suppliers/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ supplierName, contactPerson, contactPhone })
    })
        .then(response => response.json())
        .then(() => {
            loadSuppliers();
            document.getElementById('supplier-name').value = '';
            document.getElementById('contact-person').value = '';
            document.getElementById('contact-phone').value = '';
        })
        .catch(error => console.error('添加供应商出错：', error));
}


function updateSupplier(supplierId) {
    const newSupplierName = prompt('请输入新供应商名称：');
    const newContactPerson = prompt('请输入新联系人：');
    const newContactPhone = prompt('请输入新联系电话：');
    if (newSupplierName!== null && newContactPerson!== null && newContactPhone!== null) {
        fetch(`http://localhost:8080/suppliers/update/${supplierId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ supplierName: newSupplierName, contactPerson: newContactPerson, contactPhone: newContactPhone })
        })
            .then(response => response.json())
            .then(() => loadSuppliers())
            .catch(error => console.error('更新供应商出错：', error));
    }
}


function deleteSupplier(supplierId) {
    if (confirm('确认删除该供应商？')) {
        fetch(`http://localhost:8080/suppliers/delete/${supplierId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadSuppliers())
            .catch(error => console.error('删除供应商出错：', error));
    }
}


// 库存管理相关函数
function loadIngredients() {
    const ingredientList = document.getElementById('ingredient-list');
    ingredientList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载食材信息...';
    ingredientList.appendChild(loadingElement);
    fetch('http://localhost:8080/ingredients/all')
        .then(response => response.json())
        .then(ingredients => {
            ingredientList.innerHTML = '';
            ingredients.forEach(ingredient => {
                const ingredientItem = document.createElement('div');
                ingredientItem.innerHTML = `
                <h3>食材名称：${ingredient.ingredientName}</h3>
                <p>库存数量：${ingredient.ingredientStock}</p>
                <p>低库存预警阈值：${ingredient.lowStockThreshold}</p>
                <button onclick="updateIngredient(${ingredient.ingredientID})">更新</button>
                <button onclick="deleteIngredient(${ingredient.ingredientID})">删除</button>
            `;
                ingredientList.appendChild(ingredientItem);
            });
        })
        .catch(error => console.error('获取食材信息出错：', error));
}


function addIngredient() {
    const ingredientName = document.getElementById('ingredient-name').value;
    const ingredientStock = parseInt(document.getElementById('ingredient-stock').value);
    const lowStockThreshold = parseInt(document.getElementById('low-stock-threshold').value);
    fetch('http://localhost:8080/ingredients/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ ingredientName, ingredientStock, lowStockThreshold })
    })
        .then(response => response.json())
        .then(() => {
            loadIngredients();
            document.getElementById('ingredient-name').value = '';
            document.getElementById('ingredient-stock').value = '';
            document.getElementById('low-stock-threshold').value = '';
        })
        .catch(error => console.error('添加食材出错：', error));
}


function updateIngredient(ingredientId) {
    const newIngredientName = prompt('请输入新食材名称：');
    const newIngredientStock = prompt('请输入新库存数量：');
    const newLowStockThreshold = prompt('请输入新低库存预警阈值：');
    if (newIngredientName!== null && newIngredientStock!== null && newLowStockThreshold!== null) {
        fetch(`http://localhost:8080/ingredients/update/${ingredientId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ingredientName: newIngredientName, ingredientStock: parseInt(newIngredientStock), lowStockThreshold: parseInt(newLowStockThreshold) })
        })
            .then(response => response.json())
            .then(() => loadIngredients())
            .catch(error => console.error('更新食材出错：', error));
    }
}


function deleteIngredient(ingredientId) {
    if (confirm('确认删除该食材？')) {
        fetch(`http://localhost:8080/ingredients/delete/${ingredientId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadIngredients())
            .catch(error => console.error('删除食材出错：', error));
    }
}


function loadPurchases() {
    const purchaseList = document.getElementById('purchase-list');
    purchaseList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载采购记录...';
    purchaseList.appendChild(loadingElement);
    fetch('http://localhost:8080/purchases/all')
        .then(response => response.json())
        .then(purchases => {
            purchaseList.innerHTML = '';
            purchases.forEach(purchase => {
                const purchaseItem = document.createElement('div');
                purchaseItem.innerHTML = `
                <h3>采购 ID：${purchase.purchaseID}</h3>
                <p>食材 ID：${purchase.ingredientID}</p>
                <p>采购数量：${purchase.purchaseQuantity}</p>
                <p>供应商 ID：${purchase.supplierID}</p>
                <button onclick="updatePurchase(${purchase.purchaseID})">更新</button>
                <button onclick="deletePurchase(${purchase.purchaseID})">删除</button>
            `;
                purchaseList.appendChild(purchaseItem);
            });
        })
        .catch(error => console.error('获取采购记录出错：', error));
}


function addPurchase() {
    const purchaseIngredientId = parseInt(document.getElementById('purchase-ingredient-id').value);
    const purchaseQuantity = parseInt(document.getElementById('purchase-quantity').value);
    const purchaseSupplierId = parseInt(document.getElementById('purchase-supplier-id').value);
    fetch('http://localhost:8080/purchases/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ ingredientId: purchaseIngredientId, quantity: purchaseQuantity, supplierId: purchaseSupplierId })
    })
        .then(response => response.json())
        .then(() => {
            loadPurchases();
            document.getElementById('purchase-ingredient-id').value = '';
            document.getElementById('purchase-quantity').value = '';
            document.getElementById('purchase-supplier-id').value = '';
        })
        .catch(error => console.error('添加采购记录出错：', error));
}


function updatePurchase(purchaseId) {
    const newPurchaseIngredientId = parseInt(prompt('请输入新食材 ID：'));
    const newPurchaseQuantity = parseInt(prompt('请输入新采购数量：'));
    const newPurchaseSupplierId = parseInt(prompt('请输入新供应商 ID：'));
    if (newPurchaseIngredientId!== null && newPurchaseQuantity!== null && newPurchaseSupplierId!== null) {
        fetch(`http://localhost:8080/purchases/update/${purchaseId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ingredientId: newPurchaseIngredientId, quantity: newPurchaseQuantity, supplierId: newPurchaseSupplierId })
        })
            .then(response => response.json())
            .then(() => loadPurchases())
            .catch(error => console.error('更新采购记录出错：', error));
    }
}


function deletePurchase(purchaseId) {
    if (confirm('确认删除该采购记录？')) {
        fetch(`http://localhost:8080/purchases/delete/${purchaseId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadPurchases())
            .catch(error => console.error('删除采购记录出错：', error));
    }
}
function updateIngredient(ingredientId) {
    const newIngredientName = prompt('请输入新食材名称：');
    const newIngredientStock = prompt('请输入新库存数量：');
    const newLowStockThreshold = prompt('请输入新低库存预警阈值：');
    if (newIngredientName!== null && newIngredientStock!== null && newLowStockThreshold!== null) {
        fetch(`http://localhost:8080/ingredients/update/${ingredientId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ingredientName: newIngredientName, ingredientStock: parseInt(newIngredientStock), lowStockThreshold: parseInt(newLowStockThreshold) })
        })
            .then(response => response.json())
            .then(() => loadIngredients())
            .catch(error => console.error('更新食材出错：', error));
    }
}


function deleteIngredient(ingredientId) {
    if (confirm('确认删除该食材？')) {
        fetch(`http://localhost:8080/ingredients/delete/${ingredientId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadIngredients())
            .catch(error => console.error('删除食材出错：', error));
    }
}


function loadPurchases() {
    const purchaseList = document.getElementById('purchase-list');
    purchaseList.innerHTML = '';
    const loadingElement = document.createElement('p');
    loadingElement.textContent = '正在加载采购记录...';
    purchaseList.appendChild(loadingElement);
    fetch('http://localhost:8080/purchases/all')
        .then(response => response.json())
        .then(purchases => {
            purchaseList.innerHTML = '';
            purchases.forEach(purchase => {
                const purchaseItem = document.createElement('div');
                purchaseItem.innerHTML = `
                <h3>采购 ID：${purchase.purchaseID}</h3>
                <p>食材 ID：${purchase.ingredientID}</p>
                <p>采购数量：${purchase.purchaseQuantity}</p>
                <p>供应商 ID：${purchase.supplierID}</p>
                <button onclick="updatePurchase(${purchase.purchaseID})">更新</button>
                <button onclick="deletePurchase(${purchase.purchaseID})">删除</button>
            `;
                purchaseList.appendChild(purchaseItem);
            });
        })
        .catch(error => console.error('获取采购记录出错：', error));
}


function addPurchase() {
    const purchaseIngredientId = parseInt(document.getElementById('purchase-ingredient-id').value);
    const purchaseQuantity = parseInt(document.getElementById('purchase-quantity').value);
    const purchaseSupplierId = parseInt(document.getElementById('purchase-supplier-id').value);
    fetch('http://localhost:8080/purchases/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ ingredientId: purchaseIngredientId, quantity: purchaseQuantity, supplierId: purchaseSupplierId })
    })
        .then(response => response.json())
        .then(() => {
            loadPurchases();
            document.getElementById('purchase-ingredient-id').value = '';
            document.getElementById('purchase-quantity').value = '';
            document.getElementById('purchase-supplier-id').value = '';
        })
        .catch(error => console.error('添加采购记录出错：', error));
}


function updatePurchase(purchaseId) {
    const newPurchaseIngredientId = parseInt(prompt('请输入新食材 ID：'));
    const newPurchaseQuantity = parseInt(prompt('请输入新采购数量：'));
    const newPurchaseSupplierId = parseInt(prompt('请输入新供应商 ID：'));
    if (newPurchaseIngredientId!== null && newPurchaseQuantity!== null && newPurchaseSupplierId!== null) {
        fetch(`http://localhost:8080/purchases/update/${purchaseId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ ingredientId: newPurchaseIngredientId, quantity: newPurchaseQuantity, supplierId: newPurchaseSupplierId })
        })
            .then(response => response.json())
            .then(() => loadPurchases())
            .catch(error => console.error('更新采购记录出错：', error));
    }
}


function deletePurchase(purchaseId) {
    if (confirm('确认删除该采购记录？')) {
        fetch(`http://localhost:8080/purchases/delete/${purchaseId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(() => loadPurchases())
            .catch(error => console.error('删除采购记录出错：', error));
    }
}


// 辅助函数
function updateSelectOptions(selectElementId, data, valueKey, textKey) {
    const selectElement = document.getElementById(selectElementId);
    selectElement.innerHTML = '';
    data.forEach(item => {
        const option = document.createElement('option');
        option.value = item[valueKey];
        option.textContent = item[textKey];
        selectElement.appendChild(option);
    });
}


// 页面加载时，预先加载一些必要的数据
window.onload = function() {
    loadMenuCategories();
    loadTables();
    loadSuppliers();
    loadIngredients();
};