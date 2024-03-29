package nsp.server.recognition.builders.comparers;

import java.awt.image.BufferedImage;

import nsp.server.image.Simplifier;
import nsp.server.recognition.builders.IShapeBuilder;
import nsp.server.recognition.builders.results.LineResult;
import nsp.server.recognition.builders.results.Result;


public class LineComparer implements IComparer {
	
	private IShapeBuilder _builder;

	public LineComparer(IShapeBuilder builder) {
		_builder = builder;
	}

	@Override
	public Result compare(BufferedImage shape) {
		int width = shape.getWidth();
		int height = shape.getHeight();
		
		int[] row = new int[width];
		
		int[] x0y0 = getX0Y0(width, height, row, shape);
		int[] x1y1 = getX1Y1(width, height, row, shape);
		int x0, y0, x1, y1;
		if (x0y0[0] <= x1y1[0]) {
			x0 = x0y0[0]; y0 = x0y0[1]; x1 = x1y1[0]; y1 = x1y1[1];
		} else {
			x1 = x0y0[0]; y1 = x0y0[1]; x0 = x1y1[0]; y0 = x1y1[1];
		}

		int pixelOnCount = 0;
		int iteCount;
		if (y1 - y0 == 0) {
			pixelOnCount = getHorizontalLinePixelOnCount(shape, x0, x1, y0);
			iteCount = x1 - x0;
		} else if (x1 - x0 == 0) {
			pixelOnCount = getVerticalLinePixelOnCount(shape, x0, y0, y1);
			iteCount = Math.abs(y1 - y0);
		} else {
			pixelOnCount = getSlopePixelOnCount(shape, x0, y0, x1, y1);
			iteCount = Math.abs(y1 - y0);
		}
		++iteCount;

		return new LineResult(_builder, (iteCount == 0) ? 0 : (double)pixelOnCount / iteCount,
				width, height, x0, y0, x1, y1);
	}
	
	private int getHorizontalLinePixelOnCount(BufferedImage shape, int x0, int x1, int y0) {
		int pixelOnCount = 0;
		int min = Math.min(x0, x1);
		int max = Math.max(x0, x1);
		for (int i = min; i <= max; ++i) {
			if (Simplifier.get().isPixelOn(shape.getRGB(i, y0))) {
				++pixelOnCount;
			}
		}
		return pixelOnCount;
	}
	
	private int getVerticalLinePixelOnCount(BufferedImage shape, int x0, int y0, int y1) {
		int pixelOnCount = 0;
		int min = Math.min(y0, y1);
		int max = Math.max(y0, y1);
		for (int i = min; i <= max; ++i) {
			if (Simplifier.get().isPixelOn(shape.getRGB(x0, i))) {
				++pixelOnCount;
			}
		}
		return pixelOnCount;
	}
	
	private int getSlopePixelOnCount(BufferedImage shape, int x0, int y0, int x1, int y1) {
		double slope = (double)(y1 - y0) / (x1 - x0);
		int pixelOnCount = 0;
		for (int i = x0; i <= x1; ++i) {
			if (Simplifier.get().isPixelOn(shape.getRGB(
					i, 
					(int)((i - x0) * slope) + y0))) {
				++pixelOnCount;
			}
		}
		return pixelOnCount;
	}
	
	private int[] getX1Y1(int width, int height, int[] row, BufferedImage shape) {
		int x1 = -1, y1 = -1;
		for (int i = height - 1; i >= 0 && x1 == -1; --i) {
			shape.getRGB(0, i, width, 1, row, 0, width);
			for (int j = width - 1; j >= 0 && x1 == -1; --j) {
				if (Simplifier.get().isPixelOn(row[j])) {
					x1 = j;
					y1 = i;
				}
			}
		}
		return new int[] { x1, y1 };
	}

	private int[] getX0Y0(int width, int height, int[] row, BufferedImage shape) {
		int x0 = -1, y0 = -1;
		for (int i = 0; i < height && x0 == -1; ++i) {
			shape.getRGB(0, i, width, 1, row, 0, width);
			for (int j = 0; j < width && x0 == -1; ++j) {
				if (Simplifier.get().isPixelOn(row[j])) {
					x0 = j;
					y0 = i;
				}
			}
		}
		return new int[] { x0, y0 };
	}
}
