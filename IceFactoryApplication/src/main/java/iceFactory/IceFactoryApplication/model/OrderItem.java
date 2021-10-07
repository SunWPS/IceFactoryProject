package iceFactory.IceFactoryApplication.model;

import java.util.UUID;

public class OrderItem {

    private UUID orderItemId;
    private Product product;
    private int orderQuantity;
    private float price;
    private float priceDelivery;
    private CustomerOrder customerOrder;

    

    public Product getProduct() {
        return product;
    }

    public void setOrderItemId(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPriceDelivery(float priceDelivery) {
        this.priceDelivery = priceDelivery;
    }

    public void setOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public float getPrice() {
        return product.getPrice()*orderQuantity;
    }

    public float getPriceDelivery() {
        return product.getPriceDelivery()*orderQuantity;
    }



    public UUID getOrderItemId() {
        return orderItemId;
    }

    public CustomerOrder getOrder() {
        return customerOrder;
    }


}
