package XMLSwing;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import Debug.Debug;
import XMLReader.XMLCursor;
import XMLReader.XMLTag;

public class XMLRenderer {
	private XMLCursor cursor;
	private Container root;
	private Map<String, Container> elements = new HashMap<String, Container>();

	public void addElement(Container element){this.elements.put(element.getName(), element);}
	public Container findElementByName(String name){return this.elements.get(name);}
	public void show(){this.root.setVisible(true);}
	public void hide(){this.root.setVisible(false);}

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
		XMLTag rootTag = cursor.getRoot();
		this.root = (Container) XMLTagRenderer.render(rootTag, elements);
		if (rootTag.getName().equals("JFrame")){
			((JFrame)this.root).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		this.addChilderen(rootTag, this.root);
		return null;
	}

	private Container addChilderen(XMLTag parent, Container parentComponent){
		for(XMLTag child : parent.getChilderen()){
			Container childComponent = (Container) XMLTagRenderer.render(child, elements);
			if (child.hasChilderen())childComponent = this.addChilderen(child, childComponent);
			if (parentComponent.getClass().equals(JTabbedPane.class)){ //if statments for special adds
				((JTabbedPane)parentComponent).addTab(childComponent.getName(), childComponent);
				Debug.print("added tab");
			} else {
				parentComponent.add(childComponent);
			}

		}
		return parentComponent;
	}
}
