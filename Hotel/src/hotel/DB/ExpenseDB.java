package hotel.DB;

import java.util.ArrayList;
import java.sql.*;
import hotel.core.Expense;

public class ExpenseDB
{
    private  Connection con;
    
    public ExpenseDB()
    {
        con = DBConnection.getInstance().getDBConnection();
    }
    
    public ArrayList<Expense> getExpenses()
    {
        return where("");
    }
    
    public ArrayList<Expense> getExpensesByGuest(String name, String hotelName)
    {
        return where("guestName ='"+name+"' AND hotelName ='"+hotelName+"'");
    }
    
    public Expense getExpense(String name, String guestName, String hotelName)
    {
        return singleWhere("name='"+name+"' AND guestName='" + guestName + "'"+
                " AND hotelName='" + hotelName + "'");
    }

    
    private Expense singleWhere(String wClause)
    {
        ResultSet results;
        String query = buildQuery(wClause);
        Expense expense = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                expense = createExpense(results);
            }
            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return  expense;
    }

    private ArrayList<Expense> where(String wClause)
    {
        ResultSet results;
        ArrayList<Expense> list = new ArrayList<Expense>();
        String query = buildQuery(wClause);
        
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            while(results.next())
            {
                Expense expense = createExpense(results);
                list.add(expense);
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
        String query = "SELECT * FROM expenses";
        if (!whereC.isEmpty())
        {
            query = query + " WHERE " + whereC;
        }
        return query;
    }
    
    private Expense createExpense(ResultSet rs)
    {
        try
        {
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            Expense expense = new Expense(name, price);
            
            return expense;
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public int delete(String name, String guestName, String hotelName)
    {
        //row count
        int rc = -1;
        String query = "DELETE FROM expenses WHERE name='"+name + "' AND "
                + "guestName='" + guestName + "' AND hotelName='"+hotelName+"'";
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
    public int insertExpense(Expense expense, String guestName,String hotelName)
    {
        //int nextId = GetMax.getMaxId("select max(id) from customer") + 1;
        int rc = -1;
        String query = "INSERT INTO expenses(name, price, guestName, hotelName)"
                + " VALUES('"
                + expense.getName() + "','"
                + expense.getPrice() + "','"
                + guestName + "','"
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
    
    public String getGuestName(Expense expense, String hotelName)
    {
        String query = "SELECT guestName FROM expenses WHERE name = '" + 
                expense.getName() + "' AND hotelName='" + hotelName + "'";
        ResultSet results;
        String guestName = null;
        try
        {
            Statement stmt = con.createStatement();
            stmt.setQueryTimeout(5);
            results = stmt.executeQuery(query);
            if(results.next())
            {
                guestName = results.getString("guestName");
            }

            stmt.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return guestName;
    }
    
    public int updateExpense(Expense expense, String oldName, String guestName, 
            String hotelName)
    {
        int rc = -1;
        String query = "Update expenses SET "+
                "name ='" + expense.getName() + "', "+
                "price ='" + expense.getPrice() + "' "+
                "WHERE name='" + oldName + "' AND guestName='"+guestName+"' "
                + "AND hotelName='" + hotelName + "'";
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
