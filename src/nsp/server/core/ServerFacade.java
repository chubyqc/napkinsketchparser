package nsp.server.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nsp.server.core.config.Config;

class ServerFacade implements IServerFacade {
	
	private static final String IMAGE_NAME = "image";
	
	private String _id;
	
	ServerFacade(String id) {
		_id = id;
		getImagesPath().mkdir();
	}

	@Override
	public void addImage(InputStream image) {
		FileOutputStream destination = null;
		
		try {
			File dest = getImagePathFile();
			dest.createNewFile();
		
        	destination = new FileOutputStream(dest);
        	
        	byte[] buffer = new byte[8192];
        	int read;
        	while ((read = image.read(buffer)) > 0) {
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

	@Override
	public String getImagePath() throws NoImageException {
		File file = getImagePathFile();
		if (!file.exists()) {
			throw new NoImageException();
		}
		return file.getAbsolutePath();
	}
	
	private File getImagePathFile() {
		return new File(getImagesPath(), IMAGE_NAME);
	}

	private File getImagesPath() {
		return new File(new File(Config.getInstance().getImagesPath()), _id);
	}
}
