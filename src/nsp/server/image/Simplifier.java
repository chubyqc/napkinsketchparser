package nsp.server.image;

import java.awt.image.BufferedImage;

public class Simplifier {
	private static final Simplifier _instance = new Simplifier();
	public static Simplifier get() {
		return _instance;
	}
	
	private static final byte PIXEL_MAXVALUE = 123;
	private static final int PIXEL_ON = 0xff000000;
	private static final int PIXEL_OFF = 0xffffffff;
	private static final double PIXEL_ONPERCENTAGE = .1;
	
	private Simplifier() {}
	
	public BufferedImage simplify(BufferedImage complex, int newWidth, int newHeight,
			double pixelOnPercentage) {
		int pixelWidth = complex.getWidth() / newWidth;
		int pixelHeight = complex.getHeight() / newHeight;
		
		BufferedImage simplified = Utils.get().newImage(newWidth, newHeight);
		int[] buffer = new int[pixelWidth * pixelHeight];
		
		for (int i = 0; i < newHeight; ++i) {
			for (int j = 0; j < newWidth; ++j) {
				complex.getRGB(j * pixelWidth, i * pixelHeight, pixelWidth, 
						pixelHeight, buffer, 0, pixelWidth);
				simplified.setRGB(j, i, getSimplifiedPixel(buffer, pixelOnPercentage));
			}
		}
		
		return simplified;
	}
	
	public BufferedImage simplify(BufferedImage complex, int newWidth, int newHeight) {
		return simplify(complex, newWidth, newHeight, PIXEL_ONPERCENTAGE);
	}
	
	private int getSimplifiedPixel(int[] complexPixel, double pixelOnPercentage) {
		int pixelOnCount = 0;
		for (int i = 0; i < complexPixel.length; ++i) {
			int pixel = complexPixel[i];
			if (getRedComponent(pixel) <= PIXEL_MAXVALUE ||
					getGreenComponent(pixel) <= PIXEL_MAXVALUE ||
					getBlueComponent(pixel) <= PIXEL_MAXVALUE) {
				++pixelOnCount;
			}
		}
		return (pixelOnCount > complexPixel.length * pixelOnPercentage) ? PIXEL_ON : PIXEL_OFF;
	}
	
	private int getRedComponent(int pixel) {
		return (pixel & 0x00ff0000) >> 16;
	}
	
	private int getGreenComponent(int pixel) {
		return (pixel & 0x0000ff00) >> 8;
	}
	
	private int getBlueComponent(int pixel) {
		return pixel & 0x000000ff;
	}
	
	public static void main(String[] args) {
		BufferedImage complex = Utils.get().loadImage("/home/chubyqc/complex.png");
		BufferedImage simple = get().simplify(complex, 10, 10);
		Utils.get().saveImage(simple, "/home/chubyqc/simple.png");
	}
}
