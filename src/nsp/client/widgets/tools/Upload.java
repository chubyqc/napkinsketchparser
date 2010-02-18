package nsp.client.widgets.tools;

import nsp.client.widgets.UploadForm;

import com.google.gwt.user.client.ui.ButtonBase;

public class Upload extends AbstractTool {
	
	private static final String NAME = "Upload";
	
	private UploadForm _form;
	
	public Upload(UploadForm form) {
		_form = form;
	}

	@Override
	protected void clicked(ButtonBase button) {
		_form.submit(getCanvas().getNextLayerId());
	}

	@Override
	protected String getName() {
		return NAME;
	}

}
