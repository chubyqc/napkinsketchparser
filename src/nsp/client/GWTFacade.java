package nsp.client;

import com.google.gwt.core.client.GWT;

public class GWTFacade {
	private static IGWTFacadeAsync _facade = GWT.create(IGWTFacade.class);
	
	public static IGWTFacadeAsync get() {
		return _facade;
	}
}
