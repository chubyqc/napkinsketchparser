package nsp.server.core.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Config _instance = new Config("config.properties");
	public static Config get() {
		return _instance;
	}
	
	private static final String IMAGES_PATH = "nsp.images.path";
	private static final String CANVAS_WIDTH = "nsp.canvas.width";
	private static final String CANVAS_HEIGHT = "nsp.canvas.height";
	
	private String _imagesPath;
	private int _canvasWidth;
	private int _canvasHeight;
	
	private Config(String ressourceName) {
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream(ressourceName));
			
			_imagesPath = props.getProperty(IMAGES_PATH);
			_canvasWidth = Integer.parseInt(props.getProperty(CANVAS_WIDTH));
			_canvasHeight = Integer.parseInt(props.getProperty(CANVAS_HEIGHT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getImagesPath() {
		return _imagesPath;
	}
	
	public int getCanvasWidth() {
		return _canvasWidth;
	}
	
	public int getCanvasHeight() {
		return _canvasHeight;
	}
}
