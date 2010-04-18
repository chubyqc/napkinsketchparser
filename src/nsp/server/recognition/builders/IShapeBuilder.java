package nsp.server.recognition.builders;

import java.awt.Shape;

import nsp.server.recognition.builders.comparers.IComparer;
import nsp.server.recognition.builders.results.Result;

public interface IShapeBuilder {

	Shape build(Result result, int width, int height);
	
	IComparer getComparer();
}
