package hotel.controller;

import java.util.ArrayList;
import hotel.core.*;
import hotel.DB.HotelDB;

public class HotelCtrl
{
    private HotelDB hotelDB;
    
    public HotelCtrl()
    {
        hotelDB = new HotelDB();
    }
    
    public void addHotel(String name, String address)
    {
        if (getHotelByName(name) != null)
            throw new IllegalStateException("Hotel with the specified name "
                    + "already exists");
        Hotel hotel = new Hotel(name, address);
        hotelDB.insertHotel(hotel);
    }
    
    public void removeHotel(String name)
    {
        BookingCtrl bookingCtrl = new BookingCtrl(name);
        GuestCtrl guestCtrl = new GuestCtrl(name);
        RoomCtrl roomCtrl = new RoomCtrl(name);
        WorkerCtrl workerCtrl = new WorkerCtrl(name);
        
        for(int i = 0; i < bookingCtrl.getBookingCount(); i++)
        {
            Booking booking = bookingCtrl.getBooking(0);
            bookingCtrl.removeBooking(booking.getId());
        }
        for (int i = 0; i < guestCtrl.getGuestCount(); i++)
        {
            guestCtrl.removeGuest(0);
        }
        for (int i = 0; i < roomCtrl.getRoomCount(); i++)
        {
            roomCtrl.removeRoom(0);
        }
        for (int i = 0; i < workerCtrl.getWorkerCount(); i++)
        {
            Worker worker = workerCtrl.getWorkerById(0);
            workerCtrl.removeWorker(worker.getName());
        }
        
        hotelDB.delete(name);
    }
    
    public Hotel getHotelByName(String name)
    {
        return hotelDB.getHotel(name);
    }
    
    public Hotel editHotel(String oldName, String name, String address)
    {
        if (!name.equals(oldName) && getHotelByName(name) != null)
            throw new IllegalStateException("Another hotel with the specified"
                    + " name already exists.");
        
        Hotel newHotel = new Hotel(name, address);
        hotelDB.updateHotel(newHotel, oldName);
        return newHotel;
    }
    
    public Hotel[] getHotels()
    {
        ArrayList<Hotel> hotels = hotelDB.getHotels();
        
        return hotels.toArray(new Hotel[hotels.size()]);
    }
}
