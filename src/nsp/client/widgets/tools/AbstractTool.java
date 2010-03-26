package nsp.client.widgets.tools;

import java.util.Map.Entry;

import nsp.client.widgets.AbstractWidget;
import nsp.client.widgets.canvas.DrawingCanvas;
import nsp.client.widgets.tools.options.Options;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractTool extends AbstractWidget {
	
	private static final String SHOW = "+";
	private static final String HIDE = "-";
	private static final String STYLE_TOGGLER = "optionToggler";
	
	private ButtonBase _button;
	private DrawingCanvas _canvas;
	private Options _options;
	private Widget _widget;
	
	public AbstractTool(Options options) {
		_options = options;
		_widget = _button = createButton(getName());
		createOptionsPanel();
		_button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clicked(_button);
			}
		});
	}
	
	public AbstractTool() {
		this(null);
	}
	
	private void createOptionsPanel() {
		if (_options != null) {
			VerticalPanel vPanel = new VerticalPanel();
			HorizontalPanel hPanel = new HorizontalPanel();
			VerticalPanel options = new VerticalPanel();
			options.setVisible(false);
			_widget = vPanel;
			
			vPanel.add(hPanel);
			vPanel.add(options);
			
			hPanel.add(_button);
			hPanel.add(createOptionsToggler(options));
			
			for (Entry<String, String> entry : _options.entrySet()) {
				createOptionEntry(options, entry);
			}
		}
	}
	
	private void createOptionEntry(VerticalPanel optionsPanel, final Entry<String, String> entry) {
		HorizontalPanel panel = new HorizontalPanel();
		Label label = new Label(entry.getKey());
		final TextBox input = new TextBox();
		panel.add(label);
		panel.add(input);
		
		Object value = entry.getValue();
		input.setText((value == null) ? null : value.toString());
		
		optionsPanel.add(label);
		optionsPanel.add(input);
		
		input.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				entry.setValue(input.getText());
			}
		});
	}
	
	private Widget createOptionsToggler(final Widget optionsPanel) {
		final Label toggler = new Label(SHOW);
		toggler.addStyleName(STYLE_TOGGLER);
		toggler.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (toggler.getText().equals(SHOW)) {
					toggler.setText(HIDE);
					optionsPanel.setVisible(true);
				} else {
					toggler.setText(SHOW);
					optionsPanel.setVisible(false);
				}
			}
		});
		return toggler;
	}
	
	protected abstract void clicked(ButtonBase button);
	
	protected ButtonBase createButton(String name) {
		return new Button(name);
	}
	
	@Override
	public Widget getWidget() {
		return _widget;
	}
	
	void setCanvas(DrawingCanvas canvas) {
		_canvas = canvas;
	}
	
	protected DrawingCanvas getCanvas() {
		return _canvas;
	}
	
	protected Options getOptions() {
		return _options;
	}
	
	protected abstract String getName();
}
