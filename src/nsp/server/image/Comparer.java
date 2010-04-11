package nsp.server.image;

import java.awt.image.BufferedImage;

public class Comparer {
	private static Comparer _instance = new Comparer();
	public static Comparer get() {
		return _instance;
	}
	
	private static final double ALIKE_PERCENTAGE = .4;
	
	private Comparer() {}
	
	public double compare(BufferedImage firstImg, BufferedImage secondImg) {
		int width = firstImg.getWidth();
		int height = firstImg.getHeight();
		int[] rowFromFirst = new int[width];
		int[] rowFromSecond = new int[width];
		int m11 = 0, m01 = 0, m10 = 0;
		for (int i = 0; i < height; ++i) {
			firstImg.getRGB(0, i, width, 1, rowFromFirst, 0, width);
			secondImg.getRGB(0, i, width, 1, rowFromSecond, 0, width);
			for (int j = 0; j < width; ++j) {
				boolean firstOn = Simplifier.get().isPixelOn(rowFromFirst[j]);
				boolean secondOn = Simplifier.get().isPixelOn(rowFromSecond[j]);
				if (firstOn && secondOn) {
					++m11;
				} else if (firstOn && !secondOn) {
					++m10;
				} else if (!firstOn && secondOn) {
					++m01;
				}
			}
		}
		return m11 / (m01 + m10 + m11);
	}
	
	public boolean isAlike(double score) {
		return score >= ALIKE_PERCENTAGE;
	}
	
	public boolean isAlike(BufferedImage firstImg, BufferedImage secondImg) {
		return isAlike(compare(firstImg, secondImg));
	}
}
