package org.ssa.tiy.sql.assignmentone;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    
    /**
    *
    * @param customer the new customer to create (any #id parameter will be ignored) <br>
    *                 implementations are not free to modify the incoming parameter; the
    *                 effects should be solely demonstrated in the return argument
    * @return  the new Customer with it's id set, and it should be persisted in the
    *          database
     * @throws SQLException 
    */
   Customer insert(Customer customer);

   /**
    *
    * @param toDelete the customer to delete
    * @return true if the customer was deleted, false if the customer wasn't (e.g. because
    *         no such customer exists in the database)
    */
   boolean delete(Customer toDelete);

   /**
    *
    * @param customer the customer to update <br>
    *                 the customer's id must be set (implementations may throw an exception if it is not)<br>
    *                 implementations are not free to modify the incoming parameter; the
    *                 effects should be solely demonstrated in the return argument
    * @return  the updated Customer as persisted in the
    *          database
    */
   Customer update(Customer customer);

   /**
    *
    * @param id the id of the Customer to return
    * @return the Customer as persisted in the database, or null if no such customer exists
    */
   Customer read(int id);
   

//   /*
//    * OPTIONAL methods for those who have the time/are feeling ambitions
//    * 
//    */
//
//   /**
//    *
//    * @return all customers (order is undefined, but is a 'logical' set) or an empty 
//    *         list if there are no customers - never null
//    */
//   List<Customer> read();
//
//   /**
//    *
//    * @param firstName customers' first-name to match on
//    * @return  all customers (order is undefined, but is a 'logical' set) that match
//    *         <tt>firstName</tt> in a <em>case-insensitive</em> manner or an empty 
//    *         list if there are no customers with such a firstName - never null
//    */
//   List<Customer> readFirstName(String firstName);
//
//   /**
//    *
//    * @param lastName  customers' last-name to match on
//    * @return          all customers (order is undefined, but is a 'logical' set) that match
//    *                  <tt>lastName</tt> in a <em>case-insensitive</em> manner or an empty 
//    *                  list if there are no customers with such a lastName - never null
//
//    */
//   List<Customer> readLastName(String lastName);

   /**
    * removes all customers from persistent storage
    * @return the number of customers removed or 0 if none
    */
   int clear();

}


