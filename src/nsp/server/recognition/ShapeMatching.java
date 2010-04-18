package nsp.server.recognition;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;

import nsp.client.widgets.tools.options.ToShapeOptions;
import nsp.server.Utils;
import nsp.server.image.Cropper;
import nsp.server.image.Simplifier;
import nsp.server.recognition.builders.Circle;
import nsp.server.recognition.builders.Connector;
import nsp.server.recognition.builders.IShapeBuilder;
import nsp.server.recognition.builders.Line;
import nsp.server.recognition.builders.Rectangle;
import nsp.server.recognition.builders.results.Result;

public class ShapeMatching {
	private static ShapeMatching _instance = new ShapeMatching(true);
	public static ShapeMatching get() {
		return _instance;
	}
	
	private static final int SIMPLE_WIDTH = 10;
	private static final int SIMPLE_HEIGHT = 10;
	
	private Collection<IShapeBuilder> _shapes;
	
	private ShapeMatching(boolean doInit) {
		_shapes = new LinkedList<IShapeBuilder>();
		if (doInit) {
			addShape(new Rectangle());
			addShape(new Circle());
			addShape(new Line());
			addShape(new Connector());
		}
	}
	
	protected ShapeMatching() {
		this(false);
		_shapes = new LinkedList<IShapeBuilder>();
	}
	
	protected void addShape(IShapeBuilder builder) {
		_shapes.add(builder);
	}
	
	public Result getShape(BufferedImage img, int dstWidth, int dstHeight) {
		Result bestResult = null;
		for (IShapeBuilder shape : _shapes) {
			Result result = shape.getComparer().compare(img);
			if (bestResult == null || result.getScore() > bestResult.getScore()) {
				bestResult = result;
			}
		}
		if (bestResult != null) {
			bestResult.build(dstWidth, dstHeight);
		}
		return bestResult;
	}

	public BoundedResults getAllShapes(BufferedImage img, int left, int top,
			int right, int bottom, ToShapeOptions options) throws Exception {
		nsp.client.geom.Rectangle[] shapes = ShapeFinder.get().findAll(img, left, top, 
				right, bottom, options.getColorTolerance());
		Result[] results = new Result[shapes.length]; 
		Cropper cropper = new Cropper(img);
		int i = -1;
		for (nsp.client.geom.Rectangle shape : shapes) {
			results[++i] = CharacterMatching.get().getShape(
					CharacterMatching.get().simplify(cropper.cropImage(shape.getMinX(), 
							shape.getMinY(), shape.getMaxX(), shape.getMaxY(), 
							Cropper.Shape.Rectangle), options.getColorTolerance(), 
							options.getPixelOnPercentage()), 
							shape.getWidth(), shape.getHeight());
		}
		return new BoundedResults(shapes, results);
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
