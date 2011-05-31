package hotel.controller;

import java.util.ArrayList;
import java.util.Date;
import hotel.core.Booking;
import hotel.core.Guest;
import hotel.core.Room;
import hotel.DB.BookingDB;

public class BookingCtrl
{
    private String hotelName;
    private ArrayList<Booking> bookings;
    private BookingDB bookingDB;
    
    public BookingCtrl(String hotelName)
    {
        this.hotelName = hotelName;
        bookingDB = new BookingDB();
        bookings = bookingDB.getBookingsByHotel(hotelName);
    }
    
    public String getHotelName()
    {
        return hotelName;
    }
    
    public void setHotelName(String hotelName)
    {
        this.hotelName = hotelName;
        bookings = bookingDB.getBookingsByHotel(hotelName);
    }
    
    public void addBooking(int id, Room room, Guest guest, Date arrivalDate, 
            Date leavingDate, int discount)
    {
        if (arrivalDate.before(new Date()))
            throw new IllegalStateException("Arrival date can not be in "
                    + "the past.");
        
        for (int i = 0; i < bookings.size(); i++)
        {
            Booking booking = bookings.get(i);
            
            if (arrivalDate.after(booking.getArrivalDate()) && 
                    arrivalDate.before(booking.getLeavingDate()))
                throw new IllegalStateException("The room will not be "
                        + "available at specified arrival date.");
            
            if(leavingDate.after(booking.getArrivalDate()) && 
                    leavingDate.before(booking.getLeavingDate()))
                throw new IllegalStateException("There are some other guests "
                        + "who will be staying at the specified period.");
        }
        
        double totalPrice = room.getCost();
        ExpenseCtrl expenseCtrl = new ExpenseCtrl(guest.getName());
        
        totalPrice += expenseCtrl.getTotalExpenses();
        
        if (discount > totalPrice)
            throw new IllegalStateException("Discount can not be higher than"
                    + " total price.");
        
        Booking booking = new Booking(id, room, guest, arrivalDate, leavingDate, 
                discount);
        bookings.add(booking);
        bookingDB.insertBooking(booking, hotelName);
    }
    
    public void editBooking(int id, Room room, Guest guest, Date arrivalDate, 
            Date leavingDate, int discount)
    {
        for (int i = 0; i < bookings.size(); i++)
        {
            Booking booking = bookings.get(i);
            
            if (arrivalDate.after(booking.getArrivalDate()) && 
                    arrivalDate.before(booking.getLeavingDate()))
                throw new IllegalStateException("The room will not be "
                        + "available at specified arrival date.");
            
            if(leavingDate.after(booking.getArrivalDate()) && 
                    leavingDate.before(booking.getLeavingDate()))
                throw new IllegalStateException("There are some other guests "
                        + "who will be staying at the specified period.");
        }
        
        double totalPrice = room.getCost();
        ExpenseCtrl expenseCtrl = new ExpenseCtrl(guest.getName());
        
        totalPrice += expenseCtrl.getTotalExpenses();
        
        if (discount > totalPrice)
            throw new IllegalStateException("Discount can not be higher than"
                    + " total price.");
        
        Booking booking = new Booking(id, room, guest, arrivalDate, leavingDate, 
                discount);
        bookingDB.updateBooking(booking, hotelName);
        bookings.set(id, booking);
    }
    
    public void removeBooking(int bookingId)
    {
        bookings.remove(bookingDB.getBooking(bookingId,hotelName));
        bookingDB.delete(bookingId, hotelName);
    }
    
    public Booking getBooking(int id)
    {
        return bookings.get(id);
    }
    
    public int getBookingCount()
    {
        return bookings.size();
    }
}
