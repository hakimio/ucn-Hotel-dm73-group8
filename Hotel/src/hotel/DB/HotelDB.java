package hotel.DB;

import java.sql.*;
import hotel.core.Hotel;
import java.util.ArrayList;

public class HotelDB
{
    private Connection con;
    
    public HotelDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Hotel> getHotels()
    {
        return where("");
    }
        
    public Hotel getHotel(String name)
    {
        return singleWhere("name = \""+name+"\"");
    }
    
    private Hotel singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Hotel hotel = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                hotel = createHotel(results);
            }

            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  hotel;
    }

    private ArrayList<Hotel> where(String wClause)
    {
        ResultSet results;
        ArrayList<Hotel> list = new ArrayList<Hotel>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Hotel hotel = createHotel(results);                
                list.add(hotel);
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
        String query = "SELECT * FROM hotels";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Hotel createHotel(ResultSet rs)
    {
        try
        {
            String name = rs.getString("name");
            String address = rs.getString("address");
            Hotel hotel = new Hotel(name, address);
                        
            return hotel;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public int delete(String name)
    {
        //row count
        int rc = -1;
        String query = "DELETE FROM hotels WHERE name='" + name + "'";
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
    public int insertHotel(Hotel hotel)
    {
        int rc = -1;
        String query = "INSERT INTO hotels(name,address) VALUES('"
                + hotel.getName() + "','" 
                + hotel.getAddress() + "')";
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
    
    public int updateHotel(Hotel hotel, String oldName)
    {
        int rc = -1;
        String query = "Update hotels SET "+
                "name ='" + hotel.getName() + "', "+
                "address ='" + hotel.getAddress() + "' "+
                "WHERE name='" + oldName + "'";
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
