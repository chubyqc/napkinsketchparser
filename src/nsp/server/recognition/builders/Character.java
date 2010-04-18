package nsp.server.recognition.builders;


import nsp.server.Utils;
import nsp.server.recognition.builders.comparers.CharacterComparer;

public class Character extends Image {
	
	public Character(String path, String character) {
		super(new CharacterComparer(Utils.get().loadImage(path), character));
	}
}
