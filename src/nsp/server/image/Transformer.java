package nsp.server.image;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public abstract class Transformer {
	
	private BufferedImage m_image;
	
	public Transformer(BufferedImage imageReference)
	{
		m_image = imageReference;
	}
	
	protected void finalize() throws Exception
	{
		m_image=null;
	}

	
	protected BufferedImage copyImage()
	{
		return new BufferedImage(m_image.getColorModel(), (WritableRaster) m_image.getData(), false, null);
	}
	
	public BufferedImage getImage()
	{
		return m_image;
	}

}
