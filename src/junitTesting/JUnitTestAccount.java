package junitTesting;

import controllers.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestAccount {
    Account account1 = Account.getInstance();
    Account account2 = Account.getInstance();

    @Test
    /** account2 should have the same value as account1*/
    void testIfSingleton(){
        assertEquals(null,account1.getUsername());
        assertEquals(null,account1.getPass());
        assertEquals(0,account1.getIsAdmin());

        account1.setUsername("a");
        account1.setPass("a");
        account1.setIsAdmin(1);
        assertEquals("a",account1.getUsername());
        assertEquals("a",account1.getPass());
        assertEquals(1,account1.getIsAdmin());

        assertEquals("a",account2.getUsername());
        assertEquals("a",account2.getPass());
        assertEquals(1,account2.getIsAdmin());
    }
}