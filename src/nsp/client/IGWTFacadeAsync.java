package nsp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface IGWTFacadeAsync {
	void getImagePath(AsyncCallback<String> callback);
}
