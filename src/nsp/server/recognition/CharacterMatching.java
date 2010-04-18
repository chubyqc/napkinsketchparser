package nsp.server.recognition;

import java.awt.image.BufferedImage;
import java.io.File;

import nsp.client.widgets.tools.options.ToShapeOptions;
import nsp.server.recognition.builders.results.CharacterResult;
import nsp.server.recognition.builders.results.Result;

public class CharacterMatching extends ShapeMatching {
	private static CharacterMatching _instance = new CharacterMatching("characters");
	public static CharacterMatching get() {
		return _instance;
	}
	
	private static final String DOT = "\\.";
	
	private CharacterMatching(String basePath) {
		loadCharacters(basePath);
	}

	private void loadCharacters(String basePath) {
		File characters = new File(basePath);
		for (File character : characters.listFiles()) {
			addShape(new nsp.server.recognition.builders.Character(
					character.getAbsolutePath(), getChar(character)));
		}
	}
	
	private String getChar(File file) {
		return file.getName().split(DOT)[0];
	}

	public String getText(BufferedImage img, int left, int top,
			int right, int bottom, ToShapeOptions options) throws Exception {
		BoundedResults boundedResults = getAllShapes(img, left, top, right, bottom, options);
		Result[] results = boundedResults.get();
		StringBuilder builder = new StringBuilder();
		for (Result result : results) {
			builder.append(((CharacterResult)result).getCharacter());
		}
		return builder.toString();
	}
}
