package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import model.DomainObject;

public abstract class AbstractDAO<T extends DomainObject>
{
    final DataSource datasource;
    final ORM<T> orm;
    
    protected AbstractDAO(DataSource datasource, ORM<T> orm)
    {
        this.datasource = datasource;
        this.orm = orm;        
    }
    
    static protected void cleanup(ResultSet results, Statement statement, Connection connection)
    {
        try
        {
        if(results != null)
            results.close();
        if(statement != null)
            statement.close();
        if(connection != null)
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException();
            
        }
    }
    static protected void cleanup(Statement statement, Connection connection)
    {
        try
        {
        if(statement != null)
            statement.close();
        if(connection != null)
            connection.close();
        } catch (SQLException e)
        {
            throw new RuntimeException();
        }
    }
    
    public abstract T insert(T domain);
    
    public boolean delete(int id)
    {
        Connection connection = null;
        PreparedStatement delete = null;
        
        try
        {
            connection = this.datasource.getConnection();
            delete = connection.prepareStatement(this.orm.prepareDelete());
            delete.setInt(1, id);
            return delete.executeUpdate() > 0;
        } catch (Exception e)
        {
            return false;
        }
        finally {
            cleanup(delete, connection);
        }
    }
    
    public abstract T update(T domain);
    
    public T read(int id)
    {
        Connection connection = null;
        PreparedStatement read = null;
        ResultSet query = null;
        
        try
        {
            connection = this.datasource.getConnection();
            read = connection.prepareStatement(this.orm.prepareRead());
            read.setInt(1, id);
            query = read.executeQuery();
            if (query.next())
                return this.orm.map(query);
        }
        catch (Exception ex)
        {
           
        }
        finally
        {
            cleanup(query, read, connection);
        }
        return null;
    }
    
    /**
     * Optional method, if subclasses decide to not implement it, need to override
     * the method and throw UnsupportedOperationException
     * @return
     */
    public int clear() throws UnsupportedOperationException
    {
        Connection connection = null;
        Statement clear = null;
        try
        {
           connection = this.datasource.getConnection();
           clear = connection.createStatement();
           return clear.executeUpdate("DELETE FROM " + this.orm.table() + ";");
        }
        catch (Exception ex)
        {

        }
        finally 
        {
            cleanup(clear, connection);
        }
        return 0;
    }    
    
    protected Connection getConnection() throws SQLException
    {
        return this.datasource.getConnection();
    }


}
