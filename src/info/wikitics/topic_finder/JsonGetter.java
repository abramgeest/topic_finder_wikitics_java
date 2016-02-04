package info.wikitics.topic_finder;

//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils; //using from apache commons io at https://commons.apache.org/proper/commons-io/download_io.cgi
import org.json.JSONArray;
import org.json.JSONObject;  //using the mvn json library at http://mvnrepository.com/artifact/org.json/json/20151123  

public class JsonGetter {

	JSONObject json = null;
	JSONArray results = null;
	int numres = 0;
	private Article[] articles=null;
	
	public JsonGetter(String url) {
		try {
			InputStream instream = new URL(url).openStream();
		
			String Txt = IOUtils.toString(instream);
			json = new JSONObject(Txt);
			} 
			catch (MalformedURLException MUE) {
				System.out.println(MUE);
			} 
			catch (IOException IOE) {
				System.out.println(IOE);
			}
			this.numres = json.getInt("num_results");
	}

	public Article[] get_results(int limit){
		
		if (json !=null){
			this.results = json.getJSONArray("results");
			if (articles==null){
				articles = new Article[numres];
			}
			
			for(int i = 0; i<limit;i++){
				JSONObject r = (JSONObject) results.get(i);
				articles[i] = new Article(r.get("title").toString(),r.get("abstract").toString(),
						r.get("published_date").toString(),r.get("url").toString(),r.get("geo_facet").toString(),
						r.get("des_facet").toString(),r.get("per_facet").toString());
				//System.out.println(i);
				//System.out.println(articles[i].title);
				//System.out.println(articles[i].abs);
				//System.out.println(articles[i].pubdate);
				//System.out.println(articles[i].url);
				//System.out.println(articles[i].geo_facet);
				//System.out.println(articles[i].des_facet);
				//System.out.println(articles[i].per_facet);
			}
			
		}
		return articles;
	}
	
}
