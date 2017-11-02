package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OverlayPane extends PopupPane {
	
	private JPanel contentPane;
	
	private ComponentAdapter resizeListener;
	private MouseAdapter closeListener;
	
	public OverlayPane(JFrame frame, JPanel panel) {
		super(frame);
		
		contentPane = createContentPane(panel);
		
		resizeListener = createResizeListener();
		closeListener = createCloseListener();
		
		setLayout(null);
		add(contentPane);

		setBackground(new Color(0, 0, 0, 127));
		setBounds(glass.getBounds());
	}

	@Override
	public void show(Point location) {		
		glass.addComponentListener(resizeListener);
		glass.addMouseListener(closeListener);

		recalculateBounds();

		super.show(location);
	}

	@Override
	public void hide() {
		glass.removeComponentListener(resizeListener);
		glass.removeMouseListener(closeListener);
		
		super.hide();
	}

	private ComponentAdapter createResizeListener() {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				recalculateBounds();
				
				contentPane.revalidate();
				contentPane.repaint();
			}
		};
	}
	
	private MouseAdapter createCloseListener() {		
		return new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Point click = e.getPoint();
				Rectangle bounds = contentPane.getBounds();
				
				if (click.x < bounds.x || click.y < bounds.y || 
						click.x > bounds.x + bounds.width ||
						click.y > bounds.y + bounds.height) {
					hide();
				}
			}
		};
	}
	
	private JPanel createContentPane(JPanel panel) {
		Rectangle bounds = glass.getBounds();
		
		JPanel contentPane = new JPanel();
		contentPane.setBounds(bounds.width / 4, bounds.height / 4,
				bounds.width / 2, bounds.height / 2);
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(createCloseButton(), BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);
		
		return contentPane;
	}
	
	private JPanel createCloseButton() {
		JPanel container = new JPanel();
		JButton closeButton = new JButton("x");
		
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hide();
			}
		});
		
		container.setLayout(new BorderLayout());
		container.add(closeButton, BorderLayout.EAST);
		
		return container;
	}
	
	private void recalculateBounds() {
		Rectangle bounds = glass.getBounds();
		
		setBounds(bounds);
		contentPane.setBounds(bounds.width / 4, bounds.height / 4,
				bounds.width / 2, bounds.height / 2);
	}

}
