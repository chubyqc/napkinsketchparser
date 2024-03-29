package nsp.client.widgets.tools;

import nsp.client.GWTFacade;
import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.ToShapeOptions;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ButtonBase;

public class ToText extends AbstractTool {
	
	private static final String NAME = "To text";
	
	public ToText() {
		super(new ToShapeOptions());
	}

	@Override
	protected void clicked(ButtonBase button) {
		withSelectionBounds(new IWithBounds() {
			@Override
			public void execute(final Rectangle bounds) {
				GWTFacade.get().toText(getCanvas().getLayerId(), getCanvas().getNextLayerId(),
						bounds.getMinX(),
						bounds.getMinY(),
						bounds.getMaxX(),
						bounds.getMaxY(), (ToShapeOptions)getOptions(),
						new AsyncCallback<Rectangle[]>() {
							@Override
							public void onSuccess(Rectangle[] result) {
								getCanvas().addImages(result);
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
