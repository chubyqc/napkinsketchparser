package nsp.client;

import nsp.client.widgets.DrawingCanvas;
import nsp.client.widgets.SelectionBorder;
import nsp.client.widgets.UploadForm;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NapkinSketchParser implements EntryPoint {
	
	private DrawingCanvas _canvas;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		new UploadForm(this).appendTo(RootPanel.get());
		_canvas = new DrawingCanvas(1000, 1000);
		_canvas.appendTo(RootPanel.get());
		new SelectionBorder(_canvas);
	}
	
	public void fileUploaded() {
		GWTFacade.get().getImagePath(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				_canvas.addImage(result);
			}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
