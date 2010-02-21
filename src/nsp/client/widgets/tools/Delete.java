package nsp.client.widgets.tools;

import com.google.gwt.user.client.ui.ButtonBase;

public class Delete extends AbstractTool {
	
	private static final String NAME = "Delete";

	@Override
	protected void clicked(ButtonBase button) {
		getCanvas().delete();
	}

	@Override
	protected String getName() {
		return NAME;
	}
}
