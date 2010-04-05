package nsp.client.widgets.tools;

import nsp.client.GWTFacade;
import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.FindShapeOptions;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ButtonBase;

public class FindAllShapes extends AbstractTool {
	
	private static final String NAME = "Find all shapes";
	
	public FindAllShapes() {
		super(new FindShapeOptions());
	}

	@Override
	protected void clicked(ButtonBase button) {
		withSelectionBounds(new IWithBounds() {
			@Override
			public void execute(final Rectangle bounds) {
				GWTFacade.get().findAllShapes(getCanvas().getLayerId(), 
						bounds.getMinX(),
						bounds.getMinY(),
						bounds.getMaxX(),
						bounds.getMaxY(), (FindShapeOptions)getOptions(),
						new AsyncCallback<Rectangle[]>() {
							@Override
							public void onSuccess(Rectangle[] result) {
								getCanvas().setSelectionBounds(result);
							}
							@Override
							public void onFailure(Throwable caught) {}
						});
			}
		});
	}

	@Override
	protected String getName() {
		return NAME;
	}

}
