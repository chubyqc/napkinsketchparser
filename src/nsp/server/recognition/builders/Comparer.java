package nsp.server.recognition.builders;

import java.awt.image.BufferedImage;

public class Comparer implements IComparer {
	
	private BufferedImage _model;
	private IShapeBuilder _builder;

	Comparer(IShapeBuilder builder, BufferedImage model) {
		_builder = builder;
		_model = model;
	}

	@Override
	public Result compare(BufferedImage shape) {
		return new Result(_builder, nsp.server.image.Comparer.get().compare(shape, _model));
	}
}
