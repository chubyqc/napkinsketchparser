package nsp.client.widgets.tools;

import nsp.client.GWTFacade;
import nsp.client.geom.Rectangle;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ButtonBase;

public class Crop extends AbstractTool {
	
	private static final String NAME = "Crop";
	
	@Override
	protected String getName() {
		return NAME;
	}
	
	@Override
	protected void clicked(ButtonBase button) {
		final Rectangle bounds = getCanvas().getSelectionBounds();
		GWTFacade.get().cropImage(
				bounds.getMinX(), 
				bounds.getMinY(), 
				bounds.getMaxX(), 
				bounds.getMaxY(),
				new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				getCanvas().addImage(
						bounds.getMinX(), 
						bounds.getMinY(), result);
			}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
