package nsp.client.widgets.canvas;

import nsp.client.geom.Point;
import nsp.client.geom.Rectangle;
import nsp.client.widgets.AbstractWidget;
import nsp.client.widgets.canvas.modes.AbstractMode;
import nsp.client.widgets.canvas.modes.Move;
import nsp.client.widgets.canvas.modes.Select;
import nsp.client.widgets.layers.ImageContainer;
import nsp.client.widgets.layers.ImagesManager;

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
	private SelectionBorder _border;
	private HandlerRegistration _handlerRegistration;

	private AbstractMode _currentMode;
	private AbstractMode _moveMode;
	private AbstractMode _selectMode;
	
	private ImagesManager _imagesManager;
	
	public DrawingCanvas(int width, int height,
			ImagesManager imagesManager) {
		_canvas = new AbsolutePanel();
		_focusPanel = new FocusPanel(_canvas);
		_imagesManager = imagesManager;
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
		ImageContainer image = _imagesManager.createImage(url);
		image.appendTo(_canvas);
		_canvas.setWidgetPosition(image.getWidget(), x, y);
	}
	
	private void setSelectionBorder(SelectionBorder border) {
		_border = border;
		_border.appendTo(_canvas);
	}
	
	public void activateMove() {
		_currentMode = _moveMode;
		_border.hide();
	}
	
	public void deactivateMove() {
		_currentMode = _selectMode;
		_border.show();
	}
	
	public Rectangle getSelectionBounds() {
		return new Rectangle(_canvas.getWidgetLeft(_border.getWidget()), 
				_canvas.getWidgetTop(_border.getWidget()), 
				_border.getWidget().getOffsetWidth(),
				_border.getWidget().getOffsetHeight());
	}
	
	@Override
	public Widget getWidget() {
		return _focusPanel;
	}
	
	public void setBorderPosition(int x, int y) {
		_canvas.setWidgetPosition(_border.getWidget(), x, y);
	}
	
	public void setImagePosition(int x, int y) {
		_canvas.setWidgetPosition(_imagesManager.getCurrentImageWidget(), x, y);
	}
	
	public Point getImagePosition() {
		Widget image = _imagesManager.getCurrentImageWidget();
		return new Point(_canvas.getWidgetLeft(image), _canvas.getWidgetTop(image));
	}

	public boolean isWithinImage(int x, int y) {
		Point initialPosition = getImagePosition();
		Widget image = _imagesManager.getCurrentImageWidget();
		return x >= initialPosition.getX() && 
			x <= initialPosition.getX() + image.getOffsetWidth() &&
			y >= initialPosition.getY() && 
			y <= initialPosition.getY() + image.getOffsetHeight();
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
