package hotel.controller;

import java.util.ArrayList;
import hotel.core.Hotel;
import hotel.DB.HotelDB;

public class HotelCtrl
{
    private ArrayList<Hotel> hotels;
    private HotelDB hotelDB;
    
    public HotelCtrl()
    {
        hotelDB = new HotelDB();
        hotels = hotelDB.getHotels();
    }
    
    public void addHotel(String name, String address)
    {
        Hotel hotel = new Hotel(name, address);
        hotels.add(hotel);
        hotelDB.insertHotel(hotel);
    }
    
    public void removeHotel(String name)
    {
        hotels.remove(hotelDB.getHotel(name));
        hotelDB.delete(name);
    }
    
    public void editHotel(int id, String name, String address)
    {
        Hotel oldHotel = hotels.get(id);
        Hotel newHotel = new Hotel(name, address);
        hotelDB.updateHotel(newHotel, oldHotel.getName());
        hotels.set(id, newHotel);
    }
    
    public Hotel getHotel(int id)
    {
        return hotels.get(id);
    }
    
    public int getHotelCount()
    {
        return hotels.size();
    }
}
