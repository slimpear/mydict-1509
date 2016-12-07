import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SuperResultSet {
	String foundAtColumn;
	ResultSet rs;
	
	public void printOut() throws Exception {
		//TODO: methode print all word in resultSet : word, id, type, meaning. Type is "foundAtColumn"
		while (rs.next()) {
			System.out.println();
			System.out.println("*********************");
			System.out.println("ID: " +rs.getInt("ID"));
			System.out.println("Word: " +rs.getString("WORD"));
			System.out.println("Type: " +rs.getString("TYPE"));
			System.out.println("Meaning: " +rs.getString("MEANING"));
			System.out.println("*********************");
			System.out.println();
		}
		
	}
}
