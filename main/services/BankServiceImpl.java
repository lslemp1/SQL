package services;

import java.math.BigDecimal;

import dao.AccountImpl;
import model.Account;

public class BankServiceImpl implements BankService 
{

    AccountImpl acct;
    Account updatedAcct;
    BigDecimal update;

    public BankServiceImpl(AccountImpl acct) 
    {
        this.acct = acct;
    }

    @Override
    public Account withdraw(int account, BigDecimal amount) throws IllegalArgumentException 
    {
        updatedAcct = acct.read(account);
        update = updatedAcct.getBalance().subtract(amount);

        if (amount.compareTo(updatedAcct.getBalance()) < 0)
            throw new IllegalArgumentException();
        
        updatedAcct.setBalance(update);

        return acct.update(updatedAcct);
    }

    @Override
    public Account deposit(int account, BigDecimal amount) 
    {
        if(amount.compareTo(BigDecimal.ZERO) == -1)
            throw new IllegalArgumentException();
        
        updatedAcct = acct.read(account);
        update = updatedAcct.getBalance().add(amount);
        updatedAcct.setBalance(update);
        return acct.update(updatedAcct);
    }

    @Override
    public boolean transfer(int from, int to, BigDecimal amount) throws IllegalArgumentException 
    {
        Account fromAcct = acct.read(from);
        Account toAcct = acct.read(to);
        if (amount.compareTo(fromAcct.getBalance()) == 1)
            return false;
        else {
            fromAcct.setBalance(fromAcct.getBalance().subtract(amount));
            toAcct.setBalance(toAcct.getBalance().add(amount));
        }
        return true;
    }

}
