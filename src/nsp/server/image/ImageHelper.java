package image;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageHelper {

	public static BufferedImage loadImage(String sPathFile)
	{
		BufferedImage image = null;
		try 
		{
			// Read from a file
		    File sourceimage = new File(sPathFile);
		    image = ImageIO.read(sourceimage);

		} catch (IOException e) 
		{
		}
		    
	
		return image;
	}
	
	public static BufferedImage loadImage(InputStream isImage)
	{
		BufferedImage image = null;
		try
		{
			// Read from an input stream
	        InputStream is = new BufferedInputStream(isImage);
	        image = ImageIO.read(is);
	        
		}catch (Exception e)
		{
		}
		
		return image;
	}
	
	
	public static BufferedImage loadImage(URL sURL)
	{
		BufferedImage image = null;
		try
		{
			// Read from a URL
	        image = ImageIO.read(sURL);	
	        
		}catch (Exception e)
		{
		}
		
		return image;
	}
	
	
	public static void saveImage(BufferedImage image, String sFile)
	{
		try
		{
			File outputFile = new File(sFile);
			ImageIO.write(image, "png", outputFile);
		}catch(IOException e){
		}
		
	}
	
	public static void saveImage(List<BufferedImage> images, String sFilePattern) throws Exception
	{
		if (!sFilePattern.contains("*"))
		{
			throw new Exception("Put a fucking * in the sFilePattern you fucking moron...sorry I guess I've just... overreacted...");
		}
		else
		{
			int i = 0;
			for(BufferedImage image : images)
			{
				String sFile = sFilePattern.replace("*", ("" + (i++))); 
				saveImage(image, sFile);
			}
		}
	}
	


	
	
}
