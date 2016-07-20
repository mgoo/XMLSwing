package XMLSwing;

import java.awt.Container;
import javax.swing.JFrame;

import Debug.Debug;
import XMLReader.XMLCursor;
import XMLReader.XMLTag;

public class XMLRenderer {
	private XMLCursor cursor;
	private JFrame mainFrame;
	
	public void show(){this.mainFrame.setVisible(true);}
	public void hide(){this.mainFrame.setVisible(false);}

	public XMLRenderer(XMLCursor cursor){
		this.cursor = cursor;
	}

	/**
	 * Will handle the nesting of the swing objects
	 * and will call the render functions rather than them being called individually
	 * @param parentFrame
	 * @return
	 */
	public JFrame render(){
		XMLTag frame = cursor.getFrame();
		this.mainFrame = (JFrame) XMLTagRenderer.render(frame);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addChilderen(frame, this.mainFrame);
		return null;
	}
	
	private Container addChilderen(XMLTag parent, Container parentComponent){
		for(XMLTag child : parent.getChilderen()){
			Container childComponent = (Container) XMLTagRenderer.render(child);
			if (child.hasChilderen())childComponent = this.addChilderen(child, childComponent);
			parentComponent.add(childComponent);
			
		}
		return parentComponent;
	}
}
