package XMLSwing;

import java.util.Stack;
import javax.swing.JFrame;

import Debug.Debug;
import XMLReader.XMLCursor;
import XMLReader.XMLTag;

public class XMLRenderer {
	XMLCursor cursor;
	
	public XMLRenderer(XMLCursor cursor){
		this.cursor = cursor;
	}
	
	public JFrame render(JFrame parentFrame){
		Stack<XMLTag> tags = this.cursor.getTags();
		while(!tags.isEmpty()){
			Debug.print(tags.pop().getName());
		}		
		return null;
	}
}
