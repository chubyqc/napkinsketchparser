package nsp.client.widgets.canvas.modes;

import nsp.client.geom.Point;

public class Move extends AbstractMode {
	
	private int _initX;
	private int _initY;
	private boolean _isValid;
	
	@Override
	protected void mousePressed() {
		Point position = getCanvas().getImagePosition();
		_initX = position.getX();
		_initY = position.getY();
		_isValid = getCanvas().isWithinImage(getInitialX(), getInitialY());
	}
	
	@Override
	protected void mouseMoved(int x, int y) {
		if (_isValid) {
			getCanvas().setImagePosition(_initX + x, _initY + y);
		}
	}
}
