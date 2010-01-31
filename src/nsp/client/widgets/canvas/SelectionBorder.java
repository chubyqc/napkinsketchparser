package nsp.client.widgets.canvas;

import nsp.client.widgets.AbstractWidget;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SelectionBorder extends AbstractWidget {
	
	private SimplePanel _border;
	
	public SelectionBorder(DrawingCanvas canvas) {
		_border = new SimplePanel();
		_border.getElement().getStyle().setBorderStyle(BorderStyle.DASHED);
		_border.getElement().getStyle().setZIndex(1000);
		_border.setPixelSize(0, 0);
	}
	
	public void setSize(int width, int height) {
		_border.setPixelSize(width, height);
	}

	@Override
	public Widget getWidget() {
		return _border;
	}
}
