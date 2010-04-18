package nsp.server.recognition.builders.comparers;

import java.awt.image.BufferedImage;

import nsp.server.recognition.builders.results.ImageResult;
import nsp.server.recognition.builders.results.Result;

public class ImageComparer extends Comparer {

	public ImageComparer(BufferedImage model) {
		super(null, model);
	}
	
	@Override
	public Result compare(BufferedImage shape) {
		return new ImageResult(nsp.server.image.Comparer.get().compare(shape, _model),
				_model);
	}
}
