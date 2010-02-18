package nsp.client;

public class Utils {
	private static final Utils _instance = new Utils();
	public static Utils get() {
		return _instance;
	}
	
	private static final String PX_SUFFIX = "px";
	
	private static final String PARAM_LAYERID = "layerId";
	
	public String toPx(int px) {
		return px + PX_SUFFIX;
	}
	
	public String getLayerIdKey() {
		return PARAM_LAYERID;
	}
}
