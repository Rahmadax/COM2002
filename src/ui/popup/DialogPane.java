package ui.popup;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JRootPane;

import ui.custom.button.CustomButton;

public class DialogPane extends OverlayPane {
	
	private CustomButton okButton;
	private CustomButton cancelButton;

	public DialogPane(JRootPane rootPane, String dialog) {
		super(rootPane, new JPanel());
		setContentPane(createDialogPane());
		setTitle("Warning", dialog);
		setCloseButtonVisibility(false);
		
		disableOutOfBoundsClose();
		setConstraints(400, 160, 0, 0);
	}
	
	public JPanel getOKButton() {
		return okButton;
	}
	
	public JPanel getCancelButton() {
		return cancelButton;
	}
	
	private JPanel createDialogPane() {
		JPanel dialogPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		dialogPane.setBackground(new Color(100, 100, 100));
		
		CustomButton okButton = 
				new CustomButton("OK", CustomButton.REVERSED_STYLE);
		CustomButton cancelButton = 
				new CustomButton("Cancel");
		
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				hide();
			}
		});
		
		this.okButton = okButton;
		this.cancelButton = cancelButton;
		
		dialogPane.add(okButton);
		dialogPane.add(cancelButton);
		
		return dialogPane;
	}
	
}
