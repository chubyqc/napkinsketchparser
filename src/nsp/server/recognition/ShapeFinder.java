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
	
	private static final int TOLERANCE = 254;
	
	private ShapeFinder() {}
	
	public Rectangle find(BufferedImage img, int minX, int minY, int maxX, int maxY) {
		Set<Point> inspected = new HashSet<Point>();
		Queue<Point> toInspect = new LinkedList<Point>();
		Rectangle result = new Rectangle(minX, minY, 0, 0);
		Point first = findFirstPixelOn(img, inspected, minX, minY, maxX, maxY);
		if (first != null) {
			result.reset(first.getX(), first.getY());
			toInspect.add(first);
			inspect(img, inspected, toInspect, result, minX, minY, maxX, maxY);
		}
		result.computeSize();
		return result;
	}

	public Rectangle[] findAll(BufferedImage img, int minX, int minY,
			int maxX, int maxY) {
		List<Rectangle> rectangles = new LinkedList<Rectangle>();
		int toExploreMinX = minX;
		int toExploreMinY = minY;
		int toExploreMaxY = maxY;
		Rectangle rectangle = null;
		boolean doNextLine = true;
		do {
			rectangle = find(img, toExploreMinX, toExploreMinY, maxX, toExploreMaxY);
			if (rectangle.isValid()) {
				rectangles.add(rectangle);
				if (rectangle.getMaxX() != maxX) {
					doNextLine = false;
					toExploreMinX = rectangle.getMaxX() + 1;
					toExploreMaxY = rectangle.getMaxY();
				}
			} 
			if (doNextLine) {
				toExploreMinX = minX;
				toExploreMinY = toExploreMaxY + 1;
				toExploreMaxY = maxY;
			}
			doNextLine = true;
		} while (toExploreMinY < toExploreMaxY);
		return rectangles.toArray(new Rectangle[rectangles.size()]);
	}

	private void inspect(BufferedImage img, Set<Point> inspected, Queue<Point> toInspect,
			Rectangle result, int minX, int minY, int maxX, int maxY) {
		Point pixel;
		while ((pixel = toInspect.poll()) != null) {
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
								toInspect.add(new Point(j, i));
							}
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
