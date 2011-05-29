package hotel.core;

import java.util.Date;

public class Booking
{
    private Room room;
    private Guest guest;
    private Date arrivalDate, leavingDate;
    private int discount;
    
    public Booking(Room room, Guest guest, Date arrivalDate, Date leavingDate,
            int discount)
    {
        if (arrivalDate.compareTo(new Date()) < 0)
            throw new IllegalArgumentException("Arrival date can not be in the"+
                    " past.");
        if (arrivalDate.compareTo(leavingDate) == 0)
            throw new IllegalArgumentException("Arrival date can not be the " +
                    "same as leaving date");
        if(leavingDate.compareTo(arrivalDate) < 0)
            throw new IllegalArgumentException("Leaving date can not be before"+
                    " arrival date.");
        
        this.room = room;
        this.guest = guest;
        this.discount = discount;
        this.arrivalDate = arrivalDate;
        this.leavingDate = leavingDate;
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
