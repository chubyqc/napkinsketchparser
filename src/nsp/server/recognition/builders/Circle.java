package nsp.server.recognition.builders;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import nsp.server.image.Utils;

public class Circle implements IShapeBuilder {
	
	private IComparer _comparer;
	
	public Circle() {
		_comparer = new Comparer(this, Utils.get().loadImage("/home/chubyqc/circle.png"));
	}

	@Override
	public Shape build(Result result, int width, int height) {
		return new Ellipse2D.Double(0, 0, width, height);
	}

	public IComparer getComparer() {
		return _comparer;
	}
}
