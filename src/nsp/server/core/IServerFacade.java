package nsp.server.core;

import java.io.InputStream;

public interface IServerFacade {

	void addImage(InputStream image);
	
	String getImagePath() throws NoImageException;
}
