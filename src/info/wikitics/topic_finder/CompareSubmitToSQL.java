package info.wikitics.topic_finder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class CompareSubmitToSQL {
	public static void ToSQL(HashSet <String> daytopics ,HashSet <String> permtopics){
		//To use these topics later in wikitics I need to know the date these topics
		//  were generated on.  This defines those variables
		Date now = new Date();
		String day=new SimpleDateFormat("dd").format(now);
		String month=new SimpleDateFormat("MM").format(now);
		String year=new SimpleDateFormat("yyyy").format(now);
		
		//defines password and db name variables for connection to mySQL
		PassKeys sqlpass = new PassKeys("/Users/abramvandergeest/mysql_insightwiki_auth.txt");
		String db="wikidata";
		
		
		//Loops over each topic found in the NYT articles
		Connection conn = null;
		for (String daytopic : daytopics) {
        	int in=0;
        	//Compares today's topic with the permanent topics and sets "in" to 1 
        	//  if topic already exists -  this just does a direct comparison
        	//  i.e. "Bernie Sanders" would be a duplicate but "Senetor Bernie Sanders"
        	//  would not be.  Leaving until I can test how much this effects the
        	//  outlier detection.
        	for (String topic : permtopics){
        		if(topic.equals(daytopic.toLowerCase()) )
        			in=1;
        	}
        	//if topic does not previously exist submit to SQL server in the "test_topics"
        	//  table
        	if(in==0){
        		System.out.println(daytopic.toLowerCase());
        		try {
                	conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/"+db+"?" +
                		                                   "user=abram.ghost&password="+sqlpass.key);
                	String query="INSERT INTO test_topics (topic_label,topic_string,day,month,year) "
                	   +"VALUES (?,?,?,?,?)";
                	
                	//Puts variables into Query string in a way as to avoid SQL injection
                	PreparedStatement preparedStatement = conn.prepareStatement(query);
                	preparedStatement.setString(1, daytopic);
                	preparedStatement.setString(2, daytopic.toLowerCase());
                	preparedStatement.setString(3, day);
                	preparedStatement.setString(4, month);
                	preparedStatement.setString(5, year);
                	//Submits query
                	preparedStatement.executeUpdate(); 
                	
                	//prints Query for debugging purposes
                	System.out.println(query+" "+ daytopic+" "+daytopic.toLowerCase()+" "+day+" "+month+" "+year);
                } catch (SQLException ex){
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }

        	}
		}
	}
}
