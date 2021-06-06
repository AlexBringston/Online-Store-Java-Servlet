$(function () {
    $('#depositButton').click(function () {
        $('#depositForm').toggleClass('depositForm')
    })
});

function countTotal(array) {
    let price = array[0];
    let id = array[1];
    let quantity = document.getElementById("quantity_".concat(id)).value;
    document.getElementById("localTotalPrice_".concat(id)).innerText = String(price * quantity);
    let prices = document.querySelectorAll('.localTotalPrice');
    let sum = 0;
    for (let i = 0; i < prices.length; i++) {
        const element = prices[i].innerText;
        sum += parseInt(element);
    }
    document.getElementById("totalAmount").innerText = "Total: ".concat(String(sum));

}

document.addEventListener("DOMContentLoaded", () => {
    let prices = document.querySelectorAll('.localTotalPrice');
    let sum = 0;
    for (let i = 0; i < prices.length; i++) {
        const element = prices[i].innerText;
        sum += parseInt(element);
    }
    document.getElementById("totalAmount").innerText = "Total: ".concat(String(sum));
});