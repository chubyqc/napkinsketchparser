package nsp.server;

import nsp.client.IGWTFacade;
import nsp.client.NSPException;
import nsp.server.core.IServerFacade;
import nsp.server.core.SessionManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GWTFacade extends RemoteServiceServlet implements
		IGWTFacade {

	public String greetServer(String input) throws NSPException {
		try {
			return getFacade().getImagePath();
		} catch (Exception e) {
			throw new NSPException();
		}
	}
	
	private IServerFacade getFacade() {
		return SessionManager.getInstance().getFacade(getThreadLocalRequest().getSession());
	}
}
