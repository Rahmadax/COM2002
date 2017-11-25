package ui.popup;

import javax.swing.JPanel;
import javax.swing.JRootPane;

public class SuccessPane extends OverlayPane {
	
	public SuccessPane(JRootPane rootPane, String reason) {
		this(rootPane, createSuccessPane(), reason);
	}
	
	private SuccessPane(JRootPane rootPane, JPanel contentPane, String reason) {
		super(rootPane, contentPane, OverlayPane.SUCCESS_STYLE);

		setTitle("Success", reason);
		disableOutOfBoundsClose();
		setConstraints(400, 100, 0, 0);
	}
	
	private static JPanel createSuccessPane() {
		JPanel successPane = new JPanel();
		successPane.setOpaque(false);
		
		return successPane;
	}
	
}
