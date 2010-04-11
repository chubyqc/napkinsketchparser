package nsp.client;

import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.FindShapeOptions;
import nsp.client.widgets.tools.options.ToShapeOptions;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("gwtFacade")
public interface IGWTFacade extends RemoteService {
	String getImagePath(String layerId) throws NSPException;
	
	String copyImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws NSPException;
	
	String cutImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws NSPException;
	
	void mergeImages(String[] layerIds, int[] lefts, int[] tops, int[] rights, int[] bottoms) throws NSPException;
	
	String toShape(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom,
			ToShapeOptions options) throws NSPException;
	
	String toChar(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom,
			ToShapeOptions options) throws NSPException;

	Rectangle findShape(String layerId, int left, int top, int right, int bottom) throws NSPException;

	Rectangle[] findAllShapes(String layerId, int minX, int minY, int maxX,
			int maxY, FindShapeOptions findShapeOptions) throws NSPException;
}
