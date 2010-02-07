package nsp.client.widgets.tools;

public class Move extends Toggle {

	private static final String NAME = "Move";
	
	@Override
	protected String getName() {
		return NAME;
	}
	
	@Override
	protected void doActivate() {
		getCanvas().activateMove();
	}
	
	@Override
	protected void deactivate() {
		getCanvas().deactivateMove();
	}
}
