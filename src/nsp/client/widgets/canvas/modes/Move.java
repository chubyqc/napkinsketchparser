package nsp.client.widgets.canvas.modes;

public class Move extends AbstractMode {
	
	private int _initX;
	private int _initY;
	private boolean _isValid;
	
	@Override
	protected void mousePressed() {
		_initX = getCanvas().getBorderLeft();
		_initY = getCanvas().getBorderTop();
		_isValid = getInitialX() >= _initX && 
			getInitialX() <= _initX + getBorder().getWidget().getOffsetWidth() &&
			getInitialY() >= _initY && 
			getInitialY() <= _initY + getBorder().getWidget().getOffsetHeight();
	}
	
	@Override
	protected void mouseMoved(int x, int y) {
		if (_isValid) {
			int newX = _initX + x;
			int newY = _initY + y;
			getCanvas().setBorderPosition(newX, newY);
			getCanvas().setImagePosition(newX, newY);
		}
	}
}
