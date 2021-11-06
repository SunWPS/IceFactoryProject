package model;

import iceFactory.IceFactoryApplication.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    private Product product;

    @BeforeEach
    void setup(){
        product = new Product();
        product.setPName("Circle Ice");
        product.setPrice(50);
        product.setQuantity(30);
        product.setPriceDelivery(20);
    }

    @Test
    void increase_product_test() {
        product.increaseStock(5);
        assertEquals(product.getQuantity(),35);
    }
    @Test
    void decrease_product_test() {
        product.decreaseStock(10);
        assertEquals(product.getQuantity(),20);
    }

    @Test
    void check_stock_test_quantity_less_than_stock(){
        assertEquals(product.checkStock(20), true);
    }

    @Test
    void check_stock_test_quantity_more_than_stock(){
        assertEquals(product.checkStock(1000), false);
    }
}


