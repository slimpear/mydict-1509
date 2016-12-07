import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;

public class RunMeFirst {
	
	SuperResultSet sRs;
	
	static String[] columnList_ID ={"ID"};
	static String[] columnList_WORD = {"WORD"};
	static String[] columnList_full = {"ID", "WORD", "TYPE", "MEANING"};
	
	static Connection c = null;
	
	public static void main(String[] args) {
		int choose;
	    //Open DB
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:d-v_20k.db");
	        System.out.println("Opened database successfully");
	     } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	     }
	      
	    //running methodes:
	    loopwhile : while (true) {
	    	Scanner scanner = new Scanner(System.in);
		    System.out.println("Choose a number: \n1. findFromDB.\n2. addToDB.\n3. removeFromDB.\n4. updateFromDB.\n5. Quit.");
		    choose = Integer.parseInt(scanner.nextLine());
		    switch (choose) {
		    case 1:
		    //TODO: FIND A WORD FROM DB
		    	try {
			    	System.out.println("Word to find: ");
			    	String wtf = scanner.nextLine();
			    	LinkedList<SuperResultSet> ListofsRs = findWord(wtf, "DV", columnList_WORD);
			    	
			    	if (ListofsRs.isEmpty()) {
			    		System.out.println("### No such word found. ###");
			    		break;	
			    	} else {	
			    		for (SuperResultSet sRs : ListofsRs) 
			    			sRs.printOut();	
			    		System.out.println("###   Search done.  ###");			    		
			    		break;
			    	}
			    	
		    	}  catch ( Exception e ) {
			        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			        System.exit(0);
			     }
		    case 2:
		    //TODO: ADD A WORD TO DB
		    	try {
			    	SingleWord sw = new SingleWord();
			    	System.out.println("***  Enter your Word  ***");
			    	sw.word = scanner.nextLine();
			    	
			    	LinkedList<SuperResultSet> ListIfAvailable = findWord(sw.word, "DV", columnList_WORD);
			    	
			    	if (!ListIfAvailable.isEmpty()) {
			    		System.out.println("There are some results available...");
			    		//
			    		for (SuperResultSet sRs : ListIfAvailable) 
			    			sRs.printOut();
			    		//
			    		break;
			    	} else {
				    	System.out.println("***  Enter Type  ***");
				    	sw.type =scanner.nextLine();
				    	System.out.println("*** Enter Meaning  ***");
				    	sw.meaning = scanner.nextLine();
				    	///////////
				    	addToDatabase(sw, "DV");
				    	System.out.println("Add " +sw.word+ " to DB done!");
				    	System.out.println();
			    	}
		    	}catch ( Exception e ) {
			        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			        System.exit(0);
			     }
		    case 3:	
		    	try {
		    		SingleWord sw = new SingleWord();
		    		System.out.println("***  Enter word to delete:  ***");
		    		sw.word = scanner.nextLine();
		    		LinkedList<SuperResultSet> ListifAvailable = findWord(sw.word, "DV", columnList_WORD);
		    		
		    		if (ListifAvailable.isEmpty()) {
		    			System.out.println("###  NO such word in database  ###");
		    			break;
		    		} else {
		    			for (SuperResultSet sRs : ListifAvailable) 
		    				sRs.printOut();
		    			
		    			System.out.println("***  Enter ID of word you want to remove  ***");
		    			sw.id = scanner.nextInt();
		    			LinkedList<SuperResultSet> checkList = find99Acc(sw.id, sw.word, "DV");
		    			
		    			if (checkList.isEmpty()) {
		    				System.out.println("###  Please check your ID  ####");
		    				break;
		    			} else {
		    				removeFromDatabase(sw.id, sw.word, "DV");
		    				System.out.println("###  Remove " +sw.word+ " with ID = " +sw.id+ " done!  ###");
		    			}
		    		}
		    		
		    	} catch (Exception e) {
		    		System.err.println(e.getClass().getName() + ": " +e.getMessage());
		    		System.exit(0);
		    	}
		    	System.out.println("removeFromDB Done!");
		    	break;
		    case 4:
		    //TODO: update a word in Database.
		    	SingleWord sw = new SingleWord();
		    	SingleWord sW_old = new SingleWord();
		    	System.out.println("***  Enter yor Word you want to mod  ***");
		    	sW_old.word = scanner.nextLine();
		    	try {
			    	LinkedList<SuperResultSet> checkList = findWord(sW_old.word, "DV", columnList_WORD);
			    	
			    	if (checkList.isEmpty()) {
			    		System.out.println("###  No such word found  ###");
			    		break;
			    	} else {
			    		for (SuperResultSet sRs : checkList)
			    			sRs.printOut();
			    		System.out.println("***  Enter ID you want to mod  ***");
			    		sW_old.id = scanner.nextInt();
			    		//Check if ID and WORD return a result
			    		ResultSet rs = find100Acc(sW_old, "DV");
			    		if (rs.isBeforeFirst()) {
			    			sW_old = resultSet_toSingleWord(rs);
			    			////////////TODO: not done, but want to do something difference.
			    			//////////// Waiting for the UI and UMFRAGE.
			    			
			    		}
			    		
			    	}
		    	} catch (Exception e) {
		    		System.err.println(e.getClass().getName() + ": " +e.getMessage());
		    		System.exit(0);
		    	}
		    	
		    	System.out.println("updateFromDB Done!");
		    	break;
		    case 5:
		    	System.out.println("I'm quit.");
		    	break loopwhile;
		    default: 
		    }
	    }	    
	}
	
	public static LinkedList<SuperResultSet> findWord(String findwhat, String table_name, String[] columnList) {
		//TODO: methode to find a word from table_name in database.
	    LinkedList<SuperResultSet> ll = new LinkedList<SuperResultSet>();
	    try {
		    Statement stmt = c.createStatement();
		    //
	    	for (String column : columnList) {
			    	ResultSet r = stmt.executeQuery("SELECT * FROM " +table_name+ " WHERE " +column+ " LIKE '" +findwhat+ "';");;
			    	if (!r.isBeforeFirst()) {
			    		continue;
			    	} else {
			    		SuperResultSet srs = new SuperResultSet();
			    		srs.rs = r;
			    		srs.foundAtColumn = column;
			    		ll.add(srs);
			    		
		    		}
	    	}
	    } catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
    	return ll;
	}
	
	public static LinkedList<SuperResultSet> find99Acc(int id, String findwhat, String table_name) {
		//TODO: methode to find a word from table_name in database.
	    LinkedList<SuperResultSet> ll = new LinkedList<SuperResultSet>();
	    try {
		    Statement stmt = c.createStatement();
		    //
	    	ResultSet r = stmt.executeQuery("SELECT * FROM " +table_name+ " WHERE ID = " +id+ " AND WORD LIKE '" +findwhat+ "';");;
	    	if (r.isBeforeFirst()) {
	    		SuperResultSet srs = new SuperResultSet();
	    		srs.rs = r;
	    		srs.foundAtColumn = "WORD";
	    		ll.add(srs);
	    	}
	    	return ll;
		    		
	    	
	    } catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
    	return ll;
	}
		
	public static LinkedList<SuperResultSet> findFest(String findwhat, String table_name, String columnList) {
		//TODO: methode to find a word from table_name in database.
	    LinkedList<SuperResultSet> ll = new LinkedList<SuperResultSet>();
	    try {
		    Statement stmt = c.createStatement();
		    //
			    	ResultSet r = stmt.executeQuery("SELECT * FROM " +table_name+ " WHERE " +columnList+ " LIKE '" +findwhat+ "';");;
			    	
			    		SuperResultSet srs = new SuperResultSet();
			    		srs.rs = r;
			    		srs.foundAtColumn = columnList;
			    		ll.add(srs);
		    		
	    	
	    } catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
    	return ll;
	}
	
	public static ResultSet find100Acc(SingleWord sw, String table_name) {
		ResultSet r = null;
		try {
			Statement stmt = c.createStatement();
			r = stmt.executeQuery("SELECT * FROM " +table_name+ " WHERE ID = " +sw.id+ " AND WORD LIKE '" +sw.word+ "';");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " +e.getMessage());
			System.exit(0);
		}
		return r;
	}
	
	public static void addToDatabase(SingleWord addWhat, String table_name) throws Exception {
	    Statement stmt = c.createStatement();
		stmt.executeUpdate("INSERT INTO " +table_name+ " (ID, WORD, TYPE, MEANING) VALUES (" + "'" +addWhat.id+"', " + "'" +addWhat.word+ "', " + "'" +addWhat.type+ "', " + "'" +addWhat.meaning+ "');");	
	}

	public static void removeFromDatabase(int id, String removeWhat, String table_name) throws Exception {
		Statement stmt = c.createStatement();
		stmt.executeUpdate("DELETE from " +table_name+ " WHERE ID = " +id+ " AND WORD LIKE '" +removeWhat+ "';");
	}
	
	public static void updateWord(SingleWord sW, SingleWord sw_old, String table_name) throws Exception {
		Statement stmt = c.createStatement();
		stmt.executeUpdate("UPDATE " +table_name+ " SET ID = " +sW.id+ ", WORD = " +sW.word+ ", TYPE = " +sW.type+ ", MEANING = " +sW.meaning+ " WHERE ID = " +sw_old.id+ " AND WORD LIKE '" +sw_old.word+ "';");
	}
	
	public static SingleWord resultSet_toSingleWord(ResultSet rs_input) throws Exception {
		//TODO: get SingleWord from a ResultSet, but only the first row.
		SingleWord sw_output = new SingleWord();
		sw_output.id = rs_input.getInt("ID");
		sw_output.word = rs_input.getString("WORD");
		sw_output.type = rs_input.getString("TYPE");
		sw_output.meaning = rs_input.getString("MEANING");
		return sw_output;
	}
}
