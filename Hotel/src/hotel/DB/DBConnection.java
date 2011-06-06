package hotel.DB;

import java.sql.*;

public class DBConnection
{
    private static final String user = "DM73_8";
    private static final String pass = "MaaGodt";
    private static final String driver = "jdbc:sqlserver://balder.ucn.dk:1433";
    private static final String dbName = ";databaseName=DM73_8";
    private static Connection con = null;
    
    private static DBConnection instance = null;
    
    private DBConnection()
    {
        String url = driver + dbName;
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
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
