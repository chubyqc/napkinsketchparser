package nsp.server.recognition.builders;

import java.awt.image.BufferedImage;

public interface IComparer {

	Result compare(BufferedImage shape);
}
