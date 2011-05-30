package hotel.core;

import java.util.ArrayList;
import java.util.Arrays;

public class Hotel
{
    private String name, address;
    private ArrayList<Room> rooms;
    private ArrayList<Guest> guests;
    private ArrayList<Worker> workers;
    private ArrayList<Booking> bookings;
    
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
        rooms = new ArrayList<Room>();
        guests = new ArrayList<Guest>();
        workers = new ArrayList<Worker>();
        bookings = new ArrayList<Booking>();
    }
    
    public Hotel(String name, String address, Room[] rooms)
    {
        this(name, address);
        this.rooms.addAll(Arrays.asList(rooms));
    }
    
    public Hotel(String name, String address, Room[] rooms, Worker[] workers)
    {
        this(name, address, rooms);
        addWorkers(workers);
    }
    
    public boolean addBooking(Booking booking)
    {
        return bookings.add(booking);
    }
    
    public boolean removeBooking(Booking booking)
    {
        return bookings.remove(booking);
    }
    
    public Booking getBookingById(int id)
    {
        return bookings.get(id);
    }
    
    public boolean addRoom(Room room)
    {
        return rooms.add(room);
    }
    
    public boolean removeRoom(Room room)
    {
        return rooms.remove(room);
    }
    
    public Room getRoomById(int id)
    {
        return rooms.get(id);
    }
    
    public final void addWorkers(Worker[] workers)
    {
        for (int i = 0; i < workers.length; i++)
        {
            addWorker(workers[i]);
        }
    }
    
    public boolean addWorker(Worker worker)
    {
        return workers.add(worker);
    }
    
    public boolean removeWorker(Worker worker)
    {
        return workers.remove(worker);
    }
    
    public boolean addGuest(Guest guest)
    {
        return guests.add(guest);
    }
    
    public boolean removeGuest(Guest guest)
    {   
        return guests.remove(guest);
    }
     
    public Worker getWorkerById(int id)
    {
        return workers.get(id);
    }
    
    public int getRoomCount()
    {
        return rooms.size();
    }

    public int getWorkerCount()
    {
        return workers.size();
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
