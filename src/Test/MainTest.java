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
