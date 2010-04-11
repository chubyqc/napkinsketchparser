package nsp.server.recognition;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import nsp.client.geom.Point;
import nsp.client.geom.Rectangle;
import nsp.server.image.Simplifier;

public class ShapeFinder {
	private static ShapeFinder _instance = new ShapeFinder();
	public static ShapeFinder get() {
		return _instance;
	}
	
	private static final int PIXEL_MIN = 10;
	
	private ShapeFinder() {}
	
	public Rectangle find(BufferedImage img, int minX, int minY, int maxX, int maxY,
			int tolerance) {
		Set<Point> inspected = new HashSet<Point>();
		Queue<Point> toInspect = new LinkedList<Point>();
		Rectangle result = new Rectangle(minX, minY, 0, 0);
		Point first = findFirstPixelOn(img, inspected, minX, minY, maxX, maxY, tolerance);
		if (first != null) {
			result.reset(first.getX(), first.getY());
			toInspect.add(first);
			inspect(img, inspected, toInspect, result, minX, minY, maxX, maxY, tolerance);
		}
		result.computeSize();
		return result;
	}

	public Rectangle[] findAll(BufferedImage img, int minX, int minY,
			int maxX, int maxY, int tolerance) {
		List<Rectangle> rectangles = new LinkedList<Rectangle>();
		int toExploreMinX = minX;
		int toExploreMinY = minY;
		int toExploreMaxY = maxY;
		Rectangle rectangle = null;
		boolean doNextLine = true;
		do {
			rectangle = find(img, toExploreMinX, toExploreMinY, maxX, toExploreMaxY, tolerance);
			if (rectangle.isValid()) {
				rectangles.add(rectangle);
				if (rectangle.getMaxX() != maxX) {
					doNextLine = false;
					toExploreMinX = rectangle.getMaxX() + 1;
					toExploreMaxY = (toExploreMaxY == maxY) ? 
							rectangle.getMaxY() :
							Math.max(toExploreMaxY, rectangle.getMaxY());
				}
			}
			if (doNextLine) {
				toExploreMinX = minX;
				toExploreMinY = toExploreMaxY + 1;
				toExploreMaxY = maxY;
			}
			doNextLine = true;
		} while (toExploreMinY < maxY);
		return rectangles.toArray(new Rectangle[rectangles.size()]);
	}

	private void inspect(BufferedImage img, Set<Point> inspected, Queue<Point> toInspect,
			Rectangle result, int minX, int minY, int maxX, int maxY, int tolerance) {
		Point pixel;
		int pixelOnCount = 1;
		while ((pixel = toInspect.poll()) != null) {
			if (!inspected.contains(pixel)) {
				inspected.add(pixel);
				int x = pixel.getX();
				int y = pixel.getY();
				if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
					boolean continueInspect = false;
					if (Simplifier.get().isPixelOn(img.getRGB(x, y), tolerance)) {
						result.extend(x, y);
						++pixelOnCount;
						continueInspect = true;
					}
					if (continueInspect || pixelOnCount < PIXEL_MIN) {
						int startX = x - 1, startY = y - 1, endX = x + 1, endY = y + 1;
						for (int i = startY; i <= endY; ++i) {
							for (int j = startX; j <= endX; ++j) {
								toInspect.add(new Point(j, i));
							}
						}
					}
				}
			}
		}
	}
	
	private Point findFirstPixelOn(BufferedImage img, Set<Point> inspected, 
			int minX, int minY, int maxX, int maxY, int tolerance) {
		int localMaxX = minX;
		int localMaxY = minY;
		final int yStep = 5;
		while (localMaxX <= maxX || localMaxY <= maxY) {
			if (localMaxY <= maxY) {
				int currentMaxX = Math.min(maxX, localMaxX);
				for (int i = minX; i <= currentMaxX; ++i) {
					int tempMaxY = Math.min(localMaxY + yStep, maxY);
					for (int j = localMaxY; j <= tempMaxY; ++j) {
						if (Simplifier.get().isPixelOn(img.getRGB(i, j), tolerance)) {
							return new Point(i, localMaxY);
						} else {
							inspected.add(new Point(i, j));
						}
					}
				}
			}
			if (localMaxX <= maxX) {
				int currentMaxY = Math.min(maxY, localMaxY);
				for (int i = minY; i <= currentMaxY; ++i) {
					if (Simplifier.get().isPixelOn(img.getRGB(localMaxX, i), tolerance)) {
						return new Point(localMaxX, i);
					} else {
						inspected.add(new Point(localMaxX, i));
					}
				}
			}
			++localMaxX;
			localMaxY += yStep;
		}
		return null;
	}
}
