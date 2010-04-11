package nsp.client;

import nsp.client.widgets.UploadForm;
import nsp.client.widgets.canvas.DrawingCanvas;
import nsp.client.widgets.tools.Copy;
import nsp.client.widgets.tools.Cut;
import nsp.client.widgets.tools.FindAllShapes;
import nsp.client.widgets.tools.FindShape;
import nsp.client.widgets.tools.Merge;
import nsp.client.widgets.tools.Move;
import nsp.client.widgets.tools.SelectLayer;
import nsp.client.widgets.tools.ToChar;
import nsp.client.widgets.tools.ToShape;
import nsp.client.widgets.tools.Toggle;
import nsp.client.widgets.tools.Toolbar;
import nsp.client.widgets.tools.Upload;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class NapkinSketchParser implements EntryPoint {
	
	private DrawingCanvas _canvas;
	
	public void onModuleLoad() {
		_canvas = new DrawingCanvas(1000, 1000, RootPanel.get());
		createToolbar();
		_canvas.appendTo(RootPanel.get());
		
		RootPanel.get().sinkEvents(Event.KEYEVENTS);
	}
	
	private void createToolbar() {
		UploadForm form = new UploadForm(this);
		form.appendTo(RootPanel.get());
		Toolbar toolbar = new Toolbar(_canvas);
		toolbar.appendTo(RootPanel.get());
		toolbar.addTool(new Upload(form));

		Move move = new Move();
		toolbar.addTool(new Copy(move));
		toolbar.addTool(new Cut(move));
		toolbar.addTool(new ToShape(move));
		toolbar.addTool(new ToChar(move));
		toolbar.addTool(new FindShape());
		toolbar.addTool(new FindAllShapes());
		toolbar.addTool(move);
		
		SelectLayer selectLayer = new SelectLayer();
		move.setExclusivity(new Toggle[] { selectLayer });
		selectLayer.setExclusivity(new Toggle[] { move });
		
		toolbar.addTool(selectLayer);
		toolbar.addTool(new Merge());
	}
	
	public void fileUploaded(String layerId) {
		GWTFacade.get().getImagePath(layerId, new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				_canvas.addImage(result);
			}
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
}
