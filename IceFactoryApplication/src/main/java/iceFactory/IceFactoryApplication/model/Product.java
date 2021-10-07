package iceFactory.IceFactoryApplication.model;

import java.util.HashSet;
import java.util.Set;

public class Product {

    private String pName;
    private int quantity;
    private float price;
    private float priceDelivery;
    private Set<OrderItem> items = new HashSet<>();

    public Product(String pName, int quantity, float price, float priceDelivery) {
        this.pName = pName;
        this.quantity = quantity;
        this.price = price;
        this.priceDelivery = priceDelivery;
    }

    public boolean checkStock(int quantity){
        if(quantity<= this.quantity && quantity>0) return true;
        else return false;
    }

    public void increaseStock(int quantity){
        if(quantity >0){
            this.quantity+=quantity;
        }
    }

    public void decreaseStock(int quantity){
        if(quantity >0 && quantity <= this.quantity){
            this.quantity-=quantity;
        }
    }
    public String getpName() {
        return pName;
    }

    public int getQuantity() {
        return quantity;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPriceDelivery() {
        return priceDelivery;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public void setPriceDelivery(float priceDelivery) {
        this.priceDelivery = priceDelivery;
    }
}
