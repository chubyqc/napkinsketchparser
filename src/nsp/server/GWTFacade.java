package nsp.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nsp.client.IGWTFacade;
import nsp.client.NSPException;
import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.FindShapeOptions;
import nsp.client.widgets.tools.options.ToShapeOptions;
import nsp.server.core.IServerFacade;
import nsp.server.core.SessionManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GWTFacade extends RemoteServiceServlet implements
		IGWTFacade {
	
	@Override
	public void init() throws ServletException {
		super.init();
		Utils.get().setAppPath(getServletContext().getRealPath(new String()));
	}

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
	
	public String toShape(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom,
			ToShapeOptions options) throws NSPException {
		try {
			return toRelativePath(getFacade().toShape(srcLayerId, dstLayerId, left, 
					top, right, bottom, options));
		} catch (Exception e) {
			throw new NSPException();
		}
	}
	
	public String toChar(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom,
			ToShapeOptions options) throws NSPException {
		try {
			return toRelativePath(getFacade().toChar(srcLayerId, dstLayerId, left, 
					top, right, bottom, options));
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

	@Override
	public Rectangle findShape(String layerId, int left, int top, int right,
			int bottom) throws NSPException {
		try {
			return getFacade().findShape(layerId, left, top, right, bottom);
		} catch (Exception e) {
			throw new NSPException();
		}
	}

	@Override
	public Rectangle[] findAllShapes(String layerId, int minX, int minY,
			int maxX, int maxY, FindShapeOptions options) throws NSPException {
		try {
			return getFacade().findAllShape(layerId, minX, minY, maxX, maxY, options);
		} catch (Exception e) {
			throw new NSPException();
		}
	}

	@Override
	public Rectangle[] toText(String srcLayerId, String dstLayerId, int left, int top, int right,
			int bottom, ToShapeOptions options) throws NSPException {
		try {
			return toRelativePaths(getFacade().toText(srcLayerId, dstLayerId, left, 
					top, right, bottom, options));
		} catch (Exception e) {
			throw new NSPException();
		}
	}

	@Override
	public String export(String srcLayerId, int left, int top, int right,
			int bottom, ToShapeOptions options) throws NSPException {
		try {
			return toRelativePath(getFacade().export(srcLayerId, left, top, right, bottom,
				options));
		} catch (Exception e) {
			throw new NSPException();
		}
	}
	
	private Rectangle[] toRelativePaths(Rectangle[] recs) {
		HttpServletRequest req = getRequest();
		for (int i = 0; i < recs.length; ++i) {
			recs[i].setUrl(toRelativePath(recs[i].getUrl(), req));
		}
		return recs;
	}
	
	private String toRelativePath(String absolutePath) {
		HttpServletRequest req = getRequest();
		return toRelativePath(absolutePath, req);
	}
	
	private String toRelativePath(String absolutePath, HttpServletRequest req) {
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
