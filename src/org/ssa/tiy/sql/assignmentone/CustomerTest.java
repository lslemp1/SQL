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

public class CustomerTest {
    
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root";
    DataSource datasource;
    Connection connection;
    CustomerImpl customer;    
    
    Customer c1 = new Customer("John", "Doe");
    Customer c2 = new Customer("Bob", "Jones");
    Customer c3 = new Customer("Sarah", "Manning");
    
    @Before
    public void setupDB() throws Exception 
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        Connection connection = mysqlDataSource.getConnection();
        this.datasource = mysqlDataSource;
        this.connection = connection;
        customer = new CustomerImpl(datasource);
    }
    
    @After
    public void teardown() throws SQLException {
        this.connection.close();
    }
    
    @Test
    public void create()
    {
        Customer a = customer.insert(c1);
        Customer b = customer.insert(c2);
        Customer c = customer.insert(c3);
        assertEquals("", "John", a.getFirst());
        assertEquals("", "Jones", b.getLast());     
        assertEquals("", "Manning", c.getLast());     
    }
    
    @Test
    public void read()
    {
        Customer a = customer.insert(c1);
//        Customer b = customer.insert(c2);
//        Customer c = customer.insert(c3);
//        List<Customer> list = customer.read();
//        assertTrue(list.contains(a));
//        assertTrue(list.contains(b));
//        assertTrue(list.contains(c));
        System.out.println(customer.read(a.getId()));
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
        customer.delete(a);
        customer.delete(b);
        assertTrue(customer.read(a.getId()) == null);
        assertTrue(customer.read(b.getId()) == null);
    }
}
