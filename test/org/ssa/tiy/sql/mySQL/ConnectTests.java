package org.ssa.tiy.sql.mySQL;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectTests {
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root";

    DataSource datasource;
    Connection connection;

    @Before
    public void setupDB() throws Exception {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        Connection connection = mysqlDataSource.getConnection();
        this.datasource = mysqlDataSource;
        this.connection = connection;
    }

    @After
    public void teardown() throws SQLException {
        this.connection.close();

    }

    @Test
    public void datasource() throws SQLException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        Connection connection = mysqlDataSource.getConnection();
        Statement sql = connection.createStatement();

        ResultSet results = sql.executeQuery("SELECT * FROM customers WHERE id = 1");
        assertTrue("", results.next());

        assertEquals("", 1, results.getInt(1));
        assertEquals("", "John", results.getString(2));
        assertEquals("", "Doe", results.getString(3));

    }
    
    //prepareStatement:calls to DB server and returns an opaque id. now talks to driver via id. already has execution plan.
    //resultsSet: comparable to running select statement in cmd prompt

    @Test
    public void prepare() throws SQLException {
        PreparedStatement prepareStatement = this.connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
        prepareStatement.setInt(1, 1);
        ResultSet results = prepareStatement.executeQuery();

        assertTrue("", results.next());

        assertEquals("", 1, results.getInt(1));
        assertEquals("", "John", results.getString(2));
        assertEquals("", "Doe", results.getString(3));
    }
   
    //@Test
    public void create() throws SQLException
    {
        PreparedStatement prepareStatement = this.connection.prepareStatement("INSERT INTO customers(first, last) VALUES(?, ?)");
        prepareStatement.setString(1, "Bob");
        prepareStatement.setString(2, "Jones");        
        assertEquals("", 1,  prepareStatement.executeUpdate());
    }
   
    
    //@Test
    public void keys() throws SQLException
    {
        PreparedStatement prepareStatement = this.connection.prepareStatement("INSERT INTO customers(first, last) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, "Jane");
        prepareStatement.setString(2, "Doe");        
        assertEquals("", 1,  prepareStatement.executeUpdate());
        ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
        assertTrue("", generatedKeys.next());
        System.err.println("inserted customer with id " + generatedKeys.getInt(1));
    }
    
    @Test
    public void update() throws SQLException
    {
        PreparedStatement prepareStatement = this.connection.prepareStatement("UPDATE customers SET first = ?, last = ? WHERE id = ?");
        prepareStatement.setString(1, "Bob");
        prepareStatement.setString(2, "Jones");
        prepareStatement.setInt(3, 10);
        assertEquals("", 1,  prepareStatement.executeUpdate());
    }
    
    @Test
    public void delete() throws SQLException
    {        
        PreparedStatement prepareStatement = this.connection.prepareStatement("DELETE FROM customers WHERE first = ?");
        prepareStatement.setString(1, "John");       
        assertEquals("", 0,  prepareStatement.executeUpdate());
    }

}
