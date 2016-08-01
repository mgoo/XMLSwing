package Test;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JTabbedPane;

import com.alee.laf.WebLookAndFeel;

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
			Debug.print("hi");
			WebLookAndFeel.install();
			XMLCursor c = reader.openXMLFile("assets/testproper.xml");
			c.loadXML();
			XMLRenderer newRenderer = new XMLRenderer(c);
			newRenderer.render();
			newRenderer.show();
		} catch (IOException e) {e.printStackTrace();}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainTest();
	}
}
