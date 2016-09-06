package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Account.Type;

public interface AccountORM extends ORM<Account>
{
    default String projection()
    {
        return "id, customer, type, balance";
    }
    
    default String table()
    {
        return "accounts";
    }
    
    default Account map(ResultSet results) throws SQLException
    {
        return new Account(results.getInt("id"), results.getInt("customer"), Type.valueOf(results.getString("type")), results.getBigDecimal("balance"));

    }
    
    default String prepareInsert()
    {
        return "INSERT INTO " + table() + "(customer, type, balance) VALUES(?, ?, ?)";
    }
    
    default String prepareUpdate()
    {
        return "UPDATE " + table() + " SET customer = ?, type = ?, balance = ? WHERE id = ?";
    }
    
    @Override
    default String prepareRead()
    {
        return "SELECT " + projection() + " FROM " + table() + " WHERE id = ?";
        //return  "SELECT " + projection() + ", " + "customers.* FROM " + table() + " INNER JOIN customers ON accounts.customer = customer.id";
    }
    
    default String prepareDelete()
    {
        return "DELETE FROM " + table() + " WHERE id = ?";
    }

}
