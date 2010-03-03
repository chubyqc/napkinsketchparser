package nsp.client.widgets.tools;

import java.util.Collection;

import nsp.client.GWTFacade;
import nsp.client.widgets.layers.ImageContainer;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ButtonBase;

public class Merge extends AbstractTool {
	
	private static final String NAME = "Merge";

	@Override
	protected void clicked(ButtonBase button) {
		Collection<ImageContainer> layers = getCanvas().getSelectedLayers();
		String[] layerIds = new String[layers.size()];
		final int[] lefts = new int[layers.size()];
		final int[] tops = new int[layers.size()];
		int[] rights = new int[layers.size()];
		int[] bottoms = new int[layers.size()];
		int i = 0;
		for (ImageContainer layer : layers) {
			layerIds[i] = layer.getHandle().getId();
			lefts[i] = layer.getLeft();
			tops[i] = layer.getTop();
			rights[i] = layer.getRight();
			bottoms[i] = layer.getBottom();
			++i;
		}
		GWTFacade.get().mergeImages(layerIds, lefts, tops, rights, bottoms, new AsyncCallback<Void>() {			
			@Override
			public void onSuccess(Void result) {
				getCanvas().layerMerged(getSmallest(lefts), getSmallest(tops));
			}			
			@Override
			public void onFailure(Throwable caught) {}
		});
	}
	
	private int getSmallest(int[] items) {
		int smallest = Integer.MAX_VALUE;
		for (int item : items) {
			smallest = Math.min(smallest, item);
		}
		return smallest;
	}

	@Override
	protected String getName() {
		return NAME;
	}
}
