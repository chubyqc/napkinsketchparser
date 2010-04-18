package nsp.server.recognition.builders;

import java.awt.Shape;

import nsp.server.recognition.builders.comparers.IComparer;
import nsp.server.recognition.builders.comparers.LineComparer;
import nsp.server.recognition.builders.results.LineResult;
import nsp.server.recognition.builders.results.Result;

public class Line implements IShapeBuilder {
	
	private IComparer _comparer;
	
	public Line() {
		_comparer = new LineComparer(this);
	}

	@Override
	public Shape build(Result result, int width, int height) {
		return ((LineResult)result).toLine(width, height);
	}

	@Override
	public IComparer getComparer() {
		return _comparer;
	}

}
