package hotel.DB;

import java.util.ArrayList;
import java.sql.*;
import hotel.core.Room;

public class RoomDB
{
    private Connection con;
    
    public RoomDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Room> getRooms()
    {
        return where("");
    }
    
    public Room getRoom(int roomNr)
    {
        return singleWhere("roomNr=" + roomNr);
    }
        
    private Room singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Room room = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                room = createRoom(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  room;
    }

    private ArrayList<Room> where(String wClause)
    {
        ResultSet results;
        ArrayList<Room> list = new ArrayList<Room>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Room room = createRoom(results);
                list.add(room);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return list;
    }

    private String buildQuery(String whereC)
    {
        String query = "SELECT * FROM rooms";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Room createRoom(ResultSet rs)
    {
        try
        {
            int roomNr = rs.getInt("roomNr");
            int meterCost = rs.getInt("meterCost");
            int sqMeters = rs.getInt("sqMeters");
            int nrOfBedrooms = rs.getInt("nrOfBedrooms");
            Room room = new Room(roomNr, meterCost, sqMeters, nrOfBedrooms);
            
            return room;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public int delete(int roomNr)
    {
        //row count
        int rc = -1;
        String query = "DELETE FROM rooms WHERE roomNr=" + roomNr;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            rc = stmt.executeUpdate(query);
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return rc;
    }
    //@SuppressWarnings("empty-statement")
    public int insertRoom(Room room, String hotelName)
    {
        //int nextId = GetMax.getMaxId("select max(id) from customer") + 1;
        int rc = -1;
        String query = "INSERT INTO rooms(roomNr, meterCost, sqMeters, "
                + "nrOfBedrooms, hotelName) VALUES('"
                + room.getRoomNr() + "','" 
                + room.getMeterCost() + "','" 
                + room.getSqMeters() + "','" 
                + room.getNrOfBedrooms() + "','" 
                + hotelName + "')";
        try
        {
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            rc = stmt.executeUpdate(query);
            stmt.close();
            con.commit();
            con.setAutoCommit(true);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            try
            {
                rc = -1;
                con.rollback();
                con.setAutoCommit(true);
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        return rc;
    }
    
    public String getHotelName(Room room)
    {
        String query = "SELECT hotelName FROM rooms WHERE roomNr = '" + 
                room.getRoomNr() + "'";
        ResultSet results;
        String hotelName = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                hotelName = results.getString("hotelName");
            }

            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return hotelName;
    }
    
    public int updateRoom(Room room, String oldRoomNr)
    {
        int rc = -1;
        
        String query = "Update rooms SET " +
                "roomNr ='" + room.getRoomNr() + "', " +
                "meterCost ='" + room.getMeterCost() + "', " +
                "sqMeters ='" + room.getSqMeters() + "', " +
                "nrOfBedrooms ='" + room.getNrOfBedrooms() + "', "+
                "WHERE roomNr="+ oldRoomNr;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            rc = stmt.executeUpdate(query);
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rc;
    }
}
