package controller;

import java.util.ArrayList;
import hotel.core.Guest;
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
    
    public void addGuest(String name)
    {
        Guest guest = new Guest(name);
        guests.add(guest);
        guestDB.insertGuest(guest, hotelName);
    }
    
    public void removeGuest(String name)
    {
        guests.remove(guestDB.getGuest(name,hotelName));
        guestDB.delete(name, hotelName);
    }
    
    public void editGuest(int id, String name)
    {
        Guest oldGuest = guests.get(id);
        Guest newGuest = new Guest(name);
        guestDB.updateGuest(newGuest,oldGuest.getName(), hotelName);
        guests.set(id, newGuest);
    }
    
    public int getGuestCount()
    {
        return guests.size();
    }
}
