package hotel.core;

public class Expense
{
    private String name;
    private double price;
    
    public Expense(String name, double price)
    {
        if (!name.isEmpty())
            this.name = name;
        else
            throw new IllegalArgumentException("Name must be entered.");
        
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setName(String name)
    {
        if (!name.isEmpty())
            this.name = name;
        else
            throw new IllegalArgumentException("Name must be entered.");
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}
