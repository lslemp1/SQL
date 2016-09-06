package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Account;

public class AccountImpl extends AbstractDAO<Account>
{    
    public AccountImpl(DataSource datasource, ORM<Account> orm)
    {
        super(datasource, orm);
    }    
    
    public AccountImpl(DataSource datasource) 
    {
    this(datasource, new AccountORM() {});
    }
    
    @Override
    public Account insert(Account account) 
    {
        Connection connection = null;
        PreparedStatement insert = null;
        ResultSet generatedKeys = null;
        
        try {
            connection = datasource.getConnection();
            insert = connection.prepareStatement(this.orm.prepareInsert(), Statement.RETURN_GENERATED_KEYS);
            insert.setInt(1, account.getCust());
            insert.setString(2, account.getType().toString());
            insert.setBigDecimal(3, account.getBalance());
            insert.executeUpdate();
            generatedKeys = insert.getGeneratedKeys();
            if(generatedKeys.next())
            {
            Account acct = new Account(generatedKeys.getInt(1), account.getCust(), account.getType(), account.getBalance());  
            System.err.println("inserted account with id " + generatedKeys.getInt(1));
            return acct;
            }
        } catch (SQLException e) {
//            System.err.println("Cannot insert account.");
            throw new RuntimeException();
        }
        finally{
            cleanup(generatedKeys, insert, connection);
        }
        return null;
    }


    @Override
    public Account update(Account account) 
    {
        Connection connection = null;
        PreparedStatement update = null;
        
        try 
        {
            connection = datasource.getConnection();
            update = connection.prepareStatement(orm.prepareUpdate());
            update.setInt(1, account.getCust());
            update.setString(2, account.getType().toString());
            update.setBigDecimal(3, account.getBalance());
            update.setInt(4, account.getId());
            update.executeUpdate();
            if (update.executeUpdate() == 0)
                return null;
                connection.close();
                return account;
        } catch (SQLException e) {
            System.err.println("Cannot update customer.");
            return null;
        }        
        finally{
            cleanup(update, connection);
        }
    }
    
    /**
    *
    * @param user
    * @return all the accounts for given user
    */
   public List<Account> readUser(int user)
   {
       Connection connection = null;
       PreparedStatement readUser = null;
       List<Account> accounts = new ArrayList<>();
       
       try {
           connection = datasource.getConnection();
           readUser = connection.prepareStatement("SELECT " + orm.projection() + " FROM " + orm.table() + " WHERE customer = " + user);
           ResultSet results = readUser.executeQuery();
           while (results.next())
           accounts.add(orm.map(results));
           return accounts;
       } catch (SQLException e) {
           throw new RuntimeException();
       }
       finally{
           cleanup(readUser, connection);
       }
   }

   /**
    *
    * @return those accounts that have a negative balance - never null
    */
   public List<Account> readUnderwater()
   {
       Connection connection = null;
       PreparedStatement readUnderwater = null; 
       List<Account> accounts = new ArrayList<>();
       try {
           connection = datasource.getConnection();
           readUnderwater = connection.prepareStatement("SELECT " + orm.projection() + " FROM " + orm.table() + " WHERE balance < 0");
           ResultSet results = readUnderwater.executeQuery();
           while (results.next())
           accounts.add(orm.map(results));
           return accounts;
       } catch (SQLException e) {
           throw new RuntimeException();
   }
       finally{
           cleanup(readUnderwater, connection);
       }
}
}
