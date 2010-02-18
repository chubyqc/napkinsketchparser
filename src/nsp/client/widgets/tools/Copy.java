package nsp.client.widgets.tools;

import nsp.client.GWTFacade;
import nsp.client.geom.Point;
import nsp.client.geom.Rectangle;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ButtonBase;

public class Copy extends AbstractTool {
	
	private static final String NAME = "Copy";
	
	private Move _move;
	
	public Copy(Move move) {
		super();
		_move = move;
	}
	
	@Override
	protected String getName() {
		return NAME;
	}
	
	@Override
	protected void clicked(ButtonBase button) {
		final Rectangle bounds = getCanvas().getSelectionBounds();
		GWTFacade.get().copyImage(getCanvas().getLayerId(), getCanvas().getNextLayerId(),
				bounds.getMinX(), 
				bounds.getMinY(), 
				bounds.getMaxX(), 
				bounds.getMaxY(),
				new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Point imgPosition = getCanvas().getImagePosition();
				getCanvas().addImage(
						imgPosition.getX() + bounds.getMinX(), 
						imgPosition.getY() + bounds.getMinY(), result);
				_move.toggleDown();
			}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
