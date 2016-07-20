package Test;

import java.io.IOException;
import java.util.List;

import javax.swing.*;

import Debug.Debug;
import XMLReader.XMLCursor;
import XMLReader.XMLReader;
import XMLReader.XMLTag;
import XMLSwing.XMLRenderer;
import XMLSwing.XMLTagRenderer;


public class MainTest {

	/**
	 * Just a class for testing the liburay while in development
	 */
	public MainTest(){


		XMLReader reader = new XMLReader();
		try {
			XMLCursor c = reader.openXMLFile("assets/testproper.xml");
			c.loadXML();
			/*XMLRenderer XMLR = new XMLRenderer(c);
			XMLR.render(c);*/
			JFrame frame = (JFrame) XMLTagRenderer.render(c.getFrame());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			List<XMLTag> tags = c.getTags();
			tags.remove(c.getFrame());
			for (XMLTag tag : tags){
				frame.getContentPane().add(XMLTagRenderer.render(tag));
				Debug.print(tag.getName());
			}
	        frame.setVisible(true);
		} catch (IOException e) {e.printStackTrace();}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainTest();
	}

}
