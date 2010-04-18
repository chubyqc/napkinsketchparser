package nsp.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ExportHandle extends AbstractWidget {
	
	private static final String TEXT = "Exported";
	private static final String STYLE = "exportHandle";
	
	private FocusPanel _widget;
	private String _url;
	
	public ExportHandle() {
		_widget = new FocusPanel();
		_widget.add(new HTML(TEXT));
		_widget.addStyleName(STYLE);
		_widget.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (_url != null) {
					Window.open(_url, new String(), new String());
				}
			}
		});
	}
	
	public void setUrl(String url) {
		_url = url;
	}

	@Override
	public Widget getWidget() {
		return _widget;
	}

}
