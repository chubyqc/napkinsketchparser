package nsp.client;

public class Styles {
	private static Styles _instance = new Styles();
	public static Styles get() {
		return _instance;
	}
	
	public String getLayerList() {
		return "layerList";
	}
}
