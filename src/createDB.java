

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class createDB
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:d-v_20k.db");
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      String sql = "CREATE TABLE DV " +
                   "(ID INT PRIMARY KEY," +
                   " WORD           TEXT, " + 
                   " TYPE           TEXT, " + 
                   " MEANING         TEXT)";   
      stmt.executeUpdate(sql);
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }
}