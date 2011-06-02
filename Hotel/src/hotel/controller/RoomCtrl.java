package hotel.controller;

import hotel.core.Room;
import hotel.DB.RoomDB;
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
        Room room = new Room(roomNr, meterCost, sqMeters, nrOfBedrooms);
        roomDB.insertRoom(room, hotelName);
        rooms.add(room);
    }
    
    public void removeRoom(int roomNr)
    {
        rooms.remove(roomDB.getRoom(roomNr, hotelName));
        roomDB.delete(roomNr, hotelName);
    }
    
    public Room getRoomById(int id)
    {
        return rooms.get(id);
    }
    
    public Room getRoomByRoomNr(int roomNr)
    {
        return roomDB.getRoom(roomNr, hotelName);
    }
    
    public void editRoom(int id, int roomNr, int meterCost, int sqMeters, 
            int nrOfBedrooms)
    {
        Room oldRoom = rooms.get(id);
        Room newRoom = new Room(roomNr, meterCost, sqMeters, nrOfBedrooms);
        roomDB.updateRoom(newRoom, oldRoom.getRoomNr(), hotelName);
        rooms.set(id, newRoom);
    }
}
