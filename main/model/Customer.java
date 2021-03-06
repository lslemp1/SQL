package model;

public class Customer {
    
    Integer id;
    String first;
    String last;
    boolean isLoaded;
    
    
    public Customer(String first, String last)
    {
        this.first = first;
        this.last = last;
    }
    
    public Customer(int id, String first, String last, boolean isLoaded)
    {
        this.id = id;
        this.first = first;
        this.last = last;
        this.isLoaded = isLoaded;
    }
    
    public Customer()
    {
      
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setFirst(String first) {
        this.first = first;
    }
    
    public String getFirst() {
        return first;
    }
    
    public void setLast(String last) {
        this.last = last;
    }
    
    public String getLast() {
        return last;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", first=" + first + ", last=" + last + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + id;
        result = prime * result + ((last == null) ? 0 : last.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (first == null) {
            if (other.first != null)
                return false;
        } else if (!first.equals(other.first))
            return false;
        if (id != other.id)
            return false;
        if (last == null) {
            if (other.last != null)
                return false;
        } else if (!last.equals(other.last))
            return false;
        return true;
    }
    
    public boolean isLoaded() 
    {
    return false;
    }
    

}
