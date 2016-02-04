package info.wikitics.topic_finder;

public class Nyt_Query {
	String query;
	String key;
	
	//So There are multiple types of NYT API calls (NewsWire, Most popular,etc.) 
	//  this is a generic class so that in the future I can expand the code for
	//  more than just NewsWire (and so I could play with inheritance)
	public Nyt_Query(){
		//The query will be added to in the various children
		this.query = "http://api.nytimes.com/svc/";
		//Each API Call has its own api key so each child will get it's own key
		this.key= null;
	}

	public String getQuery() {
		//All queries end with the api-key, so I just add that when the Query 
		//  is used so that I can still add flags ass needed
		if (key!=null)
			return query+"&api-key="+key;
		else {
			return null;
		}
	}
	
}

