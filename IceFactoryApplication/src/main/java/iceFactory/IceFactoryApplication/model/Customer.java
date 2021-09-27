package iceFactory.IceFactoryApplication.model;

import java.util.UUID;

public class Customer {
    private UUID customerId;
    private String fName;
    private String lName;
    private String Address;
    private String phoneNumber;

    public Customer( String fName, String lName, String address, String phoneNumber) {

        this.fName = fName;
        this.lName = lName;
        Address = address;
        this.phoneNumber = phoneNumber;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
