package nsp.server.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.FindShapeOptions;
import nsp.client.widgets.tools.options.ToShapeOptions;
import nsp.server.Utils;
import nsp.server.core.config.Config;
import nsp.server.image.Cropper;
import nsp.server.image.Fusion;
import nsp.server.recognition.BoundedImages;
import nsp.server.recognition.CharacterMatching;
import nsp.server.recognition.ShapeFinder;
import nsp.server.recognition.ShapeMatching;
import nsp.server.recognition.builders.Result;

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
		String srcPath = getImagePath(srcLayerId);
		List<BufferedImage> images = new Cropper(Utils.get().loadImage(srcPath)).
			divideImage(left, top, right, bottom, Cropper.Shape.Rectangle);
		Utils.get().saveImage(images.get(0), croppedImage);
		Utils.get().saveImage(images.get(1), srcPath);
		return croppedImage;
	}
	
	private String toShape(ShapeMatching matcher, String srcLayerId, String dstLayerId, int left, int top, int right, 
			int bottom, ToShapeOptions options) throws Exception {
		int width = right - left;
		int height = bottom - top;
		String croppedImage = getImagePathFile(dstLayerId).getAbsolutePath();
		BufferedImage image = new Cropper(Utils.get().loadImage(getImagePath(srcLayerId))).
			cropImage(left, top, right, bottom, Cropper.Shape.Rectangle);
		
		Result result = matcher.getShape(
				ShapeMatching.get().simplify(image, options.getColorTolerance(), 
						options.getPixelOnPercentage()), right - left, bottom - top);
		if (result != null) {
			Utils.get().saveImage(result.toImage(width, height), croppedImage);
		}
		return croppedImage;
	}
	
	@Override
	public String toShape(String srcLayerId, String dstLayerId, int left, int top, int right, 
			int bottom, ToShapeOptions options) throws Exception {
		return toShape(ShapeMatching.get(), srcLayerId, dstLayerId, left, top, right, 
				bottom, options);
	}
	
	@Override
	public String toChar(String srcLayerId, String dstLayerId, int left, int top, int right,
			int bottom, ToShapeOptions options) throws Exception {
		return toShape(CharacterMatching.get(), srcLayerId, dstLayerId, left, top, right, 
				bottom, options);
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

	@Override
	public Rectangle findShape(String layerId, int left, int top, int right,
			int bottom) throws Exception {
		return ShapeFinder.get().find(Utils.get().loadImage(getImagePath(layerId)), left,
				top, right, bottom, 140);
	}

	@Override
	public Rectangle[] findAllShape(String layerId, int left, int top,
			int right, int bottom, FindShapeOptions options) throws Exception {
		return ShapeFinder.get().findAll(Utils.get().loadImage(getImagePath(layerId)), 
				left, top, right, bottom, options.getColorTolerance());
	}

	@Override
	public Rectangle[] toText(String srcLayerId, String dstLayerId, int left,
			int top, int right, int bottom, FindShapeOptions options) throws Exception {
		int layerId = Integer.parseInt(dstLayerId);
		BoundedImages images =  CharacterMatching.get().getAllShapes(
				Utils.get().loadImage(getImagePath(srcLayerId)), 
				left, top, right, bottom, options.getColorTolerance());
		int i = 0;
		for (BufferedImage image : images.get()) {
			String path = getImagePathFile(String.valueOf(layerId++)).getAbsolutePath();
			images.getRecs()[i++].setUrl(path);
			Utils.get().saveImage(image, path);
		}
		return images.getRecs();
	}
	
	private File getImagePathFile(String layerId) {
		return new File(getImagesPath(), layerId);
	}

	private File getImagesPath() {
		return new File(new File(Config.get().getImagesPath()), _id);
	}
}
