package nsp.client.widgets.layers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nsp.client.Styles;
import nsp.client.widgets.AbstractWidget;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class ImagesManager extends AbstractWidget {
	
	private static final int BASE_IMAGE_Z = 500;

	private static int _layerIdCpt = 0;

	private VerticalPanel _container;
	private Map<Widget, ImageContainer> _images;
	
	private Set<ImageContainer> _currentImages;
	private Set<LayerHandle> _currentHandles;
	private int _highestZ;
	
	public ImagesManager() {
		_images = new HashMap<Widget, ImageContainer>();
		_currentImages = new HashSet<ImageContainer>();
		_currentHandles = new HashSet<LayerHandle>();
		_container = new VerticalPanel();
		_container.setStyleName(Styles.get().getLayerList());
		_highestZ = BASE_IMAGE_Z;
	}
	
	public ImageContainer createImage(String url) {
		unselectCurrent();
		ImageContainer image = new ImageContainer(url, this, _highestZ++, String.valueOf(++_layerIdCpt));
		LayerHandle handle = image.getHandle();
		_images.put(handle.getWidget(), image);
		_container.insert(handle.getWidget(), 0);
		select(handle, image, false);
		return image;
	}
	
	private void unselectCurrent() {
		for (ImageContainer image : _currentImages) {
			image.unselected();
			image.getHandle().unselected();
		}
		_currentImages.clear();
	}
	
	public ImageContainer getCurrentImage() {
		ImageContainer image = null;
		for (ImageContainer img : _currentImages) {
			image = img;
			break;
		}
		return image;
	}
	
	public Collection<ImageContainer> getSelectedLayers() {
		return _currentImages;
	}
	
	void select(LayerHandle handle, ImageContainer image, boolean append) {
		if (!append) {
			unselectCurrent();
		}
		image.selected();
		handle.selected(append);
		_currentHandles.add(handle);
		_currentImages.add(image);
	}
	
	boolean moveDown(LayerHandle handle, ImageContainer image) {
		int index = _container.getWidgetIndex(handle.getWidget());
		if (index < _container.getWidgetCount() - 1) {
			_container.insert(handle.getWidget(), index + 2);
			image.moveDown();
			_images.get(_container.getWidget(index)).moveUp();
			return true;
		}
		return false;
	}
	
	boolean moveUp(LayerHandle handle, ImageContainer image) {
		int index = _container.getWidgetIndex(handle.getWidget());
		if (index > 0) {
			_container.insert(handle.getWidget(), index - 1);
			image.moveUp();
			_images.get(_container.getWidget(index)).moveDown();
			return true;
		}
		return false;
	}

	public void delete(LayerHandle layerHandle, ImageContainer image) {
		while (moveUp(layerHandle, image));
		_images.remove(layerHandle.getWidget());
		
		if (_currentImages.contains(image)) {
			for (ImageContainer item : _images.values()) {
				select(item.getHandle(), item, false);
				break;
			}
		}
		image.getWidget().removeFromParent();
		layerHandle.getWidget().removeFromParent();
	}

	public void selectByPosition(int x, int y) {
		for (ImageContainer image : _images.values()) {
			if (image.contains(x, y)) {
				select(image.getHandle(), image, false);
				break;
			}
		}
	}
	
	public String getNextLayerId() {
		return String.valueOf(_layerIdCpt + 1);
	}
	
	public void refresh() {
		for (ImageContainer image : _currentImages) {
			image.refresh();
			break;
		}
	}

	@Override
	public Widget getWidget() {
		return _container;
	}
}
