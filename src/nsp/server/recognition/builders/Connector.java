package nsp.server.recognition.builders;

import java.awt.Shape;

import nsp.server.Utils;

public class Connector implements IShapeBuilder {
	
	private IComparer _comparer;
	
	public Connector() {
		_comparer = new ImageComparer(Utils.get().loadImage("shapes/connector.png"));
	}

	@Override
	public Shape build(Result result, int width, int height) {
		return null;
	}

	public IComparer getComparer() {
		return _comparer;
	}
}
