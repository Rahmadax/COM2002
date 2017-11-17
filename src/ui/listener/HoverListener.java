package ui.listener;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HoverListener extends MouseAdapter {
	
	private Color initial;
	private Color hover;
	
	private Component target;

	public HoverListener(Color initial, Color hover) {
		this.initial = initial;
		this.hover = hover;
	}
	
	public HoverListener(Color initial, Color hover, Component target) {
		this(initial, hover);
		this.target = target;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Component component;
				
				if (target == null) {
					component = e.getComponent();
				} else {
					component = target;
				}
				
				if (component instanceof JPanel) {
					component.setBackground(hover);
				}
				
				if (component instanceof JLabel) {
					component.setForeground(hover);
				}
				
				component.revalidate();
				component.repaint();
			}
		});
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Component component;
				
				if (target == null) {
					component = e.getComponent();
				} else {
					component = target;
				}		
				
				if (component instanceof JPanel) {
					component.setBackground(initial);
				}
				
				if (component instanceof JLabel) {
					component.setForeground(initial);
				}
				
				component.revalidate();
				component.repaint();
			}
		});
	}
	
}
