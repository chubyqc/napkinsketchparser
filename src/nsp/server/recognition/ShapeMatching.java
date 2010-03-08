package nsp.server.recognition;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;

import nsp.server.image.Utils;
import nsp.server.recognition.builders.Circle;
import nsp.server.recognition.builders.IShapeBuilder;
import nsp.server.recognition.builders.Line;
import nsp.server.recognition.builders.Rectangle;
import nsp.server.recognition.builders.Result;

public class ShapeMatching {
	private static ShapeMatching _instance = new ShapeMatching();
	public static ShapeMatching get() {
		return _instance;
	}
	
	private Collection<IShapeBuilder> _shapes;
	
	private ShapeMatching() {
		_shapes = new LinkedList<IShapeBuilder>();
		_shapes.add(new Rectangle());
		_shapes.add(new Circle());
		_shapes.add(new Line());
	}
	
	public Shape getShape(BufferedImage img, int dstWidth, int dstHeight) {
		Result bestResult = null;
		for (IShapeBuilder shape : _shapes) {
			Result result = shape.getComparer().compare(img);
			if (bestResult == null || result.getScore() > bestResult.getScore()) {
				bestResult = result;
			}
		}
		return (bestResult != null) ? bestResult.build(dstWidth, dstHeight) : null;
	}
	
	public static void main(String[] args) {
		BufferedImage rectangle = Utils.get().loadImage("/home/chubyqc/handDrawnRectangle.png");
		System.out.println(get().getShape(rectangle, 10, 10));
		BufferedImage circle = Utils.get().loadImage("/home/chubyqc/handDrawnCircle.png");
		System.out.println(get().getShape(circle, 10, 10));
		BufferedImage line = Utils.get().loadImage("/home/chubyqc/handDrawnLine.png");
		Line2D lineShape = (Line2D)get().getShape(line, 10, 10);
		System.out.print(lineShape);
		if (lineShape != null) {
			System.out.println(lineShape.getP1() + " : " + lineShape.getP2());
		}
	}
}
