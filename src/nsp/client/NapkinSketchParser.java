package nsp.client;

import nsp.client.widgets.UploadForm;
import nsp.client.widgets.canvas.DrawingCanvas;
import nsp.client.widgets.layers.ImagesManager;
import nsp.client.widgets.tools.CropTool;
import nsp.client.widgets.tools.MoveTool;
import nsp.client.widgets.tools.Toolbar;
import nsp.client.widgets.tools.Upload;

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
		ImagesManager _imagesManager = new ImagesManager();
		_imagesManager.appendTo(RootPanel.get());
		_canvas = new DrawingCanvas(1000, 1000, _imagesManager);
		createToolbar();
		_canvas.appendTo(RootPanel.get());
	}
	
	private void createToolbar() {
		UploadForm form = new UploadForm(this);
		form.appendTo(RootPanel.get());
		Toolbar toolbar = new Toolbar(_canvas);
		toolbar.appendTo(RootPanel.get());
		toolbar.addTool(new Upload(form));
		toolbar.addTool(new CropTool());
		toolbar.addTool(new MoveTool());
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
