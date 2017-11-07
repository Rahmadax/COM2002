package ui.layout;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class AnchorTopPane extends JPanel {

	public AnchorTopPane(JPanel panel) {
		super();

		setLayout(new BorderLayout());
		setOpaque(false);
		add(panel, BorderLayout.NORTH);
	}
	
}
