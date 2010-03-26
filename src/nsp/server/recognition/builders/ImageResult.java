package nsp.server.recognition.builders;

import java.awt.image.BufferedImage;

public class ImageResult extends Result {
	
	private BufferedImage _result;

	ImageResult(double score, BufferedImage result) {
		super(null, score);
		_result = result;
	}
	
	@Override
	public void build(int width, int height) {}
	
	@Override
	public BufferedImage toImage(int width, int height) {
		return _result;
	}
}
