package nsp.client.widgets.tools;

import nsp.client.GWTFacade;
import nsp.client.geom.Point;
import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.Options;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ButtonBase;

public class Copy extends AbstractTool {
	
	private static final String NAME = "Copy";
	
	private Move _move;
	
	public Copy(Move move) {
		this(move, null);
	}
	
	protected Copy(Move move, Options options) {
		super(options);
		_move = move;
	}
	
	@Override
	protected String getName() {
		return NAME;
	}
	
	@Override
	protected void clicked(ButtonBase button) {
		withSelectionBounds(new IWithBounds() {
			@Override
			public void execute(final Rectangle bounds) {
				Copy.this.execute(getCanvas().getLayerId(), getCanvas().getNextLayerId(),
						bounds.getMinX(), 
						bounds.getMinY(), 
						bounds.getMaxX(), 
						bounds.getMaxY(),
						new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						done(bounds, result);
					}
					@Override
					public void onFailure(Throwable caught) {}
				});
			}
		});
	}
	
	protected void execute(String srcLayerId, String dstLayerId, int xMin, int yMin, int xMax, int yMax, AsyncCallback<String> callback) {
		GWTFacade.get().copyImage(srcLayerId, dstLayerId, xMin, yMin, xMax, yMax, callback);
	}
	
	protected void done(Rectangle bounds, String result) {
		Point imgPosition = getCanvas().getImagePosition();
		getCanvas().addImage(
				imgPosition.getX() + bounds.getMinX(), 
				imgPosition.getY() + bounds.getMinY(), result);
		_move.toggleDown();
	}
}
