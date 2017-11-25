package ui.custom;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import ui.custom.button.CustomButton;

public class CustomSwitch extends JPanel {
	
	private boolean value;
	
	private JPanel container;
	private CustomButton button1;
	private CustomButton button2;

	public CustomSwitch() {
		super(new GridLayout(1, 2));
		
		this.value = false;
		this.container = this;
		
		addButtons();
	}
	
	private void addButtons() {
		removeAll();
		
		int style1, style2;
		
		if (value) {
			style1 = CustomButton.DEFAULT_STYLE;
			style2 = CustomButton.REVERSED_STYLE;
		} else {
			style1 = CustomButton.REVERSED_STYLE;
			style2 = CustomButton.DEFAULT_STYLE;
		}
		
		button1 = new SwitchButton("No", style1);
		button2 = new SwitchButton("Yes", style2);
		
		add(button1);
		add(button2);
		
		revalidate();
		repaint();
	}
	
	public boolean getValue() {
		return this.value;
	}
	
	public void makeSwitch(int button) {
		value = button != 0;
		addButtons();
	}
	
	private void makeSwitch(CustomButton button) {
		value = button1 != button;
		addButtons();
	}
	
	private class SwitchButton extends CustomButton {
		public SwitchButton(String text, int style) {
			super(text, style);
			CustomButton button = this;
			
			for (MouseListener l: getMouseListeners()) {
				removeMouseListener(l);
			}
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					makeSwitch(button);
				}
			});
		}
	}
	
	
}
