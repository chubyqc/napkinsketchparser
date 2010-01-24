package nsp.server.core;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class SessionManager {

	private static SessionManager _instance = new SessionManager();
	public static SessionManager getInstance() {
		return _instance;
	}
	
	private HashMap<String, IServerFacade> _facades;
	
	private SessionManager() {
		_facades = new HashMap<String, IServerFacade>();
	}
	
	public IServerFacade getFacade(HttpSession session) {
		IServerFacade facade = _facades.get(session.getId());
		if (facade == null) {
			facade = new ServerFacade(session.getId());
			_facades.put(session.getId(), facade);
		}
		return facade;
	}
}
