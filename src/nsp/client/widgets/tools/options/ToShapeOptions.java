package nsp.client.widgets.tools.options;

public class ToShapeOptions extends Options {

	private static final long serialVersionUID = 1L;
	private static final String COLOR_TOLERANCE = "Color tolerance";

	public ToShapeOptions() {
		put(COLOR_TOLERANCE, "225");
	}
	
	public int getColorTolerance() {
		String value = get(COLOR_TOLERANCE);
		return (value == null || value.length() == 0) ? 0 : 
			Integer.parseInt(value);
	}
}
