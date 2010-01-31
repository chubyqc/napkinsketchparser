package nsp.client.widgets.tools;

import nsp.client.widgets.AbstractWidget;
import nsp.client.widgets.canvas.DrawingCanvas;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractTool extends AbstractWidget {
	
	private ButtonBase _button;
	private DrawingCanvas _canvas;
	
	public AbstractTool() {
		_button = createButton(getName());
		_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clicked(_button);
			}
		});
	}
	
	protected abstract void clicked(ButtonBase button);
	
	protected ButtonBase createButton(String name) {
		return new Button(name);
	}
	
	@Override
	public Widget getWidget() {
		return _button;
	}
	
	void setCanvas(DrawingCanvas canvas) {
		_canvas = canvas;
	}
	
	protected DrawingCanvas getCanvas() {
		return _canvas;
	}
	
	protected abstract String getName();
}
