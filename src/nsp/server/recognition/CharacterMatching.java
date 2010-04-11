package nsp.server.recognition;

import java.io.File;

import nsp.server.recognition.builders.Image;

public class CharacterMatching extends ShapeMatching {
	private static CharacterMatching _instance = new CharacterMatching("characters");
	public static CharacterMatching get() {
		return _instance;
	}
	
	private CharacterMatching(String basePath) {
		loadCharacters(basePath);
	}

	private void loadCharacters(String basePath) {
		File characters = new File(basePath);
		for (File character : characters.listFiles()) {
			addShape(new Image(character.getAbsolutePath()));
		}
	}
}
