package nsp.server.recognition;

import java.awt.image.BufferedImage;
import java.io.File;

import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.ToShapeOptions;
import nsp.server.recognition.builders.results.CharacterResult;
import nsp.server.recognition.builders.results.Result;

public class CharacterMatching extends ShapeMatching {
	private static CharacterMatching _instance = new CharacterMatching("characters");
	public static CharacterMatching get() {
		return _instance;
	}
	
	private static final String DOT = "\\.";
	private static final String SPACE = " ";
	private static final char NEWLINE = '\n';
	
	private static final int SPACE_WIDTH = 10;
	private static final int NL_HEIGHT = 10;
	
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
		if (results.length > 0) {
			int i = -1, previousX = -1, previousY = -1, initX = boundedResults.getRecs()[0].getMinX();
			for (Result result : results) {
				Rectangle rec = boundedResults.getRecs()[++i];
				
				if (previousX > -1) {
					if (rec.getMinX() > previousX) {
						for (int j = previousX + SPACE_WIDTH; j < rec.getMinX(); j += SPACE_WIDTH) {
							builder.append(SPACE);
						}
					} else {
						builder.append(NEWLINE);
						for (int j = previousY + NL_HEIGHT; j < rec.getMinY(); j += NL_HEIGHT) {
							builder.append(NEWLINE);
						}
						for (int j = initX + SPACE_WIDTH; j < rec.getMinX(); j += SPACE_WIDTH) {
							builder.append(SPACE);
						}
					}
				}
				builder.append(((CharacterResult)result).getCharacter());
				
				previousX = rec.getMaxX();
				previousY = rec.getMaxY();
			}
		}
		return builder.toString();
	}
}
