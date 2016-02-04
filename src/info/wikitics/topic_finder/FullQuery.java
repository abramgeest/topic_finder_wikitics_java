package info.wikitics.topic_finder;

public class FullQuery {
	
	int numres;
	String source;
	String section;
	int hours;
	Article[] allarticles=null;
	
	public FullQuery(String source,String section, int hours) {
		//NewsWireQuery news = new NewsWireQuery(
		//		"nyt",         //news source
		//		"u.s.",        //section, ie. u.s., business,technology
		//		24,            //time to look back to get articles
		//		20,            //max number of articles to get
		//		0              //how much to shift back into the returned articles
		//		);

		this.source = source;
		this.section = section;
		this.hours=hours;
		int limit=20;
		int offset=0;
		
		//Finding the number of articles to be received (making the call just to
		//  get the num articles)
		NewsWireQuery numres_q = new NewsWireQuery(source,section,hours,
				limit,offset);
		JsonGetter jg_numres = new JsonGetter(numres_q.getQuery());
		numres=jg_numres.numres;
		
		allarticles = new Article[numres];
		
		//The New York Times caps the number of articles returned for each call at 20
		//	So this while loop calls the API for each group of 20 
		while(offset<numres){
			//if the remaining articles are less than 20  just get the remaining
			if(numres-offset<limit)  
				limit=numres-offset;  
			
			//Call the API and read the resulting JSON, then record the info from
			//  each article
			NewsWireQuery news = new NewsWireQuery(source,section,hours,
				limit,offset);
			JsonGetter jg = new JsonGetter(news.getQuery());
			Article[] resultsOneQuery = jg.get_results(limit);
			numres=jg.numres; // incase an article is published during the calls
			for(int i=0;i<limit;i++){
				allarticles[i+offset]=resultsOneQuery[i];
				//System.out.println(i+offset);
				//System.out.println(allarticles[i+offset].title);
			}
			offset+=20;
		}
		//System.out.println("numres="+numres);
	}

}
