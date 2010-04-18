package nsp.server.recognition.builders.comparers;

import java.awt.image.BufferedImage;

import nsp.server.recognition.builders.results.Result;

public interface IComparer {

	Result compare(BufferedImage shape);
}
