package ui.layout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class CenteredPane extends JPanel {

	public CenteredPane(JComponent component) {
		this();

		add(component);
	}
	
	public CenteredPane(JComponent component, GridBagConstraints c) {
		this();
		
		add(component, c);
	}
	
	private CenteredPane() {
		super();
		
		setOpaque(false);
		setLayout(new GridBagLayout());
	}

}
