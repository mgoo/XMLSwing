package Test;

import java.awt.Color;
import java.io.IOException;

import javax.swing.*;

//import com.alee.laf.WebLookAndFeel;

import Debug.Debug;
import XMLReader.XMLCursor;
import XMLReader.XMLReader;
import XMLSwing.XMLRenderer;


public class MainTest {

	/**
	 * Just a class for testing the liburay while in development
	 */
	public MainTest(){
		XMLReader reader = new XMLReader();
		try {
			//WebLookAndFeel.install();
			XMLCursor c = reader.openXMLFile("assets/test.xml");
			c.loadXML();
			XMLRenderer newRenderer = new XMLRenderer(c);
			newRenderer.render(this.getClass());
			newRenderer.show(new String[]{XMLRenderer.FRAME_MAXIMISED, XMLRenderer.FRAME_CLOSE_EXIT});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MainTest();
	}
}
