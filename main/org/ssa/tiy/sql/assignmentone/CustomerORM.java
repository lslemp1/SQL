package org.ssa.tiy.sql.assignmentone;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerORM 
{
    default String projection()
    {
        return "id, first, last";
    }
    
    default String table()
    {
        return "customers";
    }
    
    default Customer map(ResultSet results) throws SQLException
    {
        Customer cust = new Customer();
        cust.setFirst(results.getString("first"));
        cust.setLast(results.getString("last"));
        cust.setId(results.getInt("id"));
        return cust;
    }
    
    default String prepareInsert()
    {
        return "INSERT INTO " + table() + " (first, last) VALUES(?, ?) ";
    }
    
    default String prepareUpdate()
    {
        return "UPDATE " + table() + " SET first = ?, last = ? WHERE id = ?";
    }
    
    default String prepareRead() //by primary key
    {
        return "SELECT " + projection() + " FROM " + table() + " WHERE id = ?";
    }
    
    default String prepareDelete() //by primary key
    {
        return "DELETE FROM " + table() + " WHERE id = ?"; 
    }

}
