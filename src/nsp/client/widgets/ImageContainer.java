package nsp.client.widgets;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ImageContainer extends AbstractWidget {
	
	private Image _image;
	
	public ImageContainer(String path) {
		_image = new Image();
		updateUrl(path);
	}
	
	public ImageContainer() {
		_image = new Image();
	}
	
	public void updateUrl(String path) {
		_image.setUrl(path);
	}

	@Override
	public Widget getWidget() {
		return _image;
	}

}
