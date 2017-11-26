package ui.layout;

import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class AbsoluteCenteredPane extends JPanel {
	
	private JComponent contentPane;
	private JPanel container;
	private ComponentListener resizeListener;

	private int minWidth = 600;
	private int minHeignt = 400;
	private double scaleX = 2;
	private double scaleY = 2;

	public AbsoluteCenteredPane(JComponent contentPane, JPanel container) {
		super(null);
		
		this.contentPane = contentPane;
		this.container = container;
		
		resizeListener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				recalculateBounds();
				
				contentPane.revalidate();
				contentPane.repaint();
			}
		};
		
		addComponentListener(resizeListener);

		setOpaque(false);
		add(contentPane);
	}
	
	public void setConstraints(int minWidth, int minHeignt, 
			double scaleX, double scaleY) {
		this.minWidth = minWidth;
		this.minHeignt = minHeignt;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	public ComponentListener getResizeListener() {
		return resizeListener;
	}

	private void recalculateBounds() {
		Rectangle bounds = container.getBounds();
		int width, height;
		
		if (scaleX != 0) {
			width = (int) Math.max(bounds.width / scaleX, minWidth);
		} else {
			width = minWidth;
		}
		
		if (scaleY != 0) {
			height = (int) Math.max(bounds.height / scaleY, minHeignt);
		} else {
			height = minHeignt;
		}

		contentPane.setBounds(
				(bounds.width - width) / 2, (bounds.height - height) / 2,
				width, height);
	}

}
