package nsp.client.widgets;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class DrawingCanvas extends AbstractWidget {
	
	private FocusPanel _focusPanel;
	private AbsolutePanel _canvas;
	private Set<ImageContainer> _images;
	private SelectionBorder _border;
	private HandlerRegistration _handlerRegistration;
	
	private int _initX;
	private int _initY;
	
	public DrawingCanvas(int width, int height) {
		_canvas = new AbsolutePanel();
		_focusPanel = new FocusPanel(_canvas);
		_images = new HashSet<ImageContainer>();
		
		_canvas.setPixelSize(width, height);
		
		addMouseListeners();
	}
	
	public void addImage(String url) {
		ImageContainer image = new ImageContainer(url);
		_images.add(image);
		image.appendTo(_canvas);
	}
	
	void setSelectionBorder(SelectionBorder border) {
		_border = border;
		_border.appendTo(_canvas);
	}
	
	@Override
	protected Widget getWidget() {
		return _focusPanel;
	}
	
	private void addMouseListeners() {
		final MouseMoveHandler moveHandler = new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				event.preventDefault();
				event.stopPropagation();
				_border.setSize(event.getX() - _initX,
						event.getY() - _initY);
			}
		};
		_focusPanel.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
				event.stopPropagation();
				_handlerRegistration = _focusPanel.addMouseMoveHandler(moveHandler);
				_initX = event.getX();
				_initY = event.getY();
				_border.setSize(0, 0);
				_canvas.setWidgetPosition(_border.getWidget(), _initX, _initY);
			}
		});
		_focusPanel.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				event.preventDefault();
				event.stopPropagation();
				_handlerRegistration.removeHandler();
			}
		});
	}
}
