package nsp.server.image;

import nsp.server.image.Cropper.Line;
import nsp.server.image.Cropper.Shape;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.List;

public class Main {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		BufferedImage image = ImageHelper.loadImage(new FileInputStream("C:\\Images\\test.png"));
					
		Cropper cropper = new Cropper(image);
		Fusion fusionner1 = new Fusion(image);
		Fusion fusionner2 = new Fusion(1024, 800);
		
		int left  = 50;
		int top   = 50;
		int right = 100;
		int down  = 200;
		BufferedImage imageRect = cropper.cropImage(left, top, right, down, Shape.Rectangle);
		BufferedImage imageCircle = cropper.cropImage(left, top, right, down, Shape.Ellipse);
		
		
		int center = 200;
		List<BufferedImage> listImagesVer = cropper.divideImage(center, Line.Vertical);
		List<BufferedImage> listImagesHor = cropper.divideImage(center, Line.Horizontal);
		List<BufferedImage> listImagesRect = cropper.divideImage(left, top, right, down, Shape.Rectangle);
		List<BufferedImage> listImagesCirc = cropper.divideImage(left, top, right, down, Shape.Ellipse);
	
		
		fusionner1.fusionImage(imageCircle, 0, 50);
		fusionner1.fusionImage(imageCircle, 100, 0);
		fusionner1.fusionImage(imageCircle, 300, 100);
		fusionner1.fusionImage(imageCircle, 200, 50);
		fusionner1.fusionImage(imageCircle, 100, 100);
		
		fusionner2.fusionImage(imageCircle, 0, 50);
		fusionner2.fusionImage(imageCircle, 500, 50);
		fusionner2.fusionImage(imageCircle, 200, 50);
		fusionner2.fusionImage(imageCircle, 100, 150);
		fusionner2.fusionImage(imageCircle, 500, 500);
					

		ImageHelper.saveImage(imageRect, "C:\\Images\\test_crop_00_rect.png");
		ImageHelper.saveImage(imageCircle, "C:\\Images\\test_crop_01_circle.png");
		ImageHelper.saveImage(listImagesVer, "C:\\Images\\test_crop_02_vert*.png");
		ImageHelper.saveImage(listImagesHor, "C:\\Images\\test_crop_03_hor*.png");
		ImageHelper.saveImage(listImagesRect, "C:\\Images\\test_crop_04_rect*.png");
		ImageHelper.saveImage(listImagesCirc, "C:\\Images\\test_crop_05_circ*.png");
		
		ImageHelper.saveImage(fusionner1.getImage(), "C:\\Images\\test_fusi_00.png");
		ImageHelper.saveImage(fusionner2.getImage(), "C:\\Images\\test_fusi_01.png");
					

	}
}
