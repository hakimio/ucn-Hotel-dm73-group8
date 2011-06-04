package hotel.controller;

import hotel.core.Room;
import hotel.DB.RoomDB;
import hotel.core.Booking;
import java.util.ArrayList;

public class RoomCtrl
{
    private RoomDB roomDB;
    private ArrayList<Room> rooms;
    private String hotelName;
    
    public RoomCtrl(String hotelName)
    {
        this.hotelName = hotelName;
        roomDB = new RoomDB();
        rooms = roomDB.getRoomsByHotel(hotelName);
    }
    
    public void setHotel(String hotelName)
    {
        this.hotelName = hotelName;
        rooms = roomDB.getRoomsByHotel(hotelName);
    }
    
    public String getHotelName()
    {
        return hotelName;
    }
    
    public Room[] getRooms()
    {
        return rooms.toArray(new Room[rooms.size()]);
    }
    
    public int getRoomCount()
    {
        return rooms.size();
    }
    
    public void addRoom(int roomNr, int meterCost, int sqMeters, 
            int nrOfBedrooms)
    {
        if (getRoomByRoomNr(roomNr) != null)
            throw new IllegalStateException("Room with the specified room nr"
                    + " already exists.");
        
        Room room = new Room(roomNr, meterCost, sqMeters, nrOfBedrooms);
        roomDB.insertRoom(room, hotelName);
        rooms.add(room);
    }
    
    public void removeRoom(int roomId)
    {
        int roomNr = rooms.get(roomId).getRoomNr();
        BookingCtrl bookingCtrl = new BookingCtrl(hotelName);
        for (int i = 0; i < bookingCtrl.getBookingCount(); i++)
        {
            Booking booking = bookingCtrl.getBooking(i);
            if (booking.getRoom().getRoomNr() == roomNr)
                bookingCtrl.removeBooking(booking.getId());
        }
        roomDB.delete(roomNr, hotelName);
        rooms.remove(roomId);
    }
    
    public Room getRoomById(int id)
    {
        Room room;
        try
        {
            room = rooms.get(id);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
        
        return room;
    }
    
    public Room getRoomByRoomNr(int roomNr)
    {
        return roomDB.getRoom(roomNr, hotelName);
    }
    
    public void editRoom(int id, int roomNr, int meterCost, int sqMeters, 
            int nrOfBedrooms)
    {
        Room oldRoom = rooms.get(id);
        if (roomNr != oldRoom.getRoomNr() && getRoomByRoomNr(roomNr) != null)
            throw new IllegalStateException("Another room with the specified "
                    + "room nr already exists.");
        Room newRoom = new Room(roomNr, meterCost, sqMeters, nrOfBedrooms);
        roomDB.updateRoom(newRoom, oldRoom.getRoomNr(), hotelName);
        rooms.set(id, newRoom);
    }
}
