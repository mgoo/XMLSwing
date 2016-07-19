package XMLReader;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import Debug.Debug;

public class XMLCursor {
	private Scanner fileReader;
	private Stack<XMLTag> tags = new Stack<XMLTag>();
	public Stack<XMLTag> getTags(){return this.tags;}

	public XMLCursor(Scanner br){
		br.useDelimiter(">[.\\n\\r\\s]{0,}<");
		this.fileReader = br;
	}
	
	public void loadXML(){
		while(fileReader.hasNext()){	
			String line = fileReader.next();
			if (line.startsWith("<"))line = line.substring(1);
			if (line.endsWith(">"))line = line.substring(0, line.length() - 1);			
			if(tags.isEmpty()){
				tags.push(XMLTag.newXMLTag(line, null));
			} else if(line.matches("\\/[a-zA-Z]{1,}")){
				tags.peek().close();
			} else {
				int count;
				for (count = tags.size()-1; !tags.get(count).isOpen() && count >= 0; count--){}
				tags.push(XMLTag.newXMLTag(line, (tags.get(count).isOpen() ? tags.get(count) : null)));
			}			
			if (line.endsWith("/")){
				tags.peek().close();
			}
		}
	}

	public XMLTag nextTag() throws IOException{			
		return tags.get(0);
	}

	public String nextAttribute(){
		return "";
	}


}
