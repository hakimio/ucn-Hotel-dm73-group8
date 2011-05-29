package hotel.core;

public abstract class Person
{
    protected String name;

    public Person(String name)
    {
        if (!name.isEmpty())
            this.name = name;
        else
            throw new IllegalArgumentException("Name can not be empty.");
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        if (!name.isEmpty())
            this.name = name;
        else
            throw new IllegalArgumentException("Name can not be empty.");
    }
}