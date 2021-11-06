package model;

import iceFactory.IceFactoryApplication.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OwnerTest {

    private Owner owner;

    @BeforeEach
    void setup(){
        owner = new Owner();
        owner.setUsername("owner");
        owner.setPassword("passwd");
    }

    @Test
    void change_password_test_wrong_old_password(){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()-> {
            owner.changePassword("passs", "12345");
        });
        String expectedMessage = "Old password didn't match!!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }

}
