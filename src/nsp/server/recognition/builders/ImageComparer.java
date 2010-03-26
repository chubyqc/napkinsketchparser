package nsp.server.recognition.builders;

import java.awt.image.BufferedImage;

public class ImageComparer extends Comparer {

	ImageComparer(BufferedImage model) {
		super(null, model);
	}
	
	@Override
	public Result compare(BufferedImage shape) {
		return new ImageResult(nsp.server.image.Comparer.get().compare(shape, _model),
				_model);
	}
}
