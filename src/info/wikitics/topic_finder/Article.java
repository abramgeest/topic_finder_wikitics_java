package info.wikitics.topic_finder;

//This objects just groups all the useful information from a specific news article
public class Article {
	String title;       //Title
	String abs;         //abstract
	String pubdate;     //date published
	String url;         //link to article
	//These are tags (geo/location, description, and people) added to the articles by
	//  the NYTs
	String geo_facet;
	String des_facet;
	String per_facet;
	
	public Article(String title, String abs, String pubdate,String url,	String geo_facet, String des_facet, String per_facet){
		this.title = title;
		this.abs = abs;
		this.pubdate = pubdate;
		this.url=url;
		this.geo_facet=geo_facet;
		this.des_facet=des_facet;
		this.per_facet=per_facet;
		
	}
}
