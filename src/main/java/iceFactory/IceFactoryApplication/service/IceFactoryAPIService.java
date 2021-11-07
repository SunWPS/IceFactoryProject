package iceFactory.IceFactoryApplication.service;


import iceFactory.IceFactoryApplication.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

import iceFactory.IceFactoryApplication.model.Owner;
import iceFactory.IceFactoryApplication.model.Staff;

import java.util.UUID;




public class IceFactoryAPIService {


    private RestTemplate restTemplate ;


    private String url="https://ice-factory-api.herokuapp.com/"; // using this when using API from Heroku

//    private String url="http://localhost:8090/"; //using this when using API from docker


    public IceFactoryAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //OWNER---------------------------------------------------------------------
    public List<Owner> getOwnerAll() {
        ResponseEntity<Owner[]> response = restTemplate.getForEntity(url+"owner", Owner[].class);
        Owner[] owner = response.getBody();
        return Arrays.asList(owner);
    }
    public Owner getOwnerByUsername(String username) {
        ResponseEntity<Owner> response = restTemplate.getForEntity(url+"owner/"+username, Owner.class);
        return response.getBody();
    }

    public void updateOwner(Owner owner) {
        restTemplate.put(url+"owner/"+owner.getUsername(), owner, Owner.class);
    }

    //Staff---------------------------------------------------------------------
    public List<Staff> getStaffAll() {
        ResponseEntity<Staff[]> response = restTemplate.getForEntity(url+"staff", Staff[].class);
        Staff[] staffs = response.getBody();
        return Arrays.asList(staffs);
    }

    public Staff getStaffByUsername(String username) {
        ResponseEntity<Staff> response = restTemplate.getForEntity(url+"staff/"+username, Staff.class);
        Staff staff = response.getBody();
        return staff;
    }

    public void addStaff(Staff staff) {
        restTemplate.postForObject(url+"staff", staff, Staff.class);
    }

    public void updateStaff(Staff staff) {
        restTemplate.put(url+"staff/"+staff.getUsername(), staff, Staff.class);
    }

    public void deleteStaff(String username) {
        restTemplate.delete(url+"staff/"+username);
    }

//------------customer----------------------------------------------------------------------------------------
    public List<Customer> getCustomerAll(){
        ResponseEntity<Customer[]> response = restTemplate.getForEntity(url+"customer", Customer[].class);
        Customer[] customers = response.getBody();
        return Arrays.asList(customers);
    }

    public Customer getCustomerByCustomerId(UUID id){
        ResponseEntity<Customer> response = restTemplate.getForEntity(url+"customer/"+id, Customer.class);
        return response.getBody();
    }

    public void updateCustomer(Customer customer){
        restTemplate.put(url+"customer/" + customer.getCustomerId(), customer, Customer.class);
    }

    public void addCustomer(Customer customer){
        restTemplate.postForObject(url+"customer/", customer, Customer.class);
    }
    public void deleteCustomer(UUID customerId) {
        restTemplate.delete(url+"customer/"+customerId);
    }

//-------------Product------------------------------------------------------------------------------------------------

   public List<Product> getProductAll(){
        ResponseEntity<Product[]> response = restTemplate.getForEntity(url+"product/",Product[].class);
        Product[] product = response.getBody();
        return Arrays.asList(product);
   }
    public Product getProductByPName(String pName){
        ResponseEntity<Product> response = restTemplate.getForEntity(url+"product/"+pName, Product.class);
        Product product = response.getBody();
        return product;
    }

    public void updateProduct(Product product){
        restTemplate.put(url+"product/"+ product.getPName(), product, Product.class);
    }
//------------Order---------------------------------------------------------------------------------------

    public List<CustomerOrder> getCustomerOrderAll(){
        ResponseEntity<CustomerOrder[]> response = restTemplate.getForEntity(url+"customerOrder/", CustomerOrder[].class);
        CustomerOrder[] customerOrder = response.getBody();
        return Arrays.asList(customerOrder);
    }

    public CustomerOrder getCustomerOrderById(UUID id){
        ResponseEntity<CustomerOrder> response = restTemplate.getForEntity(url+"customerOrder/"+id, CustomerOrder.class);
        return response.getBody();
    }

    public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
       return restTemplate.postForObject(url+"customerOrder/", customerOrder, CustomerOrder.class);
    }

    public void updateCustomerOrder(CustomerOrder order){
        restTemplate.put(url+"customerOrder/"+order.getOrderId(),order,CustomerOrder.class);
    }
//----------------OrderItem----------------------------------------------------------------------------------------
    public List<OrderItem> getOrderItemAll(){
        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(url+"orderItem/", OrderItem[].class);
        OrderItem[] orderItems = response.getBody();
        return Arrays.asList(orderItems);
    }

    public OrderItem getOrderItemByOrderItemId(UUID id){
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(url+"orderItem/"+id, OrderItem.class);
        return response.getBody();
    }

    public OrderItem addOrderItem(OrderItem orderItem) {
       return restTemplate.postForObject(url+"orderItem/", orderItem, OrderItem.class);
    }

    public void updateOrderItem(OrderItem item){
        restTemplate.put(url+"orderItem/"+item.getOrderItemId(),item,OrderItem.class);
    }
//------------------Bill----------------------------------------------------------------------------------------

    public List<Bill> getBillAll(){
        ResponseEntity<Bill[]> response = restTemplate.getForEntity(url+"bill/", Bill[].class);
        Bill[] bill = response.getBody();
        return Arrays.asList(bill);
    }

    public Bill getBillById(UUID id){
        ResponseEntity<Bill> response = restTemplate.getForEntity(url+"bill/"+id, Bill.class);
        return response.getBody();
    }

    public void addBill(Bill bill) {
        restTemplate.postForObject(url+"bill/", bill, Bill.class);
    }
}


