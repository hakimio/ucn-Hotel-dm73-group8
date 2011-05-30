package hotel.DB;

import java.sql.*;

public class DBConnection
{
    private static final String user = "tomas";
    private static final String pass = "samot";
    private static final String driver = "jdbc:mysql://localhost/";
    private static final String dbName = "AHPI";
    private static Connection con = null;
    
    private static DBConnection instance = null;
    
    private DBConnection()
    {
        String url = driver + dbName;
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection established!");
            con.setAutoCommit(true);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static void closeConnection()
    {
        try
        {
            con.close();
            System.out.println("Connection closed.");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public Connection getDBConnection()
    {
        return con;
    }
    
    public static DBConnection getInstance()
    {
        if (instance == null)
            instance = new DBConnection();
        
        return instance;
    }
}
