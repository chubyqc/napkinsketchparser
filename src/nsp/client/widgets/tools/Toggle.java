package nsp.client.widgets.tools;

import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.ToggleButton;

public abstract class Toggle extends AbstractTool {
	
	private Toggle[] _exclusivity;
	
	public Toggle() {
		_exclusivity = new Toggle[0];
	}
	
	public void setExclusivity(Toggle[] exclusivity) {
		_exclusivity = exclusivity;
	}
	
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
	
	void toggleDown() {
		((ToggleButton)getWidget()).setDown(true);
		activate();
	}

	final void activate() {
		for (Toggle toggle : _exclusivity) {
			((ToggleButton)toggle.getWidget()).setDown(false);
			toggle.deactivate();
		}
		doActivate();
	}
	
	protected abstract void doActivate();

	protected abstract void deactivate();
}
