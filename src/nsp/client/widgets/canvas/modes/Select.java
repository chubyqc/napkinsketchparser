package nsp.client.widgets.canvas.modes;

public class Select extends AbstractMode {

	@Override
	protected void mouseMoved(int xOffset, int yOffset) {
		getBorder().setSize(xOffset, yOffset);
	}

	@Override
	protected void mousePressed() {
		getBorder().setSize(0, 0);
		getCanvas().setBorderPosition(getInitialX(), getInitialY());
	}
}
