package iceFactory.IceFactoryApplication.model;

import java.util.UUID;

public class OrderItem {

    private UUID orderItemId;
    private Product product;
    private int orderQuantity;
    private float price;
    private CustomerOrder customerOrder;
    private String pName;


    public void setPrice(){
        if(customerOrder.getCustomer().getType().equals(Customer.CustomerType.Delivery.toString())){
            this.price = product.getPriceDelivery()*orderQuantity;
        } else {
            this.price = product.getPrice()*orderQuantity;
        }
    };

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

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


    public void setOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public float getPrice() {
        return this.price;
    }


    public String getPName(){
        return  this.pName;
    }
    public void setPName(String name) {this.pName = name;}

    public UUID getOrderItemId() {
        return orderItemId;
    }

    public CustomerOrder getOrder() {
        return customerOrder;
    }

    public int getMissing(int stock){
        if(orderQuantity - stock >0)
            return  orderQuantity-stock;
        else
            return 0;
    }



}
