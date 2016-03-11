package esp.socket;

import javax.servlet.ServletContext;
import java.util.*;

public class GeneralValue {

	public ServletContext servletContext;

	public GeneralValue(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public void addValue(String str,Object obj)
	{
		((HashMap)servletContext.getAttribute("socketMap")).put(str, obj);
	}
	public Object getValueFromMap(String str)
	{
		return ((HashMap)servletContext.getAttribute("socketMap")).get(str);
	}
}
