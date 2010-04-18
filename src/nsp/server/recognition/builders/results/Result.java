package nsp.server.recognition.builders.results;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import nsp.server.Utils;
import nsp.server.image.Comparer;
import nsp.server.recognition.builders.IShapeBuilder;

public class Result {
	
	private double _score;
	private Shape _shape;
	private IShapeBuilder _builder;
	
	public Result(IShapeBuilder builder, double score) {
		_builder = builder;
		_score = score;
	}
	
	public double getScore() {
		return _score;
	}
	
	public void build(int width, int height) {
		_shape = (Comparer.get().isAlike(_score)) ? _builder.build(this, width, height)
				: null;
	}
	
	public BufferedImage toImage(int width, int height) {
		BufferedImage img = Utils.get().newImage(width + 1, height + 1);
		if (_shape != null) {
			Graphics2D g = img.createGraphics();
			g.setColor(Color.BLACK);
			g.draw(_shape);
		}
		return img;
	}
}
