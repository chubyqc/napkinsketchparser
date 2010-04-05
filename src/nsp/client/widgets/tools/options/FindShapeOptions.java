package nsp.client.widgets.tools.options;

public class FindShapeOptions extends Options {

	private static final long serialVersionUID = 1L;

	public FindShapeOptions() {
		put(ToShapeOptions.COLOR_TOLERANCE, "225");
	}
	
	public int getColorTolerance() {
		String value = get(ToShapeOptions.COLOR_TOLERANCE);
		return (value == null || value.length() == 0) ? 0 : 
			Integer.parseInt(value);
	}
}
