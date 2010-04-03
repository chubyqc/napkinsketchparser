package nsp.client;

public class Styles {
	private static Styles _instance = new Styles();
	public static Styles get() {
		return _instance;
	}
	
	public String getLists() {
		return "lists";
	}
	
	public String getLayerList() {
		return "layerList";
	}
	
	public String getSelectionList() {
		return "selectionList";
	}
	
	public String getSelectionHandle() {
		return "selectionHandle";
	}
	
	public String getSelectedHandle() {
		return "selectedHandle";
	}
}
