package nsp.server.recognition.builders;

import java.awt.Shape;

import nsp.server.Utils;
import nsp.server.recognition.builders.comparers.IComparer;
import nsp.server.recognition.builders.comparers.ImageComparer;
import nsp.server.recognition.builders.results.Result;

public class Image implements IShapeBuilder {
	
	private IComparer _comparer;
	
	protected Image(IComparer comparer) {
		_comparer = comparer;
	}
	
	public Image(String path) {
		this(new ImageComparer(Utils.get().loadImage(path)));
	}

	@Override
	public Shape build(Result result, int width, int height) {
		return null;
	}

	public IComparer getComparer() {
		return _comparer;
	}
}
