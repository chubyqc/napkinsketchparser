package nsp.server.recognition.builders;

import java.awt.Shape;

import nsp.server.Utils;

public class Rectangle implements IShapeBuilder {
	
	private IComparer _comparer;
	
	public Rectangle() {
		_comparer = new Comparer(this, Utils.get().loadImage("shapes/rectangle.png"));
	}

	@Override
	public Shape build(Result result, int width, int height) {
		return new java.awt.Rectangle(width, height);
	}

	public IComparer getComparer() {
		return _comparer;
	}
}
