package XMLReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Debug.Debug;

public class XMLTag {
	
	private String name;
	private List<XMLAttribute> attributes = new ArrayList<XMLAttribute>();
	private XMLTag parent;
	private List<XMLTag> childeren = new ArrayList<XMLTag>();
	
	private boolean isOpen = true;
	public void close(){this.isOpen = false;}
	public boolean isOpen(){return this.isOpen;}
		
	public String getName(){return this.name;}
	public List<XMLAttribute> getAttributes(){return this.attributes;}
	public XMLAttribute getAttributeByName(String name){
		for (XMLAttribute attr : this.attributes){
			if (attr.getName().equals(name))return attr;
		}
		return null;
	}
	public XMLTag getParent(){return this.parent;}
	public List<XMLTag> getChilderen(){return this.childeren;}
	
	public void setName(String newName){this.name = newName;}
	public void addAttribute(XMLAttribute attr){this.attributes.add(attr);}
	public void setParent(XMLTag parent){this.parent = parent;/*parent.addChild(this);*/}
	public void addChild(XMLTag child){this.childeren.add(child);/*child.setParent(this);*/}
	public boolean hasChilderen(){return !this.childeren.isEmpty();}
	
	public XMLTag(String data){
		
	}

	protected XMLTag(){

	}
	
	public static XMLTag newXMLTag(String data,XMLTag parent){		
		XMLTag newTag = new XMLTag();
		data = data.replaceAll("[\\s\\n\\r]", " ");
		data = data.replaceAll("  ", " ");		
		String[] dataArr = data.split(" ");
		newTag.setName(dataArr[0]);

		Matcher m = Pattern.compile("([^\\s\"]{0,})=\"(.[^\"]{0,})\"").matcher(data);
		Debug.print(data);
		while (m.find()) {
			newTag.addAttribute(XMLAttribute.makeAttr(m.group()));
		}

		if (!newTag.getName().equals("laf") && parent != null){
			newTag.setParent(parent);
			parent.addChild(newTag);
		}


		return newTag;
	}
}
