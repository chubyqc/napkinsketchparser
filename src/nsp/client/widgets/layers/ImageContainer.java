package nsp.client.widgets.layers;

import nsp.client.geom.Rectangle;
import nsp.client.widgets.AbstractWidget;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ImageContainer extends AbstractWidget {
	
	private static final String STYLE_SELECTED = "imageSelected";
	
	private Image _image;
	private LayerHandle _handle;
	private int _x;
	private int _y;
	private int _z;
	
	public ImageContainer(String path, ImagesManager manager, int z,
			String layerId) {
		_image = new Image();
		_image.getElement().getStyle().setZIndex(_z = z);
		_handle = new LayerHandle(manager, this, layerId);
		updateUrl(path);
	}
	
	public ImageContainer() {
		_image = new Image();
	}
	
	void selected() {
		_image.addStyleName(STYLE_SELECTED);
	}
	
	void unselected() {
		_image.removeStyleName(STYLE_SELECTED);
	}
	
	public void updateUrl(String path) {
		_image.setUrl(path);
	}
	
	void moveUp() {
		_image.getElement().getStyle().setZIndex(++_z);
	}
	
	void moveDown() {
		_image.getElement().getStyle().setZIndex(--_z);
	}
	
	public LayerHandle getHandle() {
		return _handle;
	}
	
	public void setPosition(AbsolutePanel canvas, int x, int y) {
		_x = x;
		_y = y;
		canvas.setWidgetPosition(getWidget(), _x, _y);
	}
	
	public boolean contains(int x, int y) {
		return x >= _x && y >= _y && 
			x <= _x + getWidget().getOffsetWidth() && 
			y <= _y + getWidget().getOffsetHeight();
	}
	
	public Rectangle getInnerRectangle(Rectangle rectangle) {
		int left = Math.max(0, rectangle.getMinX() - _x);
		int top = Math.max(0, rectangle.getMinY() - _y);
		int width = Math.min(getWidget().getOffsetWidth() - left, rectangle.getWidth());
		int height = Math.min(getWidget().getOffsetHeight() - top, rectangle.getHeight());
		
		return new Rectangle(left, top, width, height);
	}

	@Override
	public Widget getWidget() {
		return _image;
	}

}
