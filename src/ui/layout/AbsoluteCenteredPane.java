package ui.layout;

import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

public class AbsoluteCenteredPane extends JPanel {
	
	private JPanel contentPane;
	private JPanel container;
	private ComponentListener resizeListener;
	
	private int minWidth = 600;
	private int minHeight = 400;

	public AbsoluteCenteredPane(JPanel contentPane, JPanel container) {
		super();
		
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
		setLayout(null);
		add(contentPane);
	}
	
	public ComponentListener getResizeListener() {
		return resizeListener;
	}

	private void recalculateBounds() {
		Rectangle bounds = container.getBounds();
		int width = Math.max(bounds.width / 2, minWidth);
		int height = Math.max(bounds.height / 2, minHeight);
		
		contentPane.setBounds(
				(bounds.width - width) / 2, (bounds.height - height) / 2,
				width, height);
	}

}
