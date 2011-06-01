package hotel.controller;

import java.util.ArrayList;
import hotel.core.*;
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
        Hotel hotel = hotelDB.getHotel(name);
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
            Guest guest = guestCtrl.getGuestById(0);
            guestCtrl.removeGuest(guest.getName());
        }
        for (int i = 0; i < roomCtrl.getRoomCount(); i++)
        {
            Room room = roomCtrl.getRoomById(0);
            roomCtrl.removeRoom(room.getRoomNr());
        }
        for (int i = 0; i < workerCtrl.getWorkerCount(); i++)
        {
            Worker worker = workerCtrl.getWorkerById(0);
            workerCtrl.removeWorker(worker.getName());
        }
        
        hotels.remove(hotel);
        hotelDB.delete(name);
    }
    
    public Hotel getHotelByName(String name)
    {
        return hotelDB.getHotel(name);
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
    
    public int getId(Hotel hotel)
    {
        return hotels.indexOf(hotel);
    }
    
    public int getHotelCount()
    {
        return hotels.size();
    }
}
