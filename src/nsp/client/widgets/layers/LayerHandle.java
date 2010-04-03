package nsp.client.widgets.layers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import nsp.client.Styles;
import nsp.client.widgets.AbstractWidget;

public class LayerHandle extends AbstractWidget {

	private static final String NAME = "Layer ";
	private static final String BTN_MOVEUP = "/\\";
	private static final String BTN_MOVEDOWN = "\\/";
	private static final String BTN_DELETE = "X";
	private static final String STYLE_ITEM = "layerHandle";
	
	private HorizontalPanel _container;
	private Label _label;
	private Button _moveUp;
	private Button _moveDown;
	private Button _delete;
	private ClickHandler _handler;
	private HandlerRegistration _registration;
	private String _layerId;
	
	public LayerHandle(final ImagesManager manager, final ImageContainer image,
			String layerId) {
		_layerId = layerId;
		
		_container = new HorizontalPanel();
		_label = new Label(NAME + _layerId);
		_moveUp = new Button(BTN_MOVEUP);
		_moveDown = new Button(BTN_MOVEDOWN);
		_delete = new Button(BTN_DELETE);
		
		_container.addStyleName(STYLE_ITEM);
		_container.add(_label);
		_container.add(_moveUp);
		_container.add(_moveDown);
		_container.add(_delete);
		
		_handler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				manager.select(LayerHandle.this, image, 
						event.getNativeEvent().getCtrlKey());
			}
		};
		_registration = _label.addClickHandler(_handler);
		
		_moveUp.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				manager.moveUp(LayerHandle.this, image);
			}
		});
		_moveDown.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				manager.moveDown(LayerHandle.this, image);
			}
		});
		_delete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				manager.delete(LayerHandle.this, image);
			}
		});
	}
	
	public String getId() {
		return _layerId;
	}
	
	void unselected() {
		_container.removeStyleName(Styles.get().getSelectedHandle());
		_registration = _label.addClickHandler(_handler);
	}
	
	void selected(boolean append) {
		_container.addStyleName(Styles.get().getSelectedHandle());
		if (!append) {
			_registration.removeHandler();
			_registration = null;
		}
	}

	@Override
	public Widget getWidget() {
		return _container;
	}
}
