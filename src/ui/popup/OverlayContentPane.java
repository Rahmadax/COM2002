package ui.popup;

import javax.swing.JPanel;

public class OverlayContentPane extends JPanel {

	private OverlayPane overlay;
	
	public OverlayContentPane(OverlayPane overlay) {
		super();
		
		this.overlay = overlay;
	}
	
	public OverlayPane getOverlay() {
		return overlay;
	}
	
}
