package nsp.client.widgets.tools;

public class MoveTool extends ToggleTool {

	private static final String NAME = "Move";
	
	@Override
	protected String getName() {
		return NAME;
	}
	
	@Override
	protected void activate() {
		getCanvas().activateMove();
	}
	
	@Override
	protected void deactivate() {
		getCanvas().deactivateMove();
	}
}
