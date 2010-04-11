package nsp.client.geom;

import java.io.Serializable;

public class Point implements Serializable {

	private static final long serialVersionUID = 1L;
	private int _x;
	private int _y;
	private int _hash;
	
	public Point() {}
	
	public Point(int x, int y) {
		_x = x;
		_y = y;
		_hash = hash(x, y);
	}
	
	public void set(int x, int y) {
		_x = x;
		_y = y;
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	@Override
	public boolean equals(Object obj) {
		return hashCode() == obj.hashCode();
	}
	
	@Override
	public int hashCode() {
		return _hash;
	}
	
	private int hash(int x, int y) {
		String strX = String.valueOf(x);
		String strY = String.valueOf(y);
		for (int i = strX.length(); i < strY.length(); ++i) {
			strX = "0" + strX;
		}
		for (int i = strY.length(); i < strX.length(); ++i) {
			strY = "0" + strY;
		}
		return (strX + strY).hashCode();
	}
}
