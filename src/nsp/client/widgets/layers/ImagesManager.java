package nsp.client.widgets.layers;

import java.util.HashSet;
import java.util.Set;

import nsp.client.Styles;
import nsp.client.widgets.AbstractWidget;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class ImagesManager extends AbstractWidget {

	private VerticalPanel _container;
	private Set<ImageContainer> _images;
	private ImageContainer _currentImage;
	
	public ImagesManager() {
		_images = new HashSet<ImageContainer>();
		_container = new VerticalPanel();
		_container.setStyleName(Styles.get().getLayerList());
	}
	
	public ImageContainer createImage(String url) {
		ImageContainer image = new ImageContainer(url);
		_images.add(image);
		new LayerHandle().appendTo(_container);
		setCurrentImage(image);
		return image;
	}
	
	void setCurrentImage(ImageContainer image) {
		_currentImage = image;
	}
	
	public Widget getCurrentImageWidget() {
		return _currentImage.getWidget();
	}

	@Override
	public Widget getWidget() {
		return _container;
	}
}
