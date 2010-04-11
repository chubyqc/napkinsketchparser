package nsp.server.recognition;

import java.awt.image.BufferedImage;

import nsp.client.geom.Rectangle;

public class BoundedImages {
	
	private Rectangle[] _recs;
	private BufferedImage[] _images;

	public BoundedImages(Rectangle[] recs, BufferedImage[] images) {
		_recs = recs;
		_images = images;
	}

	public BufferedImage[] get() {
		return _images;
	}
	
	public Rectangle[] getRecs() {
		return _recs;
	}
}
