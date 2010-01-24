package nsp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface IGWTFacadeAsync {
	void greetServer(String input, AsyncCallback<String> callback);
}
