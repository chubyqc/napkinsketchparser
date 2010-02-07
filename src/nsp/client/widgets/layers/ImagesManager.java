package nsp.client.widgets.layers;

import java.util.HashMap;
import java.util.Map;

import nsp.client.Styles;
import nsp.client.widgets.AbstractWidget;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class ImagesManager extends AbstractWidget {
	
	private static final int BASE_IMAGE_Z = 500;

	private VerticalPanel _container;
	private Map<Widget, ImageContainer> _images;
	
	private ImageContainer _currentImage;
	private LayerHandle _currentHandle;
	private int _highestZ;
	
	public ImagesManager() {
		_images = new HashMap<Widget, ImageContainer>();
		_container = new VerticalPanel();
		_container.setStyleName(Styles.get().getLayerList());
		_highestZ = BASE_IMAGE_Z;
	}
	
	public ImageContainer createImage(String url) {
		unselectCurrent();
		ImageContainer image = new ImageContainer(url, this, _highestZ++);
		LayerHandle handle = image.getHandle();
		_images.put(handle.getWidget(), image);
		_container.insert(handle.getWidget(), 0);
		select(handle, image);
		return image;
	}
	
	private void unselectCurrent() {
		if (_currentImage != null) {
			_currentImage.unselected();
			_currentHandle.unselected();
		}
	}
	
	public ImageContainer getCurrentImage() {
		return _currentImage;
	}
	
	void select(LayerHandle handle, ImageContainer image) {
		unselectCurrent();
		_currentHandle = handle;
		_currentImage = image;
		_currentImage.selected();
		_currentHandle.selected();
	}
	
	void moveDown(LayerHandle handle, ImageContainer image) {
		int index = _container.getWidgetIndex(handle.getWidget());
		if (index < _container.getWidgetCount() - 1) {
			_container.insert(handle.getWidget(), index + 2);
			image.moveDown();
			_images.get(_container.getWidget(index)).moveUp();
		}
	}
	
	void moveUp(LayerHandle handle, ImageContainer image) {
		int index = _container.getWidgetIndex(handle.getWidget());
		if (index > 0) {
			_container.insert(handle.getWidget(), index - 1);
			image.moveUp();
			_images.get(_container.getWidget(index)).moveDown();
		}
	}

	public void selectByPosition(int x, int y) {
		for (ImageContainer image : _images.values()) {
			if (image.contains(x, y)) {
				select(image.getHandle(), image);
				break;
			}
		}
	}

	@Override
	public Widget getWidget() {
		return _container;
	}
}
