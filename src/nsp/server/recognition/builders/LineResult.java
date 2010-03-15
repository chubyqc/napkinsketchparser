package nsp.server.recognition.builders;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class LineResult extends Result {

	private int _orgWidth;
	private int _orgHeight;
	private int _x0;
	private int _y0;
	private int _x1;
	private int _y1;

	LineResult(IShapeBuilder builder, double score,
			int orgWidth, int orgHeight, 
			int x0, int y0, int x1, int y1) {
		super(builder, score);
		_orgWidth = orgWidth;
		_orgHeight = orgHeight;
		_x0 = x0;
		_y0 = y0;
		_x1 = x1;
		_y1 = y1;
	}
	
	Shape toLine(int width, int height) {
		double xRatio = (double)width / _orgWidth;
		double yRatio = (double)height / _orgHeight;
		return new Line2D.Double(new Point2D.Double(_x0 * xRatio, _y0 * yRatio), 
				new Point2D.Double(_x1 * xRatio, _y1 * yRatio));
	}
}
