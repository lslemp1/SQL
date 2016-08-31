package org.ssa.tiy.sql.assignmentone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class CustomerImpl implements CustomerDAO  {

    public int id;
    public String first;
    public String last;
    Connection connection;
    DataSource datasource;
    CustomerORM orm;
    
    public CustomerImpl(DataSource datasource)
    {
        this.datasource = datasource;
    }

    @Override
    public Customer insert(Customer customer)
    {
        PreparedStatement prepareStatement;
        try {
            Connection connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement(orm.prepareInsert(), Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, customer.getFirst());
            prepareStatement.setString(2, customer.getLast());
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            generatedKeys.next();
            Customer cust = read(generatedKeys.getInt(1));            
            System.err.println("inserted customer with id " + generatedKeys.getInt(1));
            return cust;
        } catch (SQLException e) {
            System.err.println("Cannot insert customer.");
            return null;
        }
        
    }

    @Override
    public boolean delete(Customer toDelete) {
        PreparedStatement prepareStatement;
        try {
            Connection connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement("DELETE FROM customers WHERE id = ?");
            prepareStatement.setInt(1, toDelete.getId());
            //prepareStatement.executeUpdate();
            return prepareStatement.executeUpdate()>0;
        } catch (SQLException e) {
            System.err.println("customer does not exist");
            return false;
        }       
    }

    @Override
    public Customer update(Customer customer) {
        PreparedStatement prepareStatement;
        try {
            Connection connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement("UPDATE customers SET first = ?, last = ? WHERE id = ?");
            prepareStatement.setString(1, customer.getFirst());
            prepareStatement.setString(2, customer.getLast());
            prepareStatement.setInt(3, customer.getId());
            prepareStatement.executeUpdate();
            return new Customer(customer.getFirst(), customer.getLast());
        } catch (SQLException e) {
            System.err.println("Cannot update customer.");
            return null;
        }        
    }

    @Override
    public Customer read(int id) {
        PreparedStatement prepareStatement;
        try {
            Connection connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
            prepareStatement.setInt(1, id);
            ResultSet results = prepareStatement.executeQuery();
            results.next();
            
            Customer customer = new Customer(results.getString(2), results.getString(3));
            customer.setId(results.getInt(1));
            return customer;
            
        } catch (SQLException e) {
            //System.err.println("Customer does not exist.");
            return null;
        }
    }

    @Override
    public int clear() {
        PreparedStatement prepareStatement;
        try{
            Connection connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement("DELETE FROM customers");
            return prepareStatement.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
        
    }
    
//    @Override
//    public List<Customer> read() {
//        List<Customer> customers = new ArrayList<>();
//        PreparedStatement prepareStatement;
//    try {
//        Connection connection = datasource.getConnection();
//        prepareStatement = connection.prepareStatement("SELECT * FROM customers");
//        ResultSet results = prepareStatement.executeQuery();
//        while (results.next()) {
//        Customer c1 = new Customer(results.getInt(1), results.getString(2), results.getString(3));
//        customers.add(c1);
//        }
//        connection.close();
//        return customers;
//    } catch (SQLException e) {
//        System.err.println("Error.");
//        return null;
//    }
//
//    }
}
