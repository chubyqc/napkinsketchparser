package nsp.client.widgets.canvas.modes;

import nsp.client.geom.Point;

public class Move extends AbstractMode {
	
	private Point _initialPosition;
	private boolean _isValid;
	
	@Override
	protected void mousePressed() {
		_initialPosition = getCanvas().getImagePosition();
		_isValid = getCanvas().isWithinImage(getInitialX(), getInitialY());
	}
	
	@Override
	protected void mouseMoved(int x, int y) {
		if (_isValid) {
			int newX = _initialPosition.getX() + x;
			int newY = _initialPosition.getY() + y;
			getCanvas().setImagePosition(newX, newY);
		}
	}
}
