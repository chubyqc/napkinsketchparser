package nsp.client.geom;

public class Rectangle {

	private int _minx;
	private int _miny;
	private int _maxx;
	private int _maxy;
	private int _height;
	private int _width;
	
	public Rectangle(int minx, int miny, int width, int height) {
		_minx = minx;
		_miny = miny;
		_maxx = _minx + width;
		_maxy = _miny + height;
		_height = height;
		_width = width;
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
