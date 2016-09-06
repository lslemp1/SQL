package org.ssa.tiy.sql.assignmenttwo;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;

import dao.AccountImpl;
import dao.AccountORM;
import dao.CustomerDAO;
import dao.CustomerImpl;
import model.Account;
import model.Customer;
import model.Account.Type;

public class AccountDAOTest {

    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root"; // "profileSQL=true&useServerPrepStmts=true";
    DataSource datasource;
    CustomerDAO customer;
    AccountImpl account;

    Customer c1 = new Customer(1, "John", "Doe", false);
    Account a1 = new Account(c1, Type.SA, BigDecimal.valueOf(-5000));
    Account a2 = new Account(c1, Type.CH, BigDecimal.valueOf(7000));

    @Before
    public void setup() 
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        this.datasource = mysqlDataSource;
        this.account = new AccountImpl(datasource, new AccountORM() {});

        this.customer = new CustomerImpl(datasource);
        account.clear();
    }

    @Test
    public void create() 
    {
        Account a = account.insert(a2);
        assertEquals("", BigDecimal.valueOf(7000), a.getBalance());
        assertNotEquals(a, a2);
        assertTrue(a.getId() != 0);
        assertTrue(a.getBalance().equals(a2.getBalance()));
        assertTrue(a.getType().equals(a2.getType()));
        assertTrue(a.deeplyEquals(account.read(a.getId())));
    }

    @Test
    public void read() 
    {
        Account a = account.insert(a1);
        
        assertTrue(a.getType().equals(account.read(a.getId()).getType()));
        assertTrue(a.getBalance().compareTo(account.read(a.getId()).getBalance()) == 0);
//        assertTrue(a.getId() == account.read(a.getId()).getId());

//        Customer customer = account.read(a.getId()).getCust();
//        assertTrue(customer.isLoaded());
//        assertNotNull("assumed first is defined as not null in the customers DDL", customer.getFirst());
//        assertNotNull("assumed last is defined as not null in the customers DDL", customer.getLast());
//        
//        account.delete(a.getId());
//        assertTrue(account.read(a.getId()) == null);
    }

    @Test
    public void update() 
    {
        Account a = account.insert(a1);

        a.setBalance(BigDecimal.valueOf(1000));
        a.setType(Type.CH);
        a = account.update(a);

        assertTrue(a.getBalance().equals(BigDecimal.valueOf(1000)));
        assertTrue(a.getType().equals(Type.CH));
        assertTrue(a.deeplyEquals(account.read(a.getId())));
    }

    @Test
    public void delete() 
    {
        Account a = account.insert(a1);
        Account b = account.insert(a2);

        assertTrue(account.delete(a.getId()));
        assertTrue(account.delete(b.getId()));

        assertTrue(account.read(a.getId()) == null);
        assertTrue(account.read(b.getId()) == null);

        assertFalse(account.delete(a.getId()));
        assertFalse(account.delete(b.getId()));
    }

    @Test
    public void testClear() 
    {
        account.insert(a1);
        account.insert(a2);
        assertTrue(account.clear() == 2);
    }

    @Test
    public void testReadUser() 
    {
        Account a = account.insert(a1);
        account.insert(a2);
        int user = a.getCust();
        assertTrue(account.readUser(user).size() == 2);
    }

    @Test
    public void testUnderwater() 
    {
        Account a3 = new Account(c1, Type.CH, BigDecimal.valueOf(-8000));
        account.insert(a1);
        account.insert(a2);
        account.insert(a3);

        List<Account> accts = new ArrayList<>();
        accts = account.readUnderwater();
        assertEquals("", 2, accts.size());
    }

}
