package nsp.server.recognition.builders.comparers;

import java.awt.image.BufferedImage;

import nsp.server.recognition.builders.results.CharacterResult;
import nsp.server.recognition.builders.results.Result;

public class CharacterComparer extends ImageComparer {
	
	private String _character;
	
	public CharacterComparer(BufferedImage model, String character) {
		super(model);
		_character = character;
	}
	
	@Override
	public Result compare(BufferedImage shape) {
		return new CharacterResult(nsp.server.image.Comparer.get().compare(shape, _model),
				_model, _character);
	}
}
