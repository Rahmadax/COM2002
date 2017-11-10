package ui.custom;

import javax.swing.JTextArea;
import javax.swing.UIManager;

public class CustomJTextArea extends JTextArea {
	
	public CustomJTextArea(String str) {
		super(str);

		this.setWrapStyleWord(true);
		this.setLineWrap(true);
		this.setOpaque(false);
		this.setEditable(false);
		this.setFocusable(false);
		this.setBackground(UIManager.getColor("Label.background"));
		this.setFont(UIManager.getFont("Label.font"));
		this.setBorder(UIManager.getBorder("Label.border"));
	}
	
}
