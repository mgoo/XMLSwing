package XMLSwing;

import java.awt.Component;
import java.awt.LayoutManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	public static Component renderAttribute(Component obj, XMLAttribute attr){
		try{
			XMLAttributeRenderer.autoRenderAttribute(obj, attr); //Try this first
			Debug.print("Auto rendered "+attr.getName());
		} catch (NoSuchMethodException e){		
			try {
				switch (attr.getName()){ //if it doesnt work try this
				case "width":
					obj = XMLAttributeRenderer.width(obj, attr);
					break;
				case "height":
					obj = XMLAttributeRenderer.height(obj, attr);
					break;
				case "layout":
					obj = XMLAttributeRenderer.layout(obj, attr);
					break;
				default:
						Debug.print(attr.getName() + ": This attribute was not found");
				}
				Debug.print("Manually rendered " + attr.getName());
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	private static Component autoRenderAttribute(Component obj, XMLAttribute attr)throws SecurityException, NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		Class<?> type = obj.getClass();
		Method attMethod;
		try {
			attMethod = type.getMethod(attr.getName());
			attMethod.invoke(obj);
		} catch (NoSuchMethodException e){
			try {
				attMethod = type.getMethod(attr.getName(), Integer.TYPE);
				attMethod.invoke(obj, Integer.parseInt(attr.getValue()));
			} catch (NoSuchMethodException e1) {			
				attMethod = type.getMethod(attr.getName(), String.class);
				attMethod.invoke(obj, attr.getValue());					
			}
		}		
		return obj;
	}

	private static Component width(Component obj, XMLAttribute attr){
		int newWidth = Integer.parseInt(attr.getValue());
		int height = obj.getHeight();
		obj.setSize(newWidth, height);
		return obj;
	}

	private static Component height(Component obj, XMLAttribute attr){
		int newHeight = Integer.parseInt(attr.getValue());
		int width = obj.getWidth();
		obj.setSize(width, newHeight);
		return obj;
	}
	
	private static Component layout(Component obj, XMLAttribute attr) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class<?> layoutClass = Class.forName("java.awt." + attr.getValue());
		((JPanel)obj).setLayout((LayoutManager) layoutClass.newInstance());
		return obj;
	}
}
