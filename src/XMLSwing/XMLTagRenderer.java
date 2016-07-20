package XMLSwing;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Debug.Debug;
import XMLReader.XMLAttribute;
import XMLReader.XMLTag;

public class XMLTagRenderer {

	/**
	 * This is the method that all external programs should call
	 * combines the name of the swing tag and the corresponding class object
	 * @param tag
	 * @return
	 */
	public static Component render(XMLTag tag){
		try {
			Component obj = null;
			switch (tag.getName()){
			case "JLabel":
				obj = XMLTagRenderer.renderJComponet(tag, JLabel.class);
				break;
			case "JButton":
				obj = XMLTagRenderer.renderJComponet(tag, JButton.class);
				break;
			case "JColorChooser":
				obj = XMLTagRenderer.renderJComponet(tag, JColorChooser.class);
				break;
			case "JCheckBox":
				obj = XMLTagRenderer.renderJComponet(tag, JCheckBox.class);
				break;
			case "JRadioButton":
				obj = XMLTagRenderer.renderJComponet(tag, JRadioButton.class);
				break;
			case "JList":
				obj = XMLTagRenderer.renderJComponet(tag, JList.class);
				break;
			case "JComboBox":
				obj = XMLTagRenderer.renderJComponet(tag, JComboBox.class);
				break;
			case "JTextField":
				obj = XMLTagRenderer.renderJComponet(tag, JTextField.class);
				break;
			case "JPasswordField":
				obj = XMLTagRenderer.renderJComponet(tag, JPasswordField.class);
				break;
			case "JTextArea":
				obj = XMLTagRenderer.renderJComponet(tag, JTextArea.class);
				break;
			case "ImageIcon":
				//obj = XMLTagRenderer.renderJComponent(tag, ImageIcon.class);
				Debug.print("Image Icon is not yet Supported");
				break;
			case "JScrollbar":
				obj = XMLTagRenderer.renderJComponet(tag, JScrollBar.class);
				break;
			case "JOptionPane":
				obj = XMLTagRenderer.renderJComponet(tag, JOptionPane.class);
				break;
			case "JFileChooser":
				obj = XMLTagRenderer.renderJComponet(tag, JFileChooser.class);
				break;
			case "JProgressBar":
				obj = XMLTagRenderer.renderJComponet(tag, JProgressBar.class);
				break;
			case "JSlider":
				obj = XMLTagRenderer.renderJComponet(tag, JSlider.class);
				break;
			case "JSpinner":
				obj = XMLTagRenderer.renderJComponet(tag, JSpinner.class);
				break;
			case "JFrame":
				obj = XMLTagRenderer.renderContainer(tag, JFrame.class);
				break;
			default:
				Debug.print(tag.getName() + "Was not recognised");
			}
			return obj;
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

	/**
	 * creates a object that extends a JComponent with the class object you pass it
	 * @param tag
	 * @param type
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static JComponent renderJComponet(XMLTag tag, Class<? extends JComponent> type) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Constructor<? extends JComponent> con = type.getConstructor();
		JComponent obj = con.newInstance();
		if ((JComponent)obj == null)throw new InstantiationException();
		List<XMLAttribute> attributes = tag.getAttributes();
		for (XMLAttribute attr : attributes){
			obj = (JComponent) XMLAttributeRenderer.renderAttribute((JComponent) obj, attr);
		}
		return (JComponent)obj;
	}

	private static Container renderContainer(XMLTag tag, Class<? extends Container> type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Constructor<? extends Component> con = type.getConstructor();
		Container obj = (Container) con.newInstance();
		if ((Container)obj == null)throw new InstantiationException();
		List<XMLAttribute> attributes = tag.getAttributes();
		for (XMLAttribute attr : attributes){
			obj = (Container) XMLAttributeRenderer.renderAttribute((Container) obj, attr);
		}
		return (Container)obj;
	}

}
