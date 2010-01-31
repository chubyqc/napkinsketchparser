package nsp.client.widgets.tools;

import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.ToggleButton;

public abstract class ToggleTool extends AbstractTool {
	
	@Override
	protected ButtonBase createButton(String name) {
		return new ToggleButton(name);
	}
	
	@Override
	protected void clicked(ButtonBase button) {
		if (((ToggleButton)button).isDown()) {
			activate();
		} else {
			deactivate();
		}
	}

	protected abstract void activate();

	protected abstract void deactivate();
}
