package nsp.server.recognition.builders;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import nsp.server.Utils;
import nsp.server.recognition.builders.comparers.Comparer;
import nsp.server.recognition.builders.comparers.IComparer;
import nsp.server.recognition.builders.results.Result;

public class Circle implements IShapeBuilder {
	
	private IComparer _comparer;
	
	public Circle() {
		_comparer = new Comparer(this, Utils.get().loadImage(Utils.get().toRealPath("shapes/circle.png")));
	}

	@Override
	public Shape build(Result result, int width, int height) {
		return new Ellipse2D.Double(0, 0, width, height);
	}

	public IComparer getComparer() {
		return _comparer;
	}
}
