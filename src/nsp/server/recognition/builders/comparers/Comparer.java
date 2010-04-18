package nsp.server.recognition.builders.comparers;

import java.awt.image.BufferedImage;

import nsp.server.recognition.builders.IShapeBuilder;
import nsp.server.recognition.builders.results.Result;

public class Comparer implements IComparer {
	
	protected BufferedImage _model;
	private IShapeBuilder _builder;

	public Comparer(IShapeBuilder builder, BufferedImage model) {
		_builder = builder;
		_model = model;
	}

	@Override
	public Result compare(BufferedImage shape) {
		return new Result(_builder, nsp.server.image.Comparer.get().compare(shape, _model));
	}
}
