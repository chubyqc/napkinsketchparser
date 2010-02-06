package nsp.client.widgets.layers;

import nsp.client.widgets.AbstractWidget;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ImageContainer extends AbstractWidget {
	
	private static final String STYLE_SELECTED = "imageSelected";
	
	private Image _image;
	private int _z;
	
	public ImageContainer(String path, int z) {
		_image = new Image();
		_image.getElement().getStyle().setZIndex(_z = z);
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

	@Override
	public Widget getWidget() {
		return _image;
	}

}
