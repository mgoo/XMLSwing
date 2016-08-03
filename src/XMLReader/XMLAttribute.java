package XMLReader;

import Debug.Debug;

public class XMLAttribute {
	private String name;
	private String value;
	
	public void setName(String name){this.name = name;}
	public void setValue(String value){this.value = value;}
	
	public String getName(){return this.name;}
	public String getValue(){return this.value;}

	protected XMLAttribute(){

	}

	public XMLAttribute(String data){

	}



	public static XMLAttribute makeAttr(String data){
		XMLAttribute attr = new XMLAttribute();
		String[] dataArr = data.split("=");
		attr.setName(dataArr[0]);
		dataArr[1] = dataArr[1].replaceAll("/\"/", "");
		dataArr[1] = dataArr[1].replaceAll("\"", "");
		attr.setValue(dataArr[1]);
		return attr;
	}
}
