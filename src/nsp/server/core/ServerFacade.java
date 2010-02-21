package nsp.server.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;

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
		try {
			File dest = getImagePathFile(layerId);
			dest.createNewFile();
		
			ImageHelper.saveImage(dest, image);
		} catch (Exception e) {
			e.printStackTrace();
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
	
	@Override
	public String cutImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws Exception {
		String croppedImage = getImagePathFile(dstLayerId).getAbsolutePath();
		List<BufferedImage> images = new Cropper(ImageHelper.loadImage(getImagePath(srcLayerId))).
			divideImage(left, top, right, bottom, Cropper.Shape.Rectangle);
		ImageHelper.saveImage(images.get(0), croppedImage);
		ImageHelper.saveImage(images.get(1), getImagePath(srcLayerId));
		return croppedImage;
	}
	
	private File getImagePathFile(String layerId) {
		return new File(getImagesPath(), layerId);
	}

	private File getImagesPath() {
		return new File(new File(Config.getInstance().getImagesPath()), _id);
	}
}
