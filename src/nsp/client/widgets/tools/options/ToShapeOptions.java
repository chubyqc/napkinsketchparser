package nsp.client.widgets.tools.options;

public class ToShapeOptions extends Options {

	private static final long serialVersionUID = 1L;
	static final String COLOR_TOLERANCE = "Color tolerance";
	private static final String PIXELON_TOLERANCE = "PixelOn percentage";

	public ToShapeOptions() {
		put(COLOR_TOLERANCE, "225");
		put(PIXELON_TOLERANCE, "0.1");
	}
	
	public int getColorTolerance() {
		String value = get(COLOR_TOLERANCE);
		return (value == null || value.length() == 0) ? 0 : 
			Integer.parseInt(value);
	}
	
	public double getPixelOnPercentage() {
		String value = get(PIXELON_TOLERANCE);
		return (value == null || value.length() == 0) ? 0 :
			Double.parseDouble(value);
	}
}
