package nsp.server;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

public class Utils {
	private static Utils _instance = new Utils();
	public static Utils get() {
		return _instance;
	}
	
	private String _appPath;
	
	private Utils() {}
	
	public void setAppPath(String absolutePath) {
		_appPath = absolutePath;
	}
	
	public String toRealPath(String relativePath) {
		return _appPath + relativePath;
	}
	
	public BufferedImage newImage(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public BufferedImage loadImage(String sPathFile)
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
	
	public BufferedImage loadImage(InputStream isImage)
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
	
	
	public BufferedImage loadImage(URL sURL)
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
	
	public void saveImage(File dst, InputStream inputImg) throws IOException {
		FileOutputStream destination = null;
		
		try {
			destination = new FileOutputStream(dst);
        	
        	byte[] buffer = new byte[8192];
        	int read;
        	while ((read = inputImg.read(buffer)) > 0) {
        		destination.write(buffer, 0, read);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(destination != null) {
				try {
					destination.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void saveImage(BufferedImage image, String sFile)
	{
		try
		{
			File outputFile = new File(sFile);
			ImageIO.write(image, "png", outputFile);
		}catch(IOException e){
		}
	}
	
	public void saveImage(List<BufferedImage> images, String sFilePattern) throws Exception
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
