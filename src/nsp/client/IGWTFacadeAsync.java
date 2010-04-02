package nsp.client;

import nsp.client.geom.Rectangle;
import nsp.client.widgets.tools.options.ToShapeOptions;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IGWTFacadeAsync {
	void getImagePath(String layerId, AsyncCallback<String> callback);
	
	void copyImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom,
			AsyncCallback<String> callback);
	
	void cutImage(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom,
			AsyncCallback<String> callback);
	
	void mergeImages(String[] layerIds, int[] lefts, int[] tops, int[] rights, int[] bottoms,
			AsyncCallback<Void> callback);
	
	void toShape(String srcLayerId, String dstLayerId, int left, int top, int right, int bottom,
			ToShapeOptions options, AsyncCallback<String> callback);

	void findShape(String layerId, int left, int top, int right, int bottom,
			AsyncCallback<Rectangle> callback);
}
