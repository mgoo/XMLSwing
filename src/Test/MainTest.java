package Test;

import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JLabel;

import Debug.Debug;
import XMLReader.XMLCursor;
import XMLReader.XMLReader;
import XMLSwing.XMLRenderer;
import XMLSwing.XMLTagRenderer;

public class MainTest {

	public MainTest(){
		XMLReader reader = new XMLReader();
		try {
			XMLCursor c = reader.openXMLFile("assets/testproper.xml");
			c.loadXML();
			/*XMLRenderer XMLR = new XMLRenderer(c);
			XMLR.render(c);*/
			JComponent comp = XMLTagRenderer.render(c.nextTag());
			Debug.print(((JLabel)comp).getText());
		} catch (IOException e) {e.printStackTrace();}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainTest();
	}

}
