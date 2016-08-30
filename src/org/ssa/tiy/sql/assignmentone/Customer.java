package org.ssa.tiy.sql.assignmentone;

public class Customer {
    
    private int id;
    private String first;
    private String last;
    
    
    public Customer(String first, String last)
    {
        this.first = first;
        this.last = last;
    }
    
    public Customer()
    {
      
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    void setFirst(String first) {
        this.first = first;
    }
    
    public String getFirst() {
        return first;
    }
    
    void setLast(String last) {
        this.last = last;
    }
    
    public String getLast() {
        return last;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", first=" + first + ", last=" + last + "]";
    }
    

}
