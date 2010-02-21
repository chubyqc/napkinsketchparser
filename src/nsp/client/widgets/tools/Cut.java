package nsp.client.widgets.tools;

import com.google.gwt.user.client.rpc.AsyncCallback;

import nsp.client.GWTFacade;
import nsp.client.geom.Rectangle;

public class Cut extends Copy {
	
	private static final String NAME = "Cut";

	public Cut(Move move) {
		super(move);
	}
	
	@Override
	protected String getName() {
		return NAME;
	}
	
	@Override
	protected void done(Rectangle bounds, String result) {
		getCanvas().refresh();
		super.done(bounds, result);
	}
	
	@Override
	protected void execute(String srcLayerId, String dstLayerId, int xMin,
			int yMin, int xMax, int yMax, AsyncCallback<String> callback) {
		GWTFacade.get().cutImage(srcLayerId, dstLayerId, xMin, yMin, xMax, yMax, callback);
	}
}
