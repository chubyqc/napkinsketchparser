package nsp.client.widgets.tools;

import nsp.client.GWTFacade;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ButtonBase;

public class CropTool extends AbstractTool {
	
	private static final String NAME = "Crop";
	
	@Override
	protected String getName() {
		return NAME;
	}
	
	@Override
	protected void clicked(ButtonBase button) {
		final int left = getCanvas().getSelectionLeft();
		final int top = getCanvas().getSelectionTop();
		final int right = getCanvas().getSelectionRight();
		final int bottom = getCanvas().getSelectionBottom();
		GWTFacade.get().cropImage(left, top, right, bottom,
				new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				getCanvas().addImage(left, top, result);
			}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
