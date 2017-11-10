package ui.listener;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ui.custom.CustomUtilities;

public class RescaleListener extends ComponentAdapter  {

	private Integer width;
	private Integer height;
	private Component target;
	
	public RescaleListener(Integer width, Integer height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	public RescaleListener() {
		this(null, null);
	}
	
	public RescaleListener(Component target) {
		this.target = target;
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		Component component = target == null ? (Component) e.getSource() : target;


		Dimension preferredSize = component.getSize();

		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(component);
		Dimension size = frame.getSize();
		
		if (width == null) {
			width = preferredSize.width;
		}
		
		if (height == null) {
			height = preferredSize.height;
		}
		
		if (size.width >= CustomUtilities.DEFAULT_WIDTH 
				|| size.height >= CustomUtilities.DEFAULT_HEIGHT) {
			if (component instanceof JPanel) {
				component.setPreferredSize(new Dimension(
						CustomUtilities.rescaleX(width, frame),
						CustomUtilities.rescaleY(height, frame)));
				component.revalidate();
				component.repaint();
			}
			
			if (component instanceof JLabel) {
				Font font = component.getFont();
				component.setFont(new Font(font.getName(), font.getStyle(), 
						CustomUtilities.rescaleX(font.getSize(), frame)));
			}
		}
	}

	
}
