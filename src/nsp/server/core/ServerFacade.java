package nsp.server.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import nsp.client.widgets.tools.options.ToShapeOptions;
import nsp.server.Utils;
import nsp.server.core.config.Config;
import nsp.server.image.Cropper;
import nsp.server.image.Fusion;
import nsp.server.recognition.ShapeMatching;

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
		
			Utils.get().saveImage(dest, image);
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
		Utils.get().saveImage(
				new Cropper(Utils.get().loadImage(getImagePath(srcLayerId))).
					cropImage(left, top, right, bottom, Cropper.Shape.Rectangle),
					croppedImage);
		return croppedImage;
	}
	
	@Override
	public String cutImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws Exception {
		String croppedImage = getImagePathFile(dstLayerId).getAbsolutePath();
		BufferedImage image = new Cropper(Utils.get().loadImage(getImagePath(srcLayerId))).
			cropImage(left, top, right, bottom, Cropper.Shape.Rectangle);
		Utils.get().saveImage(image, croppedImage);
		return croppedImage;
	}
	
	@Override
	public String toShape(String srcLayerId, String dstLayerId, int left, int top, int right, 
			int bottom, ToShapeOptions options) throws Exception {
		String croppedImage = getImagePathFile(dstLayerId).getAbsolutePath();
		List<BufferedImage> images = new Cropper(Utils.get().loadImage(getImagePath(srcLayerId))).
			divideImage(left, top, right, bottom, Cropper.Shape.Rectangle);
		Utils.get().saveImage(images.get(1), getImagePath(srcLayerId));
		
		Utils.get().save(ShapeMatching.get().getShape(
				ShapeMatching.get().simplify(images.get(0), options.getColorTolerance()), right - left, bottom - top),
				croppedImage);
		
		return croppedImage;
	}
	
	@Override
	public void mergeImage(String[] layerIds, int[] lefts, int[] tops,
			int[] rights, int[] bottoms) throws Exception {
		int left = Integer.MAX_VALUE;
		int top = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int bottom = Integer.MIN_VALUE;
		for (int i = 0; i < layerIds.length; ++i) {
			left = Math.max(0, Math.min(left, lefts[i]));
			top = Math.max(0, Math.min(top, tops[i]));
			right = Math.min(Config.get().getCanvasWidth(), Math.max(right, rights[i]));
			bottom = Math.min(Config.get().getCanvasHeight(), Math.max(bottom, bottoms[i]));
		}
		Fusion fusion = new Fusion(right - left, bottom - top);
		for (int i = 0; i < layerIds.length; ++i) {
			fusion.fusionImage(Utils.get().loadImage(getImagePath(layerIds[i])), 
					lefts[i] - left, tops[i] - top);
		}
		if (layerIds.length > 0) {
			Utils.get().saveImage(fusion.getImage(), getImagePath(layerIds[0]));
		}
	}
	
	private File getImagePathFile(String layerId) {
		return new File(getImagesPath(), layerId);
	}

	private File getImagesPath() {
		return new File(new File(Config.get().getImagesPath()), _id);
	}
}
