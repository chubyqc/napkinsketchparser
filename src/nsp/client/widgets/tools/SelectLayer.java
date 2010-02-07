package nsp.client.widgets.tools;

public class SelectLayer extends Toggle {
	
	private static final String NAME = "Select Layer";

	@Override
	protected void doActivate() {
		getCanvas().activateSelectLayer();
	}

	@Override
	protected void deactivate() {
		getCanvas().deactivateSelectLayer();
	}

	@Override
	protected String getName() {
		return NAME;
	}
}
