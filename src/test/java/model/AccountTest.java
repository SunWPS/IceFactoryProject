package model;

import iceFactory.IceFactoryApplication.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    Account account;

    @BeforeEach
    void setup(){
        account = new Account();
        account.setUsername("acc1");
        account.setPassword("1111");

    }

    @Test
    void entry_check_test(){
        Throwable exception = assertThrows(IllegalArgumentException.class, ()-> {
            account.entryCheck("acc1","2222");
        });
        String expectedMessage = "Wrong password.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage,actualMessage);
    }
}
