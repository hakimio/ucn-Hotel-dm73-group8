package hotel.core;

public class Hotel
{
    private String name, address;
    
    public Hotel(String name, String address)
    {
        if (!name.isEmpty() && !address.isEmpty())
        {
            this.name = name;
            this.address = address;
        }
        else
            throw new IllegalArgumentException("Name and address must be "
                    + "entered.");
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        if (!address.isEmpty())
            this.address = address;
        else
            throw new IllegalArgumentException("Address can not be empty.");
    }

    public void setName(String name)
    {
        if (!name.isEmpty())
            this.name = name;
        else
            throw new IllegalArgumentException("Name can not be empty.");
    }
}
