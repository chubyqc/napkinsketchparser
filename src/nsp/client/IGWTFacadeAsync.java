package nsp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface IGWTFacadeAsync {
	void getImagePath(AsyncCallback<String> callback);
	
	void cropImage(int left, int top, int right, int bottom,
			AsyncCallback<String> callback);
}
