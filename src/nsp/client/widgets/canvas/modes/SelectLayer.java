package nsp.client.widgets.canvas.modes;

import nsp.client.widgets.layers.ImagesManager;

public class SelectLayer extends AbstractMode {
	
	private ImagesManager _manager;
	
	public SelectLayer(ImagesManager manager) {
		_manager = manager;
	}

	@Override
	protected void mouseMoved(int xOffset, int yOffset) {
		_manager.selectByPosition(getInitialX() + xOffset, getInitialY() + yOffset);
	}
}
