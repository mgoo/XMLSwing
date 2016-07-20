package XMLSwing;

import java.util.Stack;
import javax.swing.JFrame;

import Debug.Debug;
import XMLReader.XMLCursor;
import XMLReader.XMLTag;

public class XMLRenderer {
	XMLCursor cursor;

	JFrame mainFrame;

	public XMLRenderer(XMLCursor cursor){
		this.cursor = cursor;
	}

	/**
	 * Will handle the nesting of the swing objects
	 * and will call the render functions rather than them being called individually
	 * @param parentFrame
	 * @return
	 */
	public JFrame render(JFrame parentFrame){
		/*Stack<XMLTag> tags = this.cursor.getTags();
		while(!tags.isEmpty()){
			Debug.print(tags.pop().getName());
		}*/
		return null;
	}
}
