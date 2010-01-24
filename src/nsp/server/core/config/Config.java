package nsp.server.core.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Config _instance = new Config("config.properties");
	public static Config getInstance() {
		return _instance;
	}
	
	private static final String IMAGES_PATH = "nsp.images.path";
	
	private String imagesPath;
	
	private Config(String ressourceName) {
		Properties props = new Properties();
		try {
			props.load(getClass().getResourceAsStream(ressourceName));
			
			imagesPath = props.getProperty(IMAGES_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getImagesPath() {
		return imagesPath;
	}
}
