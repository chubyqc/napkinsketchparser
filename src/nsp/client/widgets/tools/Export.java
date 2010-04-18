package nsp.client.widgets.tools;

import nsp.client.GWTFacade;
import nsp.client.geom.Rectangle;
import nsp.client.widgets.ExportHandle;
import nsp.client.widgets.tools.options.ToShapeOptions;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ButtonBase;

public class Export extends AbstractTool {
	
	private static final String NAME = "Export";
	private ExportHandle _exportHandle;
	
	public Export(ExportHandle exportHandle) {
		super(new ToShapeOptions());
		_exportHandle = exportHandle;
	}

	@Override
	protected void clicked(ButtonBase button) {
		withSelectionBounds(new IWithBounds() {
			@Override
			public void execute(final Rectangle bounds) {
				GWTFacade.get().export(getCanvas().getLayerId(),
						bounds.getMinX(),
						bounds.getMinY(),
						bounds.getMaxX(),
						bounds.getMaxY(), (ToShapeOptions)getOptions(),
						new AsyncCallback<String>() {
							@Override
							public void onSuccess(String url) {
								_exportHandle.setUrl(url);
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
