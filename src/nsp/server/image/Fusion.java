package nsp.server.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import nsp.server.Utils;

public class Fusion extends Transformer{

	public Fusion(int width, int height)
	{
		super(Utils.get().newImage(width, height));
	}
	
	public Fusion(BufferedImage imageReference)
	{
		super(imageReference);
	}	
	

	public void fusionImage(BufferedImage image, int x, int y)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		
		Graphics2D g2 = getImage().createGraphics();
		g2.setClip(x, y, x+w, y+h);		
		g2.drawImage(image, x, y, w, h, null);
			
	}
	

	
		
}
