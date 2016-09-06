package model;

public interface DomainObject 
{
    
    default boolean isLoaded() //true on account, false on account.customer
    {
        return false;
    }
    
    public boolean deeplyEquals(Object object);
}
