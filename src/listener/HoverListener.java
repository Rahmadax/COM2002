package listener;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverListener extends MouseAdapter {
	
	private Color initial;
	private Color hover;

	public HoverListener(Color initial, Color hover) {
		this.initial = initial;
		this.hover = hover;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		Component component = e.getComponent();
		
		component.setBackground(hover);
		
		component.revalidate();
		component.repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		Component component = e.getComponent();
		
		component.setBackground(initial);
		
		component.revalidate();
		component.repaint();
	}
	
}
