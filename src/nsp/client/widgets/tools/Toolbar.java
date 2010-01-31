package nsp.client.widgets.tools;

import java.util.HashSet;
import java.util.Set;

import nsp.client.widgets.AbstractWidget;
import nsp.client.widgets.canvas.DrawingCanvas;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Toolbar extends AbstractWidget {
	
	private HorizontalPanel _bar;
	private Set<AbstractTool> _tools;
	private DrawingCanvas _canvas;
	
	public Toolbar(DrawingCanvas canvas) {
		_canvas = canvas;
		_bar = new HorizontalPanel();
		_tools = new HashSet<AbstractTool>();
	}

	@Override
	public Widget getWidget() {
		return _bar;
	}

	public void addTool(AbstractTool tool) {
		tool.appendTo(_bar);
		_tools.add(tool);
		tool.setCanvas(_canvas);
	}
}
