package hotel.core;

import java.util.Date;

public class Booking
{
    private int id;
    private Room room;
    private Guest guest;
    private Date arrivalDate, leavingDate;
    private int discount;
    
    public Booking(int id, Room room, Guest guest, Date arrivalDate, Date leavingDate,
            int discount)
    {
        if (arrivalDate.compareTo(leavingDate) == 0)
            throw new IllegalArgumentException("Arrival date can not be the " +
                    "same as leaving date");
        if(leavingDate.compareTo(arrivalDate) < 0)
            throw new IllegalArgumentException("Leaving date can not be before"+
                    " arrival date.");
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.discount = discount;
        this.arrivalDate = arrivalDate;
        this.leavingDate = leavingDate;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setLeavingDate(Date leavingDate)
    {
        if (arrivalDate.compareTo(leavingDate) == 0)
            throw new IllegalArgumentException("Arrival date can not be the " +
                    "same as leaving date");
        if(leavingDate.compareTo(arrivalDate) < 0)
            throw new IllegalArgumentException("Leaving date can not be before"+
                    " arrival date.");
        
        this.leavingDate = leavingDate;
    }
    
    public void setArrivalDate(Date arrivalDate)
    {
        if (arrivalDate.compareTo(leavingDate) == 0)
            throw new IllegalArgumentException("Arrival date can not be the " +
                    "same as leaving date");
        
        this.arrivalDate = arrivalDate;
    }

    public void setDiscount(int discount)
    {
        this.discount = discount;
    }

    public void setGuest(Guest guest)
    {
        this.guest = guest;
    }

    public void setRoom(Room room)
    {
        this.room = room;
    }

    public Date getArrivalDate()
    {
        return arrivalDate;
    }

    public int getDiscount()
    {
        return discount;
    }

    public Guest getGuest()
    {
        return guest;
    }

    public Date getLeavingDate()
    {
        return leavingDate;
    }

    public Room getRoom()
    {
        return room;
    }
}
