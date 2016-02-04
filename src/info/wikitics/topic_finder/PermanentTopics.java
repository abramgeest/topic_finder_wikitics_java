package info.wikitics.topic_finder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class PermanentTopics {
	
	//defining variable to hold list of permanent topics
	HashSet <String> permtopics = null;
	
	//This constructor populates the "permtopics" hashset with all the topics that
	//  were chosen by hand as permanent topics in wikitics so that later I can
	//  make sure I don't duplicate them
	public PermanentTopics(){
		permtopics=new HashSet <String>();
		
		//defines password and db name variables for connection to mySQL
		PassKeys sqlpass = new PassKeys("/Users/abramvandergeest/mysql_insightwiki_auth.txt");
		String db="wikidata";
	
		//Input and Output Variables needed to connect to MySQL database
		Statement stmt = null;
		ResultSet rs = null;
		
		//Establishes connection to SQL then queries for all the topics and puts into
		//  hashset as lower case.  Finally then cleans up in/out variables  
		Connection conn = null;
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/"+db+"?" +
    		                                   "user=abram.ghost&password="+sqlpass.key);
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery("SELECT * FROM topics");
    	
    		while(rs.next()){
    			//System.out.println(rs.getString("topic_string"));
    			permtopics.add(rs.getString("topic_string").toLowerCase());
    		}
    	
    	} catch (SQLException ex){ //Exception ex) {
        	System.out.println("SQLException: " + ex.getMessage());
        	System.out.println("SQLState: " + ex.getSQLState());
        	System.out.println("VendorError: " + ex.getErrorCode());
    	}
    	finally {
    		if (rs != null) {
            	try {
                	rs.close();
            	} catch (SQLException sqlEx) { } // ignore

            	rs = null;
        	}

        	if (stmt != null) {
            	try {
                	stmt.close();
            	} catch (SQLException sqlEx) { } // ignore

            	stmt = null;
        	}
    	}
	}
}
