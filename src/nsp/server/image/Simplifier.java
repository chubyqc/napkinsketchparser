package nsp.server.image;

import java.awt.image.BufferedImage;

import nsp.server.Utils;

public class Simplifier {
	private static final Simplifier _instance = new Simplifier();
	public static Simplifier get() {
		return _instance;
	}
	
	private static final int PIXEL_ON = 0xff000000;
	private static final int PIXEL_OFF = 0xffffffff;
	
	private Simplifier() {}
	
	private BufferedImage simplify(BufferedImage complex, int tolerance, int newWidth, int newHeight,
			double pixelOnPercentage) {
		int srcWidth = complex.getWidth();
		int srcHeight = complex.getHeight();
		double pixelWidth = Math.max(1, (double)complex.getWidth() / newWidth);
		double pixelHeight = Math.max(1, (double)complex.getHeight() / newHeight);
		
		BufferedImage simplified = Utils.get().newImage(newWidth, newHeight);
		int[] buffer = new int[(int)pixelWidth * (int)pixelHeight];
		
		int srcI, srcJ;
		for (int i = 0; (srcI = (int)(i * pixelHeight)) < srcHeight; ++i) {
			for (int j = 0; (srcJ = (int)(j * pixelWidth)) < srcWidth; ++j) {
				try {
					complex.getRGB(srcJ, srcI, (int)pixelWidth, (int)pixelHeight, buffer, 0, 
							(int)pixelWidth);
					simplified.setRGB(j, i, getSimplifiedPixel(buffer, tolerance, pixelOnPercentage));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return simplified;
	}
	
	public boolean isPixelOn(int pixel) {
		return pixel == PIXEL_ON;
	}
	
	public boolean isPixelOn(int pixel, int tolerance) {
		return getRedComponent(pixel) <= tolerance ||
			getGreenComponent(pixel) <= tolerance ||
			getBlueComponent(pixel) <= tolerance;
	}
	
	public BufferedImage simplify(BufferedImage complex, int tolerance, double pixelOnPercentage, int newWidth, int newHeight) {
		return simplify(complex, tolerance, newWidth, newHeight, pixelOnPercentage);
	}
	
	private int getSimplifiedPixel(int[] complexPixel, int tolerance, double pixelOnPercentage) {
		int pixelOnCount = 0;
		for (int i = 0; i < complexPixel.length; ++i) {
			int pixel = complexPixel[i];
			if (isPixelOn(pixel, tolerance)) {
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
		BufferedImage simple = get().simplify(complex, 225, .1, 10, 10);
		Utils.get().saveImage(simple, "/home/chubyqc/simple.png");
		
		BufferedImage secondSimple = Utils.get().loadImage("/home/chubyqc/secondSimple.png");

		System.out.println(Comparer.get().compare(simple, secondSimple));
		System.out.println(Comparer.get().isAlike(simple, secondSimple));
	}
}
