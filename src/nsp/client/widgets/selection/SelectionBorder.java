package nsp.client.widgets.selection;

import nsp.client.widgets.AbstractWidget;
import nsp.client.widgets.canvas.DrawingCanvas;

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
	
	public void hide() {
		_border.setVisible(false);
	}
	
	public void show() {
		_border.setVisible(true);
	}

	@Override
	public Widget getWidget() {
		return _border;
	}
}
