package nsp.server.recognition.builders;

import java.awt.Shape;

import nsp.server.Utils;

public class Image implements IShapeBuilder {
	
	private IComparer _comparer;
	
	public Image(String path) {
		_comparer = new ImageComparer(Utils.get().loadImage(path));
	}

	@Override
	public Shape build(Result result, int width, int height) {
		return null;
	}

	public IComparer getComparer() {
		return _comparer;
	}
}
