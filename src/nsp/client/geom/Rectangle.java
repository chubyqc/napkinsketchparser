package nsp.client.geom;

import java.io.Serializable;

public class Rectangle implements Serializable {

	private static final long serialVersionUID = 1L;
	private int _minx;
	private int _miny;
	private int _maxx;
	private int _maxy;
	private int _height;
	private int _width;
	
	public Rectangle() {}
	
	public Rectangle(int minx, int miny, int width, int height) {
		_minx = minx;
		_miny = miny;
		_maxx = _minx + width - 1;
		_maxy = _miny + height - 1;
		_height = height;
		_width = width;
	}
	
	public void reset(int x, int y) {
		_minx = _maxx = x;
		_miny = _maxy = y;
	}
	
	public void extend(int x, int y) {
		_minx = Math.min(_minx, x);
		_miny = Math.min(_miny, y);
		_maxx = Math.max(_maxx, x);
		_maxy = Math.max(_maxy, y);
	}
	
	public boolean isValid() {
		return _width > 0 && _height > 0;
	}
	
	public void computeSize() {
		_width = _maxx - _minx;
		_height = _maxy - _miny;
	}
	
	public int getMinX() {
		return _minx;
	}
	
	public int getMinY() {
		return _miny;
	}
	
	public int getMaxX() {
		return _maxx;
	}
	
	public int getMaxY() {
		return _maxy;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public int getWidth() {
		return _width;
	}
}
