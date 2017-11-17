package ui.popup;

import javax.swing.JPanel;
import javax.swing.JRootPane;

public class ErrorPane extends OverlayPane {
	
	public ErrorPane(JRootPane rootPane, String reason) {
		this(rootPane, createErrorPane(), reason);
	}
	
	private ErrorPane(JRootPane rootPane, JPanel contentPane, String reason) {
		super(rootPane, contentPane, OverlayPane.ERROR_STYLE);

		setTitle("Error", reason);
		disableOutOfBoundsClose();
		setMinWidth(400);
		setMinHeight(100);
		setScale(0);
	}
	
	private static JPanel createErrorPane() {
		JPanel errorPane = new JPanel();
		errorPane.setOpaque(false);
		
		return errorPane;
	}
	
}
