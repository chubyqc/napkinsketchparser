package nsp.client.geom;

public class Rectangle {

	private int _minx;
	private int _miny;
	private int _maxx;
	private int _maxy;
	private int _height;
	private int _width;
	
	public Rectangle(int minx, int miny, int maxx, int maxy) {
		_minx = minx;
		_miny = miny;
		_maxx = maxx;
		_maxy = maxx;
		_height = _maxy - _miny;
		_width = _maxx - _minx;
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
