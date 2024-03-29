package com.store.model.entity;

/**
 * OrderItem entity. Is used to store data from corresponding table about exact item from order.
 *
 * @author Alexander Mulyk
 * @since 2021-06-14
 */
public class OrderItem extends Entity{

    private Product product;

    private int quantity;

    private long orderId;

    public OrderItem() {
        super();
    }

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
