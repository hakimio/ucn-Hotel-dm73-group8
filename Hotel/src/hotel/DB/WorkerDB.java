package hotel.DB;

import hotel.core.Worker;
import java.util.ArrayList;
import java.sql.*;

public class WorkerDB
{
    private Connection con;
    
    public WorkerDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Worker> getWorkers()
    {
        return where("");
    }
    
    public Worker getWorker(String name)
    {
        return singleWhere("name='"+name+"'");
    }
    
    public ArrayList<Worker> getWorkersByHotel(String name)
    {
        return where("hotelName ='" + name + "'");
    }
        
    private Worker singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Worker worker = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                worker = createWorker(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  worker;
    }

    private ArrayList<Worker> where(String wClause)
    {
        ResultSet results;
        ArrayList<Worker> list = new ArrayList<Worker>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Worker worker = createWorker(results);
                list.add(worker);
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
        String query = "SELECT * FROM workers";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Worker createWorker(ResultSet rs)
    {
        try
        {
            String name = rs.getString("name");
            String position = rs.getString("position");
            Date birthDate = rs.getDate("birthDate");
            Date startedWorking = rs.getDate("startedWorking");
            int income = rs.getInt("income");
            Worker worker = new Worker(name, birthDate, startedWorking, income, 
                    position);
            
            return worker;
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
        String query = "DELETE FROM workers WHERE name='" + name + "'";
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
    public int insertWorker(Worker worker, String hotelName)
    {
        //int nextId = GetMax.getMaxId("select max(id) from customer") + 1;
        int rc = -1;
        String query = "INSERT INTO workers(name, birthDate, startedWorking, "
                + "income, position, hotelName) VALUES('"
                + worker.getName() + "','" 
                + new Date(worker.getBirthDate().getTime()) + "','" 
                + new Date(worker.getStartedWorking().getTime()) + "','" 
                + worker.getIncome() + "','" 
                + worker.getPosition() + "','" 
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
    
    public String getHotelName(Worker worker)
    {
        String query = "SELECT hotelName FROM workers WHERE name = '" + 
                worker.getName() + "'";
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
    
    public int updateWorker(Worker worker, String oldName)
    {
        int rc = -1;
        Date birthDate = new Date(worker.getBirthDate().getTime());
        Date startedWorking = new Date(worker.getStartedWorking().getTime());
        
        String query = "Update workers SET " +
                "name ='" + worker.getName() + "', " +
                "birthDate ='" + birthDate + "', " +
                "startedWorking ='" + startedWorking + "', " +
                "position ='" + worker.getPosition() + "', "+
                "income ='" + worker.getIncome() + "' "+
                "WHERE name= '"+ oldName +"'";
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
