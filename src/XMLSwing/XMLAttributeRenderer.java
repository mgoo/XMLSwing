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
		try {
			switch (attr.getName()){
			case "width":
				obj = XMLAttributeRenderer.width(obj, attr);
				break;
			case "height":
				obj = XMLAttributeRenderer.height(obj, attr);
				break;
			case "layout":
				obj = XMLAttributeRenderer.layout(obj, attr);
				break;
			case "setLayoutRows":
			case "setLayoutColumns":
				obj = XMLAttributeRenderer.setLayoutAttribute(obj, attr);
				break;
			default: //if there is no special render function try to render it automattically
				Debug.print(attr.getName() + ": This attribute was not found");
				XMLAttributeRenderer.autoRenderAttribute(obj, attr); //Try this first
				Debug.print("Auto rendered "+attr.getName());
			}
			Debug.print("Manually rendered " + attr.getName());
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
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

	private static Component setLayoutAttribute(Component obj, XMLAttribute attr) throws SecurityException, NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		if (obj.getClass() == JPanel.class){ //sets the rows for a grid layout
			LayoutManager layout = ((JPanel)obj).getLayout();
			Debug.print(layout.toString());
			Method method;
			try {
				method = layout.getClass().getMethod(attr.getName(), Integer.TYPE);
				method.invoke(layout, Integer.parseInt(attr.getValue()));
			} catch (NoSuchMethodException e) {
				try {
					method = layout.getClass().getMethod(attr.getName(), String.class);
					method.invoke(layout, attr.getValue());
				} catch (NoSuchMethodException e1) {
					method = layout.getClass().getMethod(attr.getName());
					method.invoke(layout);
				}
			}

		}
		return obj;
	}
}
