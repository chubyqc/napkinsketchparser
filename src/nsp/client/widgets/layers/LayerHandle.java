package nsp.client.widgets.layers;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import nsp.client.widgets.AbstractWidget;

public class LayerHandle extends AbstractWidget {

	private static final String NAME = "Layer ";
	
	private static int _layerId = 0;
	
	private Label _label;
	
	public LayerHandle() {
		_label = new Label(NAME + ++_layerId);
	}

	@Override
	public Widget getWidget() {
		return _label;
	}
}
