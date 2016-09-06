package dao;

import java.util.List;

import model.Account;

public interface AccountDAO extends DAO<Account>
{

    public Account insert(Account account);
    
    public Account update(Account account);
    
    public boolean delete(int id);
    
    public Account read(int id);
  
    public List<Account> readUser(int user);
    
    public List<Account> readUnderwater();

    public int clear();

}
