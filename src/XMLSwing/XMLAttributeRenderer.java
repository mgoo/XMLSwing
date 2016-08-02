package XMLSwing;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.*;

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
				case "setWidth":
				case "width":
					obj = XMLAttributeRenderer.width(obj, attr);
					break;
				case "setHeight":
				case "height":
					obj = XMLAttributeRenderer.height(obj, attr);
					break;
				case "setMaximumWidth":
					obj = XMLAttributeRenderer.maxWidth(obj, attr);
					break;
				case "setMaximumHeight":
					obj = XMLAttributeRenderer.maxHeight(obj, attr);
					break;
				case "layout":
					obj = XMLAttributeRenderer.layout(obj, attr);
					break;
				case "tabToolTip":
				case "tabName":
					break; //dont render attribute
				default: //if there is no special render function try to render it automattically
					if (attr.getName().matches("layout_([A-Za-z]{0,})")){
						obj = XMLAttributeRenderer.setLayoutAttribute(obj, attr);
						break;
					}
					Debug.print(attr.getName() + ": This attribute was not found");
					XMLAttributeRenderer.autoRenderAttribute(obj, attr); //Try this first
					Debug.print("Auto rendered "+attr.getName());
			}
			//Debug.print("Manually rendered " + attr.getName());
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
		XMLAttributeRenderer.invokeMethod(type, obj, attr.getName(), attr.getValue());
		return obj;
	}

	private static Component maxWidth(Component obj, XMLAttribute attr){
		int newWidth = Integer.parseInt(attr.getValue());
		int height = (int)(obj.getMaximumSize().getHeight());
		obj.setMaximumSize(new Dimension(newWidth, height));
		return obj;
	}

	private static Component maxHeight(Component obj, XMLAttribute attr){
		int newHeight = Integer.parseInt(attr.getValue());
		int width = (int)(obj.getMaximumSize().getWidth());
		obj.setMaximumSize(new Dimension(width, newHeight));
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

	private static Component layout(Component obj, XMLAttribute attr) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> layoutClass = null;
		try {
			layoutClass = Class.forName("java.awt." + attr.getValue());
		} catch (ClassNotFoundException e) {
			try {
				layoutClass = Class.forName("javax.swing." + attr.getValue());
			} catch (ClassNotFoundException e1) {
				layoutClass = Class.forName(attr.getValue());
			}
		}
		if(layoutClass.equals(BoxLayout.class)){
			try {
				//Does the special things to render the BoxLayout
				Constructor con = layoutClass.getDeclaredConstructor(Container.class, Integer.TYPE);
				((JPanel)obj).setLayout((LayoutManager) con.newInstance(obj, BoxLayout.Y_AXIS));
			} catch (NoSuchMethodException e) {e.printStackTrace();} catch (InvocationTargetException e) {e.printStackTrace();}
		} else {
			((JPanel)obj).setLayout((LayoutManager) layoutClass.newInstance());
		}
		return obj;
	}

	private static Component setLayoutAttribute(Component obj, XMLAttribute attr) throws SecurityException, NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{

		if (obj.getClass() == JPanel.class){ //sets the rows for a grid layout
			LayoutManager layout = ((JPanel)obj).getLayout();
			Debug.print(layout.toString());
			XMLAttributeRenderer.invokeMethod(layout.getClass(), layout, attr.getName().split("_")[1], attr.getValue());			
		}
		return obj;
	}
	
	private static boolean invokeMethod(Class<?> classObj, Object obj, String name, String param) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Method method;
		try {
			method = classObj.getMethod(name, Integer.TYPE);
			method.invoke(obj, Integer.parseInt(param));
		} catch (NoSuchMethodException e) {
			try {
				method = classObj.getMethod(name, String.class);
				method.invoke(obj, param);
			} catch (NoSuchMethodException e1) {
				try{
					method = classObj.getMethod(name);
					method.invoke(obj);
				} catch (NoSuchMethodException e2){
					try{
					method = classObj.getMethod(name, Color.class);
					method.invoke(obj, Color.decode(param));
					Debug.print("Rendered Color: " + param);
					} catch (NoSuchMethodException e3){						
						method = classObj.getMethod(name, Boolean.TYPE);
						method.invoke(obj, Boolean.parseBoolean(param));
					}
				}
			}
		}
		return true;
	}
}
