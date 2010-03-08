package nsp.server.recognition.builders;

import java.awt.Shape;

import nsp.server.image.Comparer;

public class Result {
	
	private double _score;
	private IShapeBuilder _builder;
	
	Result(IShapeBuilder builder, double score) {
		_builder = builder;
		_score = score;
	}
	
	public double getScore() {
		return _score;
	}
	
	public Shape build(int width, int height) {
		System.out.println(_score);
		return (Comparer.get().isAlike(_score)) ? _builder.build(this, width, height)
				: null;
	}
}
