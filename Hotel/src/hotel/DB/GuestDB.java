package hotel.DB;

import hotel.core.Guest;
import java.util.ArrayList;
import java.sql.*;

public class GuestDB
{
    private Connection con;
    
    public GuestDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Guest> getGuests()
    {
        return where("");
    }
        
    public Guest getGuest(String name, String hotelName)
    {
        return singleWhere("name = '"+name+"' AND hotelName='"+hotelName +"'");
    }
    
    public ArrayList<Guest> getGuestsByHotel(String name)
    {
        return where("hotelName = '" + name + "'");
    }
    
    private Guest singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Guest guest = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                guest = createGuest(results);
            }

            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  guest;
    }

    private ArrayList<Guest> where(String wClause)
    {
        ResultSet results;
        ArrayList<Guest> list = new ArrayList<Guest>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Guest guest = createGuest(results);                
                list.add(guest);
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
        String query = "SELECT * FROM guests";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Guest createGuest(ResultSet rs)
    {
        try
        {
            String name = rs.getString("name");
            Guest guest = new Guest(name);
            
            return guest;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public int delete(String name, String hotelName)
    {
        //row count
        int rc = -1;
        String query = "DELETE FROM guests WHERE name='" + name + "' AND "
                + "hotelName='" + hotelName + "'";
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
    
    public String getHotelName(Guest guest)
    {
        String query = "SELECT hotelName FROM guests WHERE name = '" + 
                guest.getName() + "'";
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
    
    //@SuppressWarnings("empty-statement")
    public int insertGuest(Guest guest, String hotelName)
    {
        int rc = -1;
        String query = "INSERT INTO guests(name,hotelName) VALUES('"
                + guest.getName() + "','" 
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
    
    public int updateGuest(Guest guest, String oldName, String hotelName)
    {
        int rc = -1;
        String query = "Update guests SET "+
                "name ='" + guest.getName() + "' "+
                "WHERE name='" + oldName + "' AND hotelName='"+hotelName+"'";
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
