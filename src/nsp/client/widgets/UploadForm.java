package nsp.client.widgets;

import nsp.client.NapkinSketchParser;
import nsp.client.Utils;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

public class UploadForm extends AbstractWidget {
	
	private static final String FORM_ACTION = "/napkinsketchparser/uploadFile";
	private static final String UPLOAD_NAME = "upload";
	
	private AbsolutePanel _panel;
	private FormPanel _form;
	private NapkinSketchParser _nsp;
	private Hidden _layerIdField;

	public UploadForm(NapkinSketchParser nsp) {
		_nsp = nsp;
		_panel = new AbsolutePanel();
		_form = new FormPanel();
		AbsolutePanel innerPanel = new AbsolutePanel();
		_form.setEncoding(FormPanel.ENCODING_MULTIPART);
		_form.setMethod(FormPanel.METHOD_POST);
		_form.setAction(FORM_ACTION);
		_form.setWidget(innerPanel);

		_panel.add(_form);
		
		innerPanel.add(_layerIdField = new Hidden(Utils.get().getLayerIdKey()));

		FileUpload upload = new FileUpload();
		upload.setName(UPLOAD_NAME);
		
		innerPanel.add(upload);
		
		_form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				_nsp.fileUploaded(_layerIdField.getValue());
			}
		});
	}
	
	public void submit(String layerId) {
		_layerIdField.setValue(layerId);
		_form.submit();
	}
	
	@Override
	public Widget getWidget() {
		return _panel;
	}
}
