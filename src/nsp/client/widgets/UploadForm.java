package nsp.client.widgets;

import nsp.client.NapkinSketchParser;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

public class UploadForm extends AbstractWidget {
	
	private static final String FORM_ACTION = "/napkinsketchparser/uploadFile";
	private static final String UPLOAD_NAME = "upload";
	private static final String BUTTON_CAPTION = "Submit";
	
	private AbsolutePanel _panel;
	private FormPanel _form;
	private NapkinSketchParser _nsp;

	public UploadForm(NapkinSketchParser nsp) {
		_nsp = nsp;
		_panel = new AbsolutePanel();
		_form = new FormPanel();
		_form.setEncoding(FormPanel.ENCODING_MULTIPART);
		_form.setMethod(FormPanel.METHOD_POST);
		_form.setAction(FORM_ACTION);
		

		FileUpload upload = new FileUpload();
		upload.setName(UPLOAD_NAME);
		
		_panel.add(_form);
		_form.add(upload);
		
		_panel.add(new Button(BUTTON_CAPTION, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				_form.submit();
			}
		}));
		
		_form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				_nsp.fileUploaded();
			}
		});
	}
	
	@Override
	protected Widget getWidget() {
		return _panel;
	}
}
