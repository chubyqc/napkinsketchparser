package nsp.client;

public class Utils {
	private static final Utils _instance = new Utils();
	public static Utils get() {
		return _instance;
	}
	
	private static final String PX_SUFFIX = "px";
	
	public String toPx(int px) {
		return px + PX_SUFFIX;
	}
}
