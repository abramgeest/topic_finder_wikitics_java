package info.wikitics.topic_finder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PassKeys {
	String key=null;
	
	//I found I had to refer to files with passwords/api keys multiple times
	//  so this takes a filename and returns the one string (password or apikey)
	//  in the file
	public PassKeys(String file){
		//Opens and reads file, while habndling exceptions
		File fin = new File(file);
		try{
			this.key=readFile(fin);
		}catch(FileNotFoundException fnfe){
			System.out.println("Password/Key File Not found");
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	//Reads line from file "fin"
	private static String readFile(File fin) throws IOException,FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader(fin));
		
		String line = null;
		line = br.readLine();
		br.close();
		return line;
	}
}
