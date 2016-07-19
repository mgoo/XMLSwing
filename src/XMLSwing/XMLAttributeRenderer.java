package XMLSwing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import Debug.Debug;
import XMLReader.XMLAttribute;

public class XMLAttributeRenderer {
	
	/**
	 * This is the only method that should be called from outside
	 * combines the swing attribute to the name of the attribute in XML
	 * @param obj
	 * @param attr
	 * @return
	 */
	public static JComponent renderAttribute(JComponent obj, XMLAttribute attr){
		try {
			switch (attr.getName()){
			case "name":
				obj = XMLAttributeRenderer.name(obj, attr);
				break;
			case "width":
				obj = XMLAttributeRenderer.width(obj, attr);
				break;
			case "height":
				obj = XMLAttributeRenderer.height(obj, attr);
				break;
			case "text":
				obj = XMLAttributeRenderer.text(obj, attr);
				break;
			default:
					Debug.print(attr.getName() + "This attribute was not found");
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	private static JComponent name(JComponent obj, XMLAttribute attr){
		obj.setName(attr.getValue());
		return obj;
	}
	
	private static JComponent width(JComponent obj, XMLAttribute attr){
		int newWidth = Integer.parseInt(attr.getValue());
		int height = obj.getHeight();
		obj.setSize(newWidth, height);
		return obj;
	}
	
	private static JComponent height(JComponent obj, XMLAttribute attr){
		int newHeight = Integer.parseInt(attr.getValue());
		int width = obj.getWidth();
		obj.setSize(width, newHeight);
		return obj;
	}
	
	private static JComponent text(JComponent obj, XMLAttribute attr) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> type = obj.getClass();		
		Method textSetter = type.getMethod("setText", String.class);
		textSetter.invoke(obj, attr.getValue());		
		return obj;
	}

}
