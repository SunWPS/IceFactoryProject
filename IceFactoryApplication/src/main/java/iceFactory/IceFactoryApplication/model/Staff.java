package iceFactory.IceFactoryApplication.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Staff extends Account{
    private String firstName;
    private String lastName;
    private String dateTime;
    private String phoneNumber;
    private String address;
    private Owner owner;
    private Set<CustomerOrder> customerOrders = new HashSet<>();
    public Owner getOwner() {
        return owner;
    }

    //add order
    //editOrder






    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void checkIn(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dateTime = LocalDateTime.now().format(formatter);
    }

    public Staff logIn(String username,String password){
        try {
            if(entryCheck(username,password)){
                checkIn();
                return this;}
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return null;
        }




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<CustomerOrder> getOrders() {
        return customerOrders;
    }

    public void setOrders(Set<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
}
