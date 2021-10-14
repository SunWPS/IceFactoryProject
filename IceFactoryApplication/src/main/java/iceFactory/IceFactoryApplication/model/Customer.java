package iceFactory.IceFactoryApplication.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Customer {
    enum CustomerType{
        Delivery,Pickup
    }
    private UUID customerId;
    private String name;
    private String address;
    private String phoneNumber;
    private String type;
    private Set<CustomerOrder> customerOrders = new HashSet<>();

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDelivery(){
        type = CustomerType.Delivery.toString();
    }

    public void setPickup(){
        type = CustomerType.Pickup.toString();
    }

    public Set<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(Set<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
}
