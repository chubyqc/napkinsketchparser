package nsp.client.widgets.selection;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import nsp.client.Styles;
import nsp.client.geom.Rectangle;
import nsp.client.widgets.AbstractWidget;
import nsp.client.widgets.canvas.DrawingCanvas;

public class SelectionHandle extends AbstractWidget {

	private Label _handle;
	
	SelectionHandle(int id, final Rectangle bounds, final DrawingCanvas canvas,
			final SelectionsManager manager) {
		_handle = new Label(String.valueOf(id));
		_handle.addStyleName(Styles.get().getSelectionHandle());
		_handle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				canvas.setSelectionBounds(bounds);
				manager.selected(SelectionHandle.this);
				_handle.addStyleName(Styles.get().getSelectedHandle());
			}
		});
	}
	
	void unselect() {
		_handle.removeStyleName(Styles.get().getSelectedHandle());
	}

	@Override
	public Widget getWidget() {
		return _handle;
	}
}
