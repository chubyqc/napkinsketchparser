package nsp.client.widgets.canvas.modes;

import nsp.client.widgets.canvas.DrawingCanvas;
import nsp.client.widgets.selection.SelectionBorder;

public abstract class AbstractMode {
	
	private int _initialX;
	private int _initialY;
	private DrawingCanvas _canvas;
	private SelectionBorder _border;
	
	public void setCanvas(DrawingCanvas canvas) {
		_canvas = canvas;
	}
	
	public void setSelectionBorder(SelectionBorder border) {
		_border = border;
	}
	
	protected DrawingCanvas getCanvas() {
		return _canvas;
	}
	
	protected SelectionBorder getBorder() {
		return _border;
	}

	public void onMouseDown(int x, int y) {
		_initialX = x;
		_initialY = y;
		mousePressed();
	}

	public void onMouseMove(int x, int y) {
		mouseMoved(x - _initialX, y - _initialY);
	}
	
	protected void mousePressed() {}
	
	protected int getInitialX() {
		return _initialX;
	}
	
	protected int getInitialY() {
		return _initialY;
	}

	protected abstract void mouseMoved(int xOffset, int yOffset);
}
