package info.wikitics.topic_finder;

import java.io.FileNotFoundException;
import java.io.IOException;
//import java.io.StringWriter;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;

public class KeywordsGetter {
	//the set of topics determined from the Alchemy and NYT APIs
	HashSet <String> topics = null;
	
	//The input is the Object containing all the articles received from 
	//  the NYT API
	public KeywordsGetter(FullQuery daysresult) {
		//defining the key location for the AlchemyAPI
		String filename = "/Users/abramvandergeest/Dropbox/"+
		     "insight_work/topics_finder_wikitics/api_key.txt";
		
		//actually allocating the memory and creating the set
		topics = new HashSet <String>();
		
		//for each article from the New York Times submit both title and the 
		//  abstract to the AlchemyAPI Entity search - adding the keywords from
		//  both to the set "topics"
		for(int i=1;i<daysresult.numres;i++){
			try {
				////Just checking the inputs submitted to Alchemy
				//System.out.println(i+" "+daysresult.allarticles[i].title);
				//System.out.println(i+" "+daysresult.allarticles[i].abs);

				//Getting the set of keywords from the abstract and title or an article
				topics.addAll(alchemyAPICall(filename, daysresult.allarticles[i].abs));
				topics.addAll(alchemyAPICall(filename, daysresult.allarticles[i].title));
				
			} catch (IOException e) {
				System.out.println("AlchemyAPI can't find the file with the key:"+e);
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		////Comments to check output while debugging
		//for (String topic : topics) {
		//	System.out.println(topic);
		//}
		//System.out.println(topics.size());
		
	}

	public HashSet <String> alchemyAPICall(String filename,String text) throws FileNotFoundException, IOException, XPathExpressionException, SAXException, ParserConfigurationException{
		HashSet <String> topics = new HashSet <String>();
		
		//This definition includes getting the API Key from file
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile(filename);
		
		//Calls the API and returns an XML document with the results
		Document doc = alchemyObj.TextGetRankedNamedEntities(text);
		
		//loops over the xml to extract the entities/keywords and adds them to
		//  the "topics" set
		NodeList nList = doc.getElementsByTagName("entity");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String topic = eElement.getElementsByTagName("text").item(0).getTextContent();
				//.out.println("Text : " +topic);
				topics.add(topic);
			}
		}
		
		//Returns the "topics" set for this article
		return topics;
	}
	
}
