package iceFactory.IceFactoryApplication.model;

import java.util.UUID;

public class Bill {

    private UUID billId;
    private String createDate;
    private float totalPrice;
    private CustomerOrder order;

    public UUID getBillId() {
        return billId;
    }

    public void setBillId(UUID billID) {
        this.billId = billID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }
}
