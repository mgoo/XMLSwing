package XMLReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFrame;

import Debug.Debug;

public class XMLCursor {
	private Scanner fileReader;
	private List<XMLTag> tags = new ArrayList<XMLTag>();
	private XMLTag root;
	private List<XMLTag> laf = new ArrayList<>();
	public List<XMLTag> getLaf(){return this.laf;}
		
	public List<XMLTag> getTags(){return this.tags;}
	public XMLTag getRoot(){return this.root;}

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
				tags.add(XMLTag.newXMLTag(line, null));
			} else if(line.matches("\\/[a-zA-Z]{1,}") || line.startsWith("/")){
				int count;
				for (count = tags.size()-1; count >= 0 && !tags.get(count).isOpen() ; count--){}
				try{
					tags.get(count).close();
				} catch (ArrayIndexOutOfBoundsException e){Debug.print("failed to close: " + line);}
			} else {
				int count = -1;
				try {
					for (count = tags.size()-1; !tags.get(count).isOpen() && count >= 0; count--){}
				} catch (ArrayIndexOutOfBoundsException e){}
				XMLTag newTag = XMLTag.newXMLTag(line, ((count != -1 && tags.get(count).isOpen()) ? tags.get(count) : null));
				switch (newTag.getName()){
					case "laf":
						laf.add(newTag);
						break;
					default:
						tags.add(newTag);
				}
			}
			if (line.endsWith("/")){
				tags.get(tags.size()-1).close();
			}
		}
		this.root = this.findRoot();
		this.removeLaf();
		this.tags.remove(this.root);
		for (XMLTag tag : tags){
			if (tag.isOpen()){
				Debug.print("*** ERROR in closing " + tag.getName() + " ***");
			}
		}

	}

	public XMLTag nextTag() throws IOException{
		return tags.get(1);
	}

	private void removeLaf(){
		for(XMLTag tag : tags){
			if (tag.getName().equals("laf")){
				this.laf.add(tag);
			}
		}
		tags.removeAll(this.laf);
	}

	public XMLTag findRoot(){
		for (XMLTag tag : tags){
			if (!tag.getName().equals("laf")){
				return tag;
			}
		}
		/*for (XMLTag tag : tags){
			if (tag.getName().equals("JFrame")){
				return tag;
			}
		}*/
		return null;
	}
}
