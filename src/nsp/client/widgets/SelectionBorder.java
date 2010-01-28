package nsp.client.widgets;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SelectionBorder extends AbstractWidget {
	
	private SimplePanel _border;
	
	public SelectionBorder(DrawingCanvas canvas) {
		_border = new SimplePanel();
		_border.getElement().getStyle().setBorderStyle(BorderStyle.DASHED);
		_border.setPixelSize(0, 0);
		canvas.setSelectionBorder(this);
	}
	
	void setSize(int width, int height) {
		_border.setPixelSize(width, height);
	}

	@Override
	protected Widget getWidget() {
		return _border;
	}
}
