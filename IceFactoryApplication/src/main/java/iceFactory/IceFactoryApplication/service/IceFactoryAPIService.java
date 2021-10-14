package iceFactory.IceFactoryApplication.service;


import iceFactory.IceFactoryApplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

import iceFactory.IceFactoryApplication.model.Owner;
import iceFactory.IceFactoryApplication.model.Staff;

import java.util.UUID;



//@Service
public class IceFactoryAPIService {

//    @Autowired
    private RestTemplate restTemplate ;


    public IceFactoryAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //OWNER---------------------------------------------------------------------
    public List<Owner> getOwnerAll() {
        String url = "http://localhost:8090/owner";
        ResponseEntity<Owner[]> response = restTemplate.getForEntity(url, Owner[].class);
        Owner[] owner = response.getBody();
        return Arrays.asList(owner);
    }
    public Owner getOwnerByUsername(String username) {
        String url = "http://localhost:8090/owner/" + username;
        ResponseEntity<Owner> response = restTemplate.getForEntity(url, Owner.class);
        return response.getBody();
    }

    public void updateOwner(Owner owner) {
        String url = "http://localhost:8090/owner/" + owner.getUsername();
        restTemplate.put(url, owner, Owner.class);
    }

    //Staff---------------------------------------------------------------------
    public List<Staff> getStaffAll() {
        String url = "http://localhost:8090/staff";
        ResponseEntity<Staff[]> response = restTemplate.getForEntity(url, Staff[].class);
        Staff[] staffs = response.getBody();
        return Arrays.asList(staffs);
    }

    public Staff getStaffByUsername(String username) {
        String url = "http://localhost:8090/staff/" + username;
        ResponseEntity<Staff> response = restTemplate.getForEntity(url, Staff.class);
        Staff staff = response.getBody();
        return staff;
    }

    public void addStaff(Staff staff) {
        String url = "http://localhost:8090/staff";
        restTemplate.postForObject(url, staff, Staff.class);
    }

    public void updateStaff(Staff staff) {
        String url = "http://localhost:8090/staff/" + staff.getUsername();
        restTemplate.put(url, staff, Staff.class);
    }

    public void deleteStaff(String username) {
        String url = "http://localhost:8090/staff/" + username;
        restTemplate.delete(url);
    }

//------------customer----------------------------------------------------------------------------------------
    public List<Customer> getCustomerAll(){
        String url = "http://localhost:8090/customer";
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(url, Customer[].class);
        Customer[] customers = response.getBody();
        return Arrays.asList(customers);
    }

    public Customer getCustomerByCustomerId(UUID id){
        String url = "http://localhost:8090/customer/"+ id;
        ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);
        return response.getBody();
    }

    public void updateCustomer(Customer customer){
        String url = "http://localhost:8090/customer/" + customer.getCustomerId();
        restTemplate.put(url, customer, Customer.class);
    }

    public void addCustomer(Customer customer){
        String url = "http://localhost:8090/customer";
        restTemplate.postForObject(url, customer, Customer.class);
    }

//-------------Product------------------------------------------------------------------------------------------------

   public List<Product> getProductAll(){
        String url = "http://localhost:8090/product";
        ResponseEntity<Product[]> response = restTemplate.getForEntity(url,Product[].class);
        Product[] product = response.getBody();
        return Arrays.asList(product);
   }
    public Product getProductByPName(String pName){
        String url = "http://localhost:8090/product/" + pName;
        ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);
        Product product = response.getBody();
        return product;
    }

    public void updateProduct(Product product){
        String url = "http://localhost:8090/product/" + product.getPName();
        restTemplate.put(url, product, Product.class);
    }
//------------Order---------------------------------------------------------------------------------------

    public List<CustomerOrder> getCustomerOrderAll(){
        String url = "http://localhost:8090/customerorder";
        ResponseEntity<CustomerOrder[]> response = restTemplate.getForEntity(url, CustomerOrder[].class);
        CustomerOrder[] customerOrder = response.getBody();
        return Arrays.asList(customerOrder);
    }

    public CustomerOrder getCustomerOrderById(UUID id){
        String url = "http://localhost:8090/customerorder/"+ id;
        ResponseEntity<CustomerOrder> response = restTemplate.getForEntity(url, CustomerOrder.class);
        return response.getBody();
    }

    public void addCustomerOrder(CustomerOrder customerOrder) {
        String url = "http://localhost:8090/customerorder";
        restTemplate.postForObject(url, customerOrder, CustomerOrder.class);
    }

    public void updateCustomerOrder(CustomerOrder order){
        String url="http://localhost:8090/customerorder/"+order.getOrderId();
        restTemplate.put(url,order,CustomerOrder.class);
    }
//----------------OrderItem----------------------------------------------------------------------------------------
    public List<OrderItem> getOrderItemAll(){
        String url = "http://localhost:8090/orderItem";
        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(url, OrderItem[].class);
        OrderItem[] orderItems = response.getBody();
        return Arrays.asList(orderItems);
    }

    public OrderItem getOrderItemByOrderItemId(UUID id){
        String url = "http://localhost:8090/orderItem/"+ id;
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(url, OrderItem.class);
        return response.getBody();
    }

    public void addOrderItem(OrderItem orderItem) {
        String url = "http://localhost:8090/orderItem";
        restTemplate.postForObject(url, orderItem, OrderItem.class);
    }






}


