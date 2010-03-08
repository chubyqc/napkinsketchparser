package nsp.server.recognition.builders;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class LineResult extends Result {

	private double _x0;
	private double _y0;
	private double _x1;
	private double _y1;

	LineResult(IShapeBuilder builder, double score,
			double x0, double y0, double x1, double y1) {
		super(builder, score);
		_x0 = x0;
		_y0 = y0;
		_x1 = x1;
		_y1 = y1;
	}
	
	Shape toLine(int width, int height) {
		return new Line2D.Double(new Point2D.Double(_x0, _y0), 
				new Point2D.Double(_x1, _y1));
	}
}
