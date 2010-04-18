package nsp.server.recognition;

import nsp.client.geom.Rectangle;
import nsp.server.recognition.builders.results.Result;

public class BoundedResults {
	
	private Rectangle[] _recs;
	private Result[] _results;

	public BoundedResults(Rectangle[] recs, Result[] results) {
		_recs = recs;
		_results = results;
	}

	public Result[] get() {
		return _results;
	}
	
	public Rectangle[] getRecs() {
		return _recs;
	}
}
