package XMLSwing;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

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
	public static Component render(XMLTag tag, Map<String, Container> elements){
		try {
			Component obj = null;
			Class<? extends Component> type;
			try {
				type = (Class<? extends Component>) Class.forName("javax.swing." + tag.getName());
				obj = XMLTagRenderer.renderComponet(tag, type);
			} catch (ClassNotFoundException e) {
				Debug.print("the class " + tag.getName() + " was not found");
				e.printStackTrace();
			}
			elements.put(obj.getName(), (Container) obj);
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
	 * creates a object that extends a Component which all non layouts do with the class object you pass it
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
	private static Component renderComponet(XMLTag tag, Class<? extends Component> type) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Constructor<? extends Component> con = type.getConstructor();
		Component obj = con.newInstance();
		if ((Component)obj == null)throw new InstantiationException();
		List<XMLAttribute> attributes = tag.getAttributes();
		for (XMLAttribute attr : attributes){
			obj = (Component) XMLAttributeRenderer.renderAttribute((Component) obj, attr);
		}
		return (Component)obj;
	}

}
