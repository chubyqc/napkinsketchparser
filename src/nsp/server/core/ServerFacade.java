package nsp.server.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nsp.server.core.config.Config;
import nsp.server.image.Cropper;
import nsp.server.image.ImageHelper;

class ServerFacade implements IServerFacade {
	
	private String _id;
	
	ServerFacade(String id) {
		_id = id;
		getImagesPath().mkdir();
	}

	@Override
	public void addImage(String layerId, InputStream image) {
		FileOutputStream destination = null;
		
		try {
			File dest = getImagePathFile(layerId);
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
	public String getImagePath(String layerId) throws NoImageException {
		File file = getImagePathFile(layerId);
		if (!file.exists()) {
			throw new NoImageException();
		}
		return file.getAbsolutePath();
	}
	
	@Override
	public String copyImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws Exception {
		String croppedImage = getImagePathFile(dstLayerId).getAbsolutePath();
		ImageHelper.saveImage(
				new Cropper(ImageHelper.loadImage(getImagePath(srcLayerId))).
					cropImage(left, top, right, bottom, Cropper.Shape.Rectangle),
					croppedImage);
		return croppedImage;
	}
	
	private File getImagePathFile(String layerId) {
		return new File(getImagesPath(), layerId);
	}

	private File getImagesPath() {
		return new File(new File(Config.getInstance().getImagesPath()), _id);
	}
}
