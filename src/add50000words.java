

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class add50000words {

	public static void main(String[] args) {
		int a = 1;
		// TODO Auto-generated method stub
		Connection c = null;
	    Statement stmt = null;
	    System.out.println("Opened database successfully");
	    for (int i=1; i<20000; i++) {
		    try {
		    	Class.forName("org.sqlite.JDBC");
		    	c = DriverManager.getConnection("jdbc:sqlite:d-v_20k.db");
				      stmt = c.createStatement();
		    	  stmt.executeUpdate("INSERT INTO DV (ID, WORD, TYPE, MEANING) VALUES ('" +a+ "', 'WordNumber " +a+ "', 'HereIsTYPE', 'HereIsBEDEUTUNG');");
		    	  System.out.println(a);
		    	  a++;
		    	  
			      stmt.close();
			      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
	    }
	  }
	}