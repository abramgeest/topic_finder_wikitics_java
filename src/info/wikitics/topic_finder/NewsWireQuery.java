package info.wikitics.topic_finder;

public class NewsWireQuery extends Nyt_Query {

	//So Of all the API CALLS for the NYT I use NewsWire because it gives all 
	//  the articles/blogs/etc. published in the last week
	public NewsWireQuery(String newssource, String section, int time,int limit, int offset) {
		super();
		
		//get NewsWire api key
		String file ="/Users/abramvandergeest/Dropbox/insight_work/topics_finder_wikitics/nyt_newswire_key.txt";
		PassKeys key = new PassKeys(file);
		this.key=key.key;
		
		//Include NewsWire Specific part of API CAll
		query=query+"news/v3/content/"+newssource+"/"+section+"/"+time
				+".json?limit="+limit+"&offset="+offset;
	}
	
}
