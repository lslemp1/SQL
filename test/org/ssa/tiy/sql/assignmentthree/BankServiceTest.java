package org.ssa.tiy.sql.assignmentthree;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;

import dao.AccountImpl;
import dao.CustomerDAO;
import dao.CustomerImpl;
import model.Account;
import model.Account.Type;
import services.BankService;
import services.BankServiceImpl;
import model.Customer;

public class BankServiceTest 
{
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root";  //"&useServerPrepStmts=true";
    CustomerDAO customer;
    AccountImpl account;
    BankService bankService;

    int checkingID, savingsID;
    

    @Before
    public void setup() 
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(URL);
        this.account = new AccountImpl(mysqlDataSource);
        this.customer = new CustomerImpl(mysqlDataSource);
        bankService = new BankServiceImpl(account);
        
        Customer c1 = customer.insert(new Customer("John", "Doe"));
        Account checking = new Account(c1, Type.CH, BigDecimal.valueOf(-5000));
        Account savings = new Account(c1, Type.SA, BigDecimal.valueOf(5000));
        
        account.clear();

        checkingID = account.insert(checking).getId();
        savingsID = account.insert(savings).getId();      
    }

    
    @Test
    public void withdraw()
    {
        Account a = bankService.deposit(savingsID, BigDecimal.valueOf(1000));
        assertTrue(a.getBalance().compareTo(BigDecimal.valueOf(4000)) == 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void withdrawInvalid()
    {
        assertEquals("", new IllegalArgumentException(), bankService.withdraw(savingsID, BigDecimal.valueOf(-8000)).getBalance());
    }
    
    @Test
    public void testDeposit() 
    {    
        assertTrue(bankService.deposit(checkingID, BigDecimal.valueOf(8000)).getBalance().compareTo(BigDecimal.valueOf(3000)) == 0);
        assertTrue(bankService.deposit(savingsID, BigDecimal.valueOf(500)).getBalance().compareTo(BigDecimal.valueOf(5500)) == 0);
    }
    
//    @Test(expected = IllegalArgumentException.class)
//    public void depositInvalid()
//    {
//        assertEquals("", new IllegalArgumentException(), bankService.deposit(savingsID, BigDecimal.valueOf(-500)).getBalance());
//    }
    
    @Test
    public void transfer()
    {
        assertTrue(bankService.transfer(savingsID, checkingID, BigDecimal.valueOf(3000)));
        assertFalse(bankService.transfer(savingsID, checkingID, BigDecimal.valueOf(5001)));
        assertFalse(bankService.transfer(checkingID, savingsID, BigDecimal.valueOf(3000)));
    }
 
}
