package nsp.client;

import nsp.client.widgets.ImageContainer;
import nsp.client.widgets.UploadForm;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NapkinSketchParser implements EntryPoint {
	
	private ImageContainer _image;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		new UploadForm(this).appendTo(RootPanel.get());
		_image = new ImageContainer();
		_image.appendTo(RootPanel.get());
	}
	
	public void fileUploaded() {
		GWTFacade.get().getImagePath(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				_image.updateUrl(result);
			}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
