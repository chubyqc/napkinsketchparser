package nsp.server.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Fusion extends Transformer{

	public Fusion(int width, int height)
	{
		super(new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR));
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
