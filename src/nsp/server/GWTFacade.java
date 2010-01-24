package nsp.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	public String getImagePath() throws NSPException {
		try {
			return toRelativePath(getFacade().getImagePath());
		} catch (Exception e) {
			throw new NSPException();
		}
	}
	
	private String toRelativePath(String absolutePath) {
		HttpServletRequest req = getRequest();
		return absolutePath.substring(
				getServletContext().getRealPath(req.getContextPath()).length() - 1);
	}
	
	private HttpServletRequest getRequest() {
		return getThreadLocalRequest();
	}
	
	private HttpSession getSession() {
		return getRequest().getSession();
	}
	
	private IServerFacade getFacade() {
		return SessionManager.getInstance().getFacade(getSession());
	}
}
