package XMLSwing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import Debug.Debug;
import XMLReader.XMLAttribute;
import XMLReader.XMLTag;

public class XMLTagRenderer {
	
	public static JComponent render(XMLTag tag){
		JComponent obj = null;
		switch (tag.getName()){
		case "JLabel":
			obj = XMLTagRenderer.renderJComponent(tag, JLabel.class);
			break;
		case "JButton":
			obj = XMLTagRenderer.renderJComponent(tag, JButton.class);
			break;
		case "JColorChooser":
			obj = XMLTagRenderer.renderJComponent(tag, JColorChooser.class);
			break;
		case "JCheckBox":
			obj = XMLTagRenderer.renderJComponent(tag, JCheckBox.class);
			break;
		case "JRadioButton":
			obj = XMLTagRenderer.renderJComponent(tag, JRadioButton.class);
			break;
		default:
			Debug.print(tag.getName() + "Was not recognised");
		}
		return obj;
	}
	
	private static JComponent renderJComponent(XMLTag tag, Class<? extends JComponent> type){
		try {
			Constructor<? extends JComponent> con = type.getConstructor();
			JComponent obj = con.newInstance();
			if ((JComponent)obj == null)throw new InstantiationException();
			List<XMLAttribute> attributes = tag.getAttributes();
			for (XMLAttribute attr : attributes){
				obj = XMLAttributeRenderer.renderAttribute((JComponent) obj, attr);
			}
			return (JComponent)obj;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}	
}
