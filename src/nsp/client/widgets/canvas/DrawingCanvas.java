package nsp.client.widgets.canvas;

import java.util.HashSet;
import java.util.Set;

import nsp.client.widgets.AbstractWidget;
import nsp.client.widgets.ImageContainer;
import nsp.client.widgets.canvas.modes.AbstractMode;
import nsp.client.widgets.canvas.modes.Move;
import nsp.client.widgets.canvas.modes.Select;

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

	private AbstractMode _currentMode;
	private AbstractMode _moveMode;
	private AbstractMode _selectMode;
	
	private ImageContainer _lastImage;
	
	public DrawingCanvas(int width, int height) {
		_canvas = new AbsolutePanel();
		_focusPanel = new FocusPanel(_canvas);
		_images = new HashSet<ImageContainer>();
		
		_canvas.setPixelSize(width, height);
		setSelectionBorder(new SelectionBorder(this));
		addMouseListeners();
		
		initModes();
	}
	
	private void initModes() {
		_moveMode = initMode(new Move());
		_selectMode = initMode(new Select());
		_currentMode = _selectMode;
	}
	
	private AbstractMode initMode(AbstractMode mode) {
		mode.setCanvas(this);
		mode.setSelectionBorder(_border);
		return mode;
	}
	
	public void addImage(String url) {
		addImage(0, 0, url);
	}
	
	public void addImage(int x, int y, String url) {
		ImageContainer image = new ImageContainer(url);
		_images.add(image);
		image.appendTo(_canvas);
		_canvas.setWidgetPosition(image.getWidget(), x, y);
		_lastImage = image;
	}
	
	private void setSelectionBorder(SelectionBorder border) {
		_border = border;
		_border.appendTo(_canvas);
	}
	
	public void activateMove() {
		_currentMode = _moveMode;
	}
	
	public void deactivateMove() {
		_currentMode = _selectMode;
	}
	
	public int getSelectionLeft() {
		return _canvas.getWidgetLeft(_border.getWidget());
	}
	
	public int getSelectionTop() {
		return _canvas.getWidgetTop(_border.getWidget());
	}
	
	public int getSelectionRight() {
		return getSelectionLeft() + _border.getWidget().getOffsetWidth();
	}
	
	public int getSelectionBottom() {
		return getSelectionTop() + _border.getWidget().getOffsetHeight();
	}
	
	@Override
	public Widget getWidget() {
		return _focusPanel;
	}
	
	public void setBorderPosition(int x, int y) {
		_canvas.setWidgetPosition(_border.getWidget(), x, y);
	}
	
	public void setAttachedImagePosition(int x, int y) {
		_canvas.setWidgetPosition(_lastImage.getWidget(), x, y);
	}
	
	public int getBorderLeft() {
		return _canvas.getWidgetLeft(_border.getWidget());
	}
	
	public int getBorderTop() {
		return _canvas.getWidgetTop(_border.getWidget());
	}
	
	private void addMouseListeners() {
		final MouseMoveHandler moveHandler = new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				event.preventDefault();
				event.stopPropagation();
				_currentMode.onMouseMove(event.getX(), event.getY());
			}
		};
		_focusPanel.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
				event.stopPropagation();
				_handlerRegistration = _focusPanel.addMouseMoveHandler(moveHandler);
				_currentMode.onMouseDown(event.getX(), event.getY());
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
