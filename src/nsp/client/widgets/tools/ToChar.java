package nsp.client.widgets.tools;

import nsp.client.GWTFacade;
import nsp.client.widgets.tools.options.ToShapeOptions;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ToChar extends Copy {

	private static final String NAME = "To char";
	
	public ToChar(Move move) {
		super(move, new ToShapeOptions());
	}

	@Override
	protected String getName() {
		return NAME;
	}
	
	@Override
	protected void execute(String srcLayerId, String dstLayerId, int xMin,
			int yMin, int xMax, int yMax, AsyncCallback<String> callback) {
		GWTFacade.get().toChar(srcLayerId, dstLayerId, xMin, yMin, xMax, yMax, 
				(ToShapeOptions)getOptions(), callback);
	}

}
