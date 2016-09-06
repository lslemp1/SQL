package org.ssa.tiy.sql.assignmentone;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;

import dao.CustomerDAO;
import dao.CustomerImpl;
import model.Customer;

public class CustomerTest {
    
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root"; //"profileSQL=true&useServerPrepStmts=true";
    DataSource datasource;
    CustomerDAO customer;    
    
    Customer c1 = new Customer("John", "Doe");
    Customer c2 = new Customer("Bob", "Jones");
    Customer c3 = new Customer("Sarah", "Manning");
    
    @Before
    public void setupDB() throws Exception 
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        this.datasource = mysqlDataSource;
        this.customer = new CustomerImpl(datasource);
        this.customer.clear();
    }
    
    @Test
    public void create()
    {
        Customer a = customer.insert(c1);
        Customer b = customer.insert(c2);
        Customer c = customer.insert(c3);
        assertTrue(a.getId() > 0);
        assertTrue(b.getId() > 0);
        assertTrue(c.getId() > 0);  
        assertEquals("", "Manning", c.getLast()); 
        assertTrue(c.equals(customer.read(c.getId())));
    }
    
    @Test
    public void read()
    {
        Customer a = customer.insert(c1);
        customer.read(a.getId());
        customer.delete(a);
        assertTrue(customer.read(a.getId()) == null);
//        Customer b = customer.insert(c2);
//        Customer c = customer.insert(c3);
//        List<Customer> list = customer.read();
//        assertTrue(list.contains(a));
//        assertTrue(list.contains(b));
//        assertTrue(list.contains(c));
//        System.out.println(customer.read(a.getId()));
    }
   
    @Test
    public void update()
    {
        Customer c = customer.insert(c3);
        c.setLast("Hendrix");
        c = customer.update(c);
        assertTrue(c.getLast().equals("Hendrix"));
    }
    
    @Test
    public void delete()
    {
        Customer a = customer.insert(c1);
        Customer b = customer.insert(c2);
        
        assertTrue(customer.delete(a));
        assertTrue(customer.delete(b));
        
        assertTrue(customer.read(a.getId()) == null);
        assertTrue(customer.read(b.getId()) == null);
        
        assertFalse(customer.delete(a));
        assertFalse(customer.delete(b));
    }
}
