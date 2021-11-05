package model;

import iceFactory.IceFactoryApplication.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerOrderTest {

    private Staff staff;
    private Customer customer;
    private Product product;
    private OrderItem orderItem;
    private CustomerOrder customerOrder;

    @BeforeEach
    void setup(){
        staff = new Staff();
        staff.setUsername("Staff");
        staff.setPassword("0000");
        staff.setFirstName("first");
        staff.setLastName("last");
        staff.setPhoneNumber("Staff PhoneNumber");
        staff.setAddress("Staff Address");

        UUID random = UUID.randomUUID();
        customer = new Customer();
        customer.setCustomerId(random);
        customer.setName("Customer");
        customer.setPhoneNumber("Customer PhoneNumber");
        customer.setAddress("Customer Address");
        customer.setType("Delivery");

        product = new Product();
        product.setPName("Circle Ice");
        product.setPrice(50);
        product.setQuantity(30);
        product.setPriceDelivery(20);

        UUID random1 = UUID.randomUUID();
        orderItem = new OrderItem();
        orderItem.setOrderItemId(random1);
        orderItem.setOrderQuantity(50);
        orderItem.setProduct(product);

        UUID random2 = UUID.randomUUID();
        customerOrder = new CustomerOrder();
        customerOrder.setOrderId(random2);
        customerOrder.setCustomer(customer);
        customerOrder.setStaff(staff);
        customerOrder.addOrder(orderItem);

    }
    @Test
    void prepare_order_test(){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()-> {
            customerOrder.PrepareOrder();
        });
        String expectedMessage = "Kong mai por";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }
}
