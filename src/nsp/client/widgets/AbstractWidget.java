package nsp.client.widgets;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractWidget {
	
	protected abstract Widget getWidget();
	
	public void appendTo(Panel parent) {
		parent.add(getWidget());
	}
}
