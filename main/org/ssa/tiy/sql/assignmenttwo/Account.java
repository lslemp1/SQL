package org.ssa.tiy.sql.assignmenttwo;

import java.math.BigDecimal;

import org.ssa.tiy.sql.assignmentone.Customer;

public class Account implements DomainObject 
{
    int id;
    int customer;
    Type type;
    BigDecimal balance;

    public Account(int id, int customer, Type type, BigDecimal balance) {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.balance = balance;
    }
    
    public Account(Customer customer, Type type, BigDecimal balance) {
        this.customer = customer.getId();
        this.type = type;
        this.balance = balance;
    }

    public Account() {

    }

    public enum Type {
        SA, CH;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCust() {
        return customer;
    }

    public void setCust(int customer) {
        this.customer = customer;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
//    @Override
//    public Account clone()
//    {
//        try
//        {
//            Account copy = (Account) super.clone();
//            copy.setCust(this.cust.clone());
//            return copy;
//        }
//        catch(CloneNotSupportedException ex)
//        {
//            return null;
//        }
//        
//    }

}
