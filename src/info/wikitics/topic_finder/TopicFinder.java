package info.wikitics.topic_finder;
import java.util.HashSet;

public class TopicFinder {

	public static void main(String[] args) {
		//Default parameters for NYT article search
		String source = "nyt";
		String section = "u.s.";
		int hours=1;
		
		//Crate a query for the NYT API and then submit and process the
		//  query (including putting the resulting articles through the 
		//  AlchemyAPI) resulting in a list of keywords for the last "hours" hours.
		KeywordsGetter keywords= new KeywordsGetter(new FullQuery(source,section,hours));
		HashSet <String> daystopics = keywords.topics;
		
		//Query the MySQL database on the server for a list of all the
		//permanent Topics already considered in wikitics.
		PermanentTopics predeftopics = new PermanentTopics(); 
		HashSet <String> permtopics = predeftopics.permtopics;
		
		//I then compare the two sets of topics to make sure there are no 
		//  duplicates and submit the new (or daily) topics to a "test_topics"
		//  table in the MySQL database
		CompareSubmitToSQL.ToSQL(daystopics,permtopics);
		}


}
