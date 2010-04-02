package nsp.server.core;

import java.io.InputStream;

import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.ToShapeOptions;

public interface IServerFacade {

	void addImage(String layerId, InputStream image);
	
	String getImagePath(String layerId) throws NoImageException;
	
	String copyImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws Exception;
	
	String cutImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom) throws Exception;
	
	String toShape(String srcLayerId, String dstLayerId, int left, int top, int right, 
			int bottom, ToShapeOptions options) throws Exception;
	
	void mergeImage(String[] layerIds, int[] lefts, int[] tops, int[] rights, int[] bottoms) throws Exception;

	Rectangle findShape(String layerId, int left, int top, int right, int bottom) throws Exception;
}
