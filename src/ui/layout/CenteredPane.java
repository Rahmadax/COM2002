package ui.layout;

import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class CenteredPane extends JPanel {

	public CenteredPane(JComponent component) {
		super();
		
		setOpaque(false);
		setLayout(new GridBagLayout());
		add(component);
	}

}
