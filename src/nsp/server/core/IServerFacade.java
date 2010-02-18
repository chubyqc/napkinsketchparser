package nsp.server.core;

import java.io.InputStream;

public interface IServerFacade {

	void addImage(String layerId, InputStream image);
	
	String getImagePath(String layerId) throws NoImageException;
	
	String copyImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws Exception;
}
