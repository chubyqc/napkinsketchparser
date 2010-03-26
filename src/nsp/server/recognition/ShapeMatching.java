package nsp.server.recognition;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;

import nsp.server.Utils;
import nsp.server.image.Simplifier;
import nsp.server.recognition.builders.Circle;
import nsp.server.recognition.builders.Connector;
import nsp.server.recognition.builders.IShapeBuilder;
import nsp.server.recognition.builders.Line;
import nsp.server.recognition.builders.Rectangle;
import nsp.server.recognition.builders.Result;

public class ShapeMatching {
	private static ShapeMatching _instance = new ShapeMatching();
	public static ShapeMatching get() {
		return _instance;
	}
	
	private static final int SIMPLE_WIDTH = 10;
	private static final int SIMPLE_HEIGHT = 10;
	
	private Collection<IShapeBuilder> _shapes;
	
	private ShapeMatching() {
		_shapes = new LinkedList<IShapeBuilder>();
		_shapes.add(new Rectangle());
		_shapes.add(new Circle());
		_shapes.add(new Line());
		_shapes.add(new Connector());
	}
	
	public Result getShape(BufferedImage img, int dstWidth, int dstHeight) {
System.err.println("getShape()");
		Result bestResult = null;
		for (IShapeBuilder shape : _shapes) {
			Result result = shape.getComparer().compare(img);
			System.err.println(result.getScore());
			if (bestResult == null || result.getScore() > bestResult.getScore()) {
				bestResult = result;
			}
		}
		if (bestResult != null) {
			bestResult.build(dstWidth, dstHeight);
		}
		return bestResult;
	}
	
	public BufferedImage simplify(BufferedImage img, int tolerance, double pixelOnTolerance) {
		return Simplifier.get().simplify(img, tolerance, pixelOnTolerance, SIMPLE_WIDTH, SIMPLE_HEIGHT);
	}
	
	public static void main(String[] args) {
		BufferedImage rectangle = Utils.get().loadImage("/home/chubyqc/handDrawnRectangle.png");
		System.out.println(get().getShape(rectangle, 10, 10));
		BufferedImage circle = Utils.get().loadImage("/home/chubyqc/handDrawnCircle.png");
		System.out.println(get().getShape(circle, 10, 10));
		BufferedImage line = Utils.get().loadImage("/home/chubyqc/handDrawnLine.png");
		get().getShape(line, 10, 10);
	}
}
