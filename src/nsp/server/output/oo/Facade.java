package nsp.server.output.oo;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.PropertyVetoException;
import com.sun.star.beans.UnknownPropertyException;
import com.sun.star.beans.XPropertySet;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.IllegalArgumentException;
import com.sun.star.lang.WrappedTargetException;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.text.HoriOrientation;
import com.sun.star.text.SizeType;
import com.sun.star.text.VertOrientation;
import com.sun.star.text.XText;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextFrame;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;

public class Facade {

	private static final String PATH_PRE = "file:%s";
	private static final String BLANK = "_blank";
	private static final String OUT_URL = "private:factory/swriter";

	private static final String X_DESKTOP = "com.sun.star.frame.Desktop";
	private static final String X_TEXTFRAME = "com.sun.star.text.TextFrame";

	private static final String PROP_HIDDEN = "Hidden";
	private static final Boolean IS_HIDDEN = new Boolean(true);
	
	private static final String PROP_SIZETYPE = "SizeType";
	private static final String PROP_WIDTH = "FrameWidthAbsolute";
	private static final String PROP_HEIGHT = "FrameHeightAbsolute";
	private static final String PROP_HORIORIENT = "HoriOrient";
	private static final String PROP_HORIPOSITION = "HoriOrientPosition";
	private static final String PROP_VERTORIENT = "VertOrient";
	private static final String PROP_VERTPOSITION = "VertOrientPosition";
	
	private static final double CANVAS_PIXEL_SIDE = 1000;
	private static final double PIXEL_RATIO = 8.5 * 25 * 100 / CANVAS_PIXEL_SIDE;

	private XComponentContext _xContext;
	private XMultiComponentFactory _xMCF;
	private XComponentLoader _loader;

	public Facade() {
		try {
			XComponentContext xContext = Bootstrap.bootstrap();
			_xMCF = xContext.getServiceManager();
			_loader = query(XComponentLoader.class, _xMCF.createInstanceWithContext(X_DESKTOP, _xContext));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(Object documentObj, String path) throws IOException {
		XStorable xStorable = (XStorable) UnoRuntime.queryInterface(
				XStorable.class, documentObj);
		xStorable.storeAsURL(String.format(PATH_PRE, path), null);
	}

	public void appendText(Object documentObj, String text) {
		appendText(((XTextDocument) documentObj).getText(), text);
	}

	public void addTextArea(Object documentObj, String text, int x, int y,
			int width, int height) throws Exception {
		XTextDocument document = (XTextDocument) documentObj;
		XMultiServiceFactory factory = query(XMultiServiceFactory.class, document);

		XTextFrame xFrame = create(XTextFrame.class, factory, X_TEXTFRAME);
		
		setPositionAndSize(xFrame, x, y, width, height);
		
		XText xText = document.getText();
		xText.insertTextContent(xText.getEnd(), xFrame, false);

		appendText(xFrame.getText(), text);
	}

	private void setPositionAndSize(Object obj, int x, int y, int width,
			int height) throws UnknownPropertyException, PropertyVetoException, IllegalArgumentException, WrappedTargetException {
		XPropertySet xFrameProps = query(XPropertySet.class, obj);
		
		xFrameProps.setPropertyValue(PROP_SIZETYPE, SizeType.FIX);
		xFrameProps.setPropertyValue(PROP_WIDTH, (int)(width * PIXEL_RATIO));
		xFrameProps.setPropertyValue(PROP_HEIGHT, (int)(height * PIXEL_RATIO));
		xFrameProps.setPropertyValue(PROP_HORIORIENT, HoriOrientation.NONE);
		xFrameProps.setPropertyValue(PROP_HORIPOSITION, (int)(x * PIXEL_RATIO));
		xFrameProps.setPropertyValue(PROP_VERTORIENT, VertOrientation.NONE);
		xFrameProps.setPropertyValue(PROP_VERTPOSITION, (int)(y * PIXEL_RATIO));
	}

	public Object createTextDocument() throws IOException,
			IllegalArgumentException {
		PropertyValue[] loadProps = new PropertyValue[1];
		loadProps[0] = new PropertyValue();
		loadProps[0].Name = PROP_HIDDEN;
		loadProps[0].Value = IS_HIDDEN;
		return query(XTextDocument.class, _loader.loadComponentFromURL(OUT_URL,
				BLANK, 0, loadProps));
	}

	private void appendText(XText xText, String text) {
		xText.insertString(xText.getEnd(), text, false);
	}

	private <T> T query(Class<T> type, Object obj) {
		return UnoRuntime.queryInterface(type, obj);
	}
	
	private <T> T create(Class<T> type, XMultiServiceFactory factory, String path) throws Exception {
		return query(type, factory.createInstance(path));
	}
	
	public static void main(String[] args) throws Exception {
		Facade facade = new Facade();
		Object document = facade.createTextDocument();
		facade.addTextArea(document, "test text\non two lines.", 100, 200, 300, 400);
		facade.save(document, "/home/chubyqc/textarea.odt");
	}
}
