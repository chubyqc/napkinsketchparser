package nsp.client.widgets.canvas.modes;

import nsp.client.geom.Point;

public class Move extends AbstractMode {
	
	private Point _initialPosition;
	private boolean _isValid;
	
	@Override
	protected void mousePressed() {
		_initialPosition = getCanvas().getBorderPosition();
		_isValid = getInitialX() >= _initialPosition.getX() && 
			getInitialX() <= _initialPosition.getX() + getBorder().getWidget().getOffsetWidth() &&
			getInitialY() >= _initialPosition.getY() && 
			getInitialY() <= _initialPosition.getY() + getBorder().getWidget().getOffsetHeight();
	}
	
	@Override
	protected void mouseMoved(int x, int y) {
		if (_isValid) {
			int newX = _initialPosition.getX() + x;
			int newY = _initialPosition.getY() + y;
			getCanvas().setBorderPosition(newX, newY);
			getCanvas().setImagePosition(newX, newY);
		}
	}
}
