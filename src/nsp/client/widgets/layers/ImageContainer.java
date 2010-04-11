package nsp.client.widgets.layers;

import nsp.client.geom.Rectangle;
import nsp.client.widgets.AbstractWidget;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ImageContainer extends AbstractWidget {
	
	private static final String STYLE_SELECTED = "imageSelected";
	private static final String URL_MIDDLEPART = "?r=";
	
	private Image _image;
	private LayerHandle _handle;
	private int _x;
	private int _y;
	private int _z;
	private String _baseUrl;
	private int _refreshCount;
	private ImagesManager _manager;
	
	public ImageContainer(String path, ImagesManager manager, int z,
			String layerId) {
		_image = new Image();
		_image.getElement().getStyle().setZIndex(_z = z);
		_handle = new LayerHandle(manager, this, layerId);
		_refreshCount = 0;
		_baseUrl = path;
		_manager = manager;
		refresh();
	}
	
	public ImageContainer() {
		_image = new Image();
	}
	
	public int getLeft() {
		return _x;
	}
	
	public int getTop() {
		return _y;
	}
	
	public int getRight() {
		return _x + _image.getWidth();
	}
	
	public int getBottom() {
		return _y + _image.getHeight();
	}
	
	void selected() {
		_image.addStyleName(STYLE_SELECTED);
		_manager.setCurrentPosition(_x, _y);
	}
	
	void unselected() {
		_image.removeStyleName(STYLE_SELECTED);
	}
	
	void moveUp() {
		_image.getElement().getStyle().setZIndex(++_z);
	}
	
	void moveDown() {
		_image.getElement().getStyle().setZIndex(--_z);
	}
	
	public void refresh() {
		_image.setUrl(_baseUrl + URL_MIDDLEPART + ++_refreshCount);
	}
	
	public LayerHandle getHandle() {
		return _handle;
	}
	
	public void setPosition(AbsolutePanel canvas, int x, int y) {
		_x = x;
		_y = y;
		canvas.setWidgetPosition(getWidget(), _x, _y);
		_manager.setCurrentPosition(_x, _y);
	}
	
	public boolean contains(int x, int y) {
		return x >= _x && y >= _y && 
			x <= _x + getWidget().getOffsetWidth() && 
			y <= _y + getWidget().getOffsetHeight();
	}
	
	private int getInnerWidth() {
		return getWidget().getElement().getClientWidth();
	}
	
	private int getInnerHeight() {
		return getWidget().getElement().getClientHeight();
	}
	
	public Rectangle getInnerRectangle(Rectangle rectangle) {
		int left = Math.max(0, rectangle.getMinX() - _x);
		int top = Math.max(0, rectangle.getMinY() - _y);
		int width = Math.min(getInnerWidth(), rectangle.getMaxX() - _x) - left;
		int height = Math.min(getInnerHeight(), rectangle.getMaxY() - _y) - top;
		
		return new Rectangle(left, top, width, height);
	}

	@Override
	public Widget getWidget() {
		return _image;
	}

}
