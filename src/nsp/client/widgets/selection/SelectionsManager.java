package nsp.client.widgets.selection;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import nsp.client.Styles;
import nsp.client.geom.Rectangle;
import nsp.client.widgets.AbstractWidget;
import nsp.client.widgets.canvas.DrawingCanvas;

public class SelectionsManager extends AbstractWidget {
	
	private VerticalPanel _panel;
	private DrawingCanvas _canvas;
	private List<SelectionHandle> _handles;
	private int _currentId;
	private SelectionHandle _selected;

	public SelectionsManager(DrawingCanvas canvas) {
		_canvas = canvas;
		_handles = new LinkedList<SelectionHandle>();
		_panel = new VerticalPanel();
		_panel.addStyleName(Styles.get().getSelectionList());
		_currentId = -1;
	}
	
	public void clear() {
		for (SelectionHandle handle : _handles) {
			_panel.remove(handle.getWidget());
		}
		_handles.clear();
	}
	
	public void add(Rectangle bounds) {
		SelectionHandle handle = new SelectionHandle(++_currentId, bounds, _canvas, this);
		_handles.add(handle);
		handle.appendTo(_panel);
	}

	@Override
	public Widget getWidget() {
		return _panel;
	}

	void selected(SelectionHandle selectionHandle) {
		if (_selected != null) {
			_selected.unselect();
		}
		_selected = selectionHandle;
	}
}
