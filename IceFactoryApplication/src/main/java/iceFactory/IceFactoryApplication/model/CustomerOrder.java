package iceFactory.IceFactoryApplication.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomerOrder {

    enum Status{
        PrepareProduct,
        WaitForDelivery,
        CloseOrder
    }
    private UUID orderId;
    private Customer customer;
    private Staff staff;
    private List<OrderItem> orderItemList = new ArrayList<>();
    private String orderStatus = Status.PrepareProduct.toString();
    private String orderDate;

    public void PrepareOrder(){
        for(OrderItem item : orderItemList){
            if(item.getProduct().checkStock(item.getOrderQuantity()));
            else throw new IllegalArgumentException("Kong mai por");
        }
        for(OrderItem item : orderItemList){
            item.getProduct().decreaseStock(item.getOrderQuantity());
        }
        orderStatus = Status.WaitForDelivery.toString();
        return;
    }

    public void timeStamp(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        orderDate = LocalDateTime.now().format(formatter);
    }

    public void addOrder(OrderItem item){
        orderItemList.add(item);
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = Status.valueOf(orderStatus).toString();
    }

    public void changeStatus(){
        switch (Status.valueOf(orderStatus)){
            case PrepareProduct:
                orderStatus = Status.WaitForDelivery.toString();
                break;
            case WaitForDelivery:
                orderStatus = Status.CloseOrder.toString();
                break;
        }
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
