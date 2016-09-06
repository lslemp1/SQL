package model;

import java.math.BigDecimal;

public class Account implements DomainObject 
{
    int id;
    Integer customer;
    Type type;
    BigDecimal balance;
    boolean isLoaded = false;

    public Account(int id, int customer, Type type, BigDecimal balance) 
    {
        this.id = id;
        this.customer = customer;
        this.type = type;
        this.balance = balance;
    }

    public Account(Customer customer, Type type, BigDecimal balance) 
    {
        this.customer = customer.getId();
        this.type = type;
        this.balance = balance;
    }

    public Account() {

    }

    public enum Type {
        SA, CH;
    }

    public Integer getId() {
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

    public void isLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @Override
    public boolean deeplyEquals(Object obj) 
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (balance == null) {
            if (other.balance != null)
                return false;
        } else if (!(balance.compareTo(other.balance) == 0))
            return false;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (id != other.id)
            return false;
        if (type != other.type)
            return false;
        return true;
                    
    }

    // @Override
    // public Account clone()
    // {
    // try
    // {
    // Account copy = (Account) super.clone();
    // copy.setCust(this.cust.clone());
    // return copy;
    // }
    // catch(CloneNotSupportedException ex)
    // {
    // return null;
    // }
    //
    // }

}
