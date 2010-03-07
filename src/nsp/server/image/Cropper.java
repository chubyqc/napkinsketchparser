package nsp.server.image;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Cropper extends Transformer{

	public enum Line
	{
		Vertical,
		Horizontal
	}
	
	public enum Shape
	{
		Rectangle,
		Ellipse
	}
	
	public Cropper(BufferedImage imageReference)
	{
		super(imageReference);
	}
	
	public BufferedImage cropImage(int left, int top, int right, int down, Shape eShape) throws Exception
	{
		int w = right-left;
		int h = down-top;
		
		java.awt.Shape shape = null;
		
		switch (eShape)
		{
		case Rectangle:
			{
				shape = new Rectangle2D.Double(0,0,w,h);
				break;
			}	
		case Ellipse:
			{
				shape = new Ellipse2D.Double(0,0,w,h);	
				break;
			}	
		default:
			{
				throw new Exception("This type isn't supported");
			}
		}

		BufferedImage newImage = Utils.get().newImage(w, h);
		Graphics2D graph = newImage.createGraphics();
		graph.setClip(shape);
		graph.drawImage(getImage().getSubimage(left, top, w, h), null, null);
		
		return newImage;
		
	}
	
	private BufferedImage outboundImage(int left, int top, int right, int down, Shape eShape) throws Exception
	{
		int destImageH = down-top;
		int destImageW = right-left;
		
		BufferedImage image = getImage();
		int srcImageH = image.getHeight();
		int srcImageW = image.getWidth();
	
		Area wholeShape = new Area (new Rectangle2D.Double(0,0, srcImageW, srcImageH));
		Area shapeToSubtract = null;
		
		switch (eShape)
		{
		case Rectangle:
			shapeToSubtract = new Area(new Rectangle2D.Double(left,top,destImageW,destImageH));
			break;
			
		case Ellipse:
			shapeToSubtract = new Area(new Ellipse2D.Double(left,top,destImageW,destImageH));
			break;
			
		default:
			throw new Exception("This type isn't supported");
		
		}
		wholeShape.subtract(shapeToSubtract);
	
		BufferedImage newImage = Utils.get().newImage(srcImageW, srcImageH);
		Graphics2D graph = newImage.createGraphics();
		graph.setClip(wholeShape);
		graph.drawImage(image, null, null);
		
		return newImage;
		
	}
	

	//Divide two images from a shape (inbound and outbound the shape)	
	public List<BufferedImage> divideImage(int left, int top, int right, int down, Shape eShape) throws Exception
	{
		ArrayList<BufferedImage> listImages = new ArrayList<BufferedImage>();
		
		listImages.add(cropImage(left, top, right, down, eShape)); 
		listImages.add(outboundImage(left, top, right, down, eShape));
		
		return listImages;
	}
	
	
	
	//Divide two images in half (Vertically or Horizontally)
	public List<BufferedImage> divideImage(int center, Line type) throws Exception
	{
		ArrayList<BufferedImage> listImages = new ArrayList<BufferedImage>();
		
		BufferedImage image = copyImage();
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		switch(type)
		{
		case Vertical:
			//Image Left
			listImages.add(image.getSubimage(0, 0, center, height));
			//Image Right
			listImages.add(image.getSubimage(center, 0, width-center, height));
			break;
			
		case Horizontal:
			//Image Top
			listImages.add(image.getSubimage(0, 0, width, center));
			//Image Down
			listImages.add(image.getSubimage(0, center, width, height-center));
			break;
			
		default:
			throw new Exception("This type isn't supported");
		}
			
		return listImages;
	}
	
	
}
