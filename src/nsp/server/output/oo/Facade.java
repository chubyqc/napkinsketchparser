package nsp.server.output.oo;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.text.XText;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

public class Facade {
	private static Facade _instance = new Facade();
	public static Facade get() {
		return _instance;
	}
	
	private static final String PATH_PRE = "file:%s";
	
	private XComponentLoader _loader;
	
	private Facade() {
		try {
			XComponentContext xContext = Bootstrap.bootstrap();
			XMultiComponentFactory xMCF = xContext.getServiceManager();
			Object obj = xMCF.createInstanceWithContext(
					"com.sun.star.frame.Desktop", xContext);
			_loader = (XComponentLoader)get(XComponentLoader.class, obj);
		} catch (Exception t) {
			t.printStackTrace();
		}
	}
	
	public void createTextDocument(String text, String path) throws IOException, IllegalArgumentException {
		XTextDocument document = createTextDocument();
		XText xText = document.getText();
		xText.insertString(xText.getEnd(), text, false);
		XStorable xStorable = (XStorable)UnoRuntime.queryInterface(XStorable.class, document);
		xStorable.storeAsURL(String.format(PATH_PRE, path), null);
	}
	
	private XTextDocument createTextDocument() throws IOException, IllegalArgumentException {
		PropertyValue[] loadProps = new PropertyValue[1];
		loadProps[0] = new PropertyValue();
		loadProps[0].Name = "Hidden";
		loadProps[0].Value = new Boolean(true);
		return (XTextDocument)get(XTextDocument.class, 
				_loader.loadComponentFromURL(
						"private:factory/swriter", "_blank", 0, loadProps));
	}
	
	private <T> Object get(Class<T> type, Object obj) {
		return UnoRuntime.queryInterface(type, obj);
	}
	
	public static void main(String[] args) throws IOException, IllegalArgumentException {
		get().createTextDocument("another text", "/home/chubyqc/test.xml");
		System.exit(0);
	}
}
