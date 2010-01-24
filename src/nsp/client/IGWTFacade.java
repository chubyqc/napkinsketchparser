package nsp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("gwtFacade")
public interface IGWTFacade extends RemoteService {
	String greetServer(String name) throws NSPException;
}
