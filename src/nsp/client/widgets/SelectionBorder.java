package nsp.client.widgets;

import nsp.client.GWTFacade;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SelectionBorder extends AbstractWidget {
	
	private SimplePanel _border;
	private DrawingCanvas _canvas;
	
	public SelectionBorder(DrawingCanvas canvas) {
		_border = new SimplePanel();
		_border.getElement().getStyle().setBorderStyle(BorderStyle.DASHED);
		_border.getElement().getStyle().setZIndex(1000);
		_border.setPixelSize(0, 0);
		_canvas = canvas;
		_canvas.setSelectionBorder(this);
	}
	
	void setSize(int width, int height) {
		_border.setPixelSize(width, height);
	}
	
	void cropImage(final int left, final int top) {
		int right = left + _border.getOffsetWidth();
		int bottom = top + _border.getOffsetHeight();
		GWTFacade.get().cropImage(left, top, right, bottom,
				new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				_canvas.addImage(left, top, result);
			}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}

	@Override
	protected Widget getWidget() {
		return _border;
	}
}
