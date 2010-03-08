package nsp.server.recognition.builders;

import java.awt.Shape;

public interface IShapeBuilder {

	Shape build(Result result, int width, int height);
	
	IComparer getComparer();
}
