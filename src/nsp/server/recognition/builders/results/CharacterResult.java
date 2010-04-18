package nsp.server.recognition.builders.results;

import java.awt.image.BufferedImage;

public class CharacterResult extends ImageResult {
	
	private String _character;

	public CharacterResult(double score, BufferedImage result, String character) {
		super(score, result);
		_character = character;
	}

	public String getCharacter() {
		return _character;
	}
}
