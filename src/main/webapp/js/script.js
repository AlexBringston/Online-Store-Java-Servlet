function addProductToCart(id) {
    let data = sessionStorage.getItem('shoppingCart');
    alert("Product added to cart");

    if (data == null) {
        sessionStorage.setItem('shoppingCart', id);
    } else {
        sessionStorage.setItem('shoppingCart', data.concat(",").concat(id));
    }
    console.log(sessionStorage.getItem('shoppingCart'));
}