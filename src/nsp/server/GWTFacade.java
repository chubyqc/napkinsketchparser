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

	public String getImagePath(String layerId) throws NSPException {
		try {
			return toRelativePath(getFacade().getImagePath(layerId));
		} catch (Exception e) {
			throw new NSPException();
		}
	}
	
	public String copyImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws NSPException {
		try {
			return toRelativePath(getFacade().copyImage(srcLayerId, dstLayerId, left, top, right, bottom));
		} catch (Exception e) {
			throw new NSPException();
		}
	}
	
	public String cutImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws NSPException {
		try {
			return toRelativePath(getFacade().cutImage(srcLayerId, dstLayerId, left, top, right, bottom));
		} catch (Exception e) {
			throw new NSPException();
		}
	}
	
	@Override
	public void mergeImages(String[] layerIds, int[] lefts, int[] tops, int[] rights,
			int[] bottoms) throws NSPException {
		try {
			getFacade().mergeImage(layerIds, lefts, tops, rights, bottoms);
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
