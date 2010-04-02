package nsp.server.recognition;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import nsp.client.geom.Point;
import nsp.client.geom.Rectangle;
import nsp.server.image.Simplifier;

public class ShapeFinder {
	private static ShapeFinder _instance = new ShapeFinder();
	public static ShapeFinder get() {
		return _instance;
	}
	
	private static final int TOLERANCE = 140;
	
	private ShapeFinder() {}
	
	public Rectangle find(BufferedImage img, int minX, int minY, int maxX, int maxY) {
		Set<Point> inspected = new HashSet<Point>();
		Rectangle result = new Rectangle(minX, minY, 0, 0);
		Point first = findFirstPixelOn(img, inspected, minX, minY, maxX, maxY);
		if (first != null) {
			result.reset(first.getX(), first.getY());
			inspect(img, inspected, result, first, minX, minY, maxX, maxY);
		}
		result.computeSize();
		return result;
	}

	private void inspect(final BufferedImage img, final Set<Point> inspected, final Rectangle result, 
			Point pixel, final int minX, final int minY, final int maxX, final int maxY) {
		if (!inspected.contains(pixel)) {
			inspected.add(pixel);
			int x = pixel.getX();
			int y = pixel.getY();
			if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
				if (Simplifier.get().isPixelOn(img.getRGB(x, y), TOLERANCE)) {
					result.extend(x, y);
					int startX = x - 1, startY = y - 1, endX = x + 1, endY = y + 1;
					for (int i = startY; i <= endY; ++i) {
						for (int j = startX; j <= endX; ++j) {
							inspect(img, inspected, result, new Point(j, i), 
									minX, minY, maxX, maxY);
						}
					}
				}
			}
		}
	}
	
	private Point findFirstPixelOn(BufferedImage img, Set<Point> inspected, 
			int minX, int minY, int maxX, int maxY) {
		for (int i = minY; i <= maxY; ++i) {
			for (int j = minX; j <= maxX; ++j) {
				if (Simplifier.get().isPixelOn(img.getRGB(j, i), TOLERANCE)) {
					return new Point(j, i);
				} else {
					inspected.add(new Point(j, i));
				}
			}
		}
		return null;
	}
}
