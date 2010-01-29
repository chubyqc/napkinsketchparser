package nsp.server.core;

import java.io.InputStream;

public interface IServerFacade {

	void addImage(InputStream image);
	
	String getImagePath() throws NoImageException;
	
	String cropImage(int left, int top, int right, int bottom) throws NoImageException;
}
