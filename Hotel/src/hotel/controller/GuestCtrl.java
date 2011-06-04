package hotel.controller;

import java.util.ArrayList;
import hotel.core.Guest;
import hotel.core.Expense;
import hotel.DB.GuestDB;

public class GuestCtrl
{
    private String hotelName;
    private ArrayList<Guest> guests;
    private GuestDB guestDB;
    
    public GuestCtrl(String hotelName)
    {
        this.hotelName = hotelName;
        guestDB = new GuestDB();
        guests = guestDB.getGuestsByHotel(hotelName);
    }
    
    public String getHotelName()
    {
        return hotelName;
    }
    
    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
        guests = guestDB.getGuestsByHotel(hotelName);
    }
    
    public Guest getGuestById(int id)
    {
        return guests.get(id);
    }
    
    public Guest getGuestByName(String name)
    {
        return guestDB.getGuest(name, hotelName);
    }
    
    public void addGuest(String name)
    {
        if (getGuestByName(name) != null)
            throw new IllegalStateException("Guest with specified name already"
                    + " exists.");
        Guest guest = new Guest(name);
        guests.add(guest);
        guestDB.insertGuest(guest, hotelName);
    }
    
    public void removeGuest(int id)
    {
        Guest guest = guests.get(id);
        ExpenseCtrl expenseCtrl = new ExpenseCtrl(guest.getName());
        for(int i = 0; i < expenseCtrl.getExpenseCount(); i++)
        {
            expenseCtrl.removeExpense(0);
        }
        guestDB.delete(guest.getName(), hotelName);
        guests.remove(id);
    }
    
    public void editGuest(int id, String name)
    {
        Guest oldGuest = guests.get(id);
        if (!name.equals(oldGuest.getName()) && getGuestByName(name) != null)
            throw new IllegalStateException("Another guest with the specified"
                    + " name already exists.");
        Guest newGuest = new Guest(name);
        guestDB.updateGuest(newGuest,oldGuest.getName(), hotelName);
        guests.set(id, newGuest);
    }
    
    public int getGuestCount()
    {
        return guests.size();
    }
}
