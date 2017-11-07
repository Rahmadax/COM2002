package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ui.custom.CustomButton;

public class OverlayPane extends PopupPane {

	private JPanel contentPane;
	
	private ComponentAdapter resizeListener;
	private MouseAdapter closeListener;
	
	public OverlayPane(JRootPane rootPane, JPanel panel) {
		super(rootPane);
		
		contentPane = createContentPane(panel);
		
		resizeListener = createResizeListener();
		closeListener = createCloseListener();
		
		setLayout(null);
		add(contentPane);

		setBackground(new Color(0, 0, 0, 127));
		setBounds(glass.getBounds());
	}

	public void show() {		
		glass.addComponentListener(resizeListener);
		glass.addMouseListener(closeListener);

		recalculateBounds();

		super.show(new Point(0, 0));
	}

	@Override
	public void hide() {
		glass.removeComponentListener(resizeListener);
		glass.removeMouseListener(closeListener);
		
		super.hide();
	}
	
	public void setTitlePane(JPanel title) {
		contentPane.add(createTitlePane(title), BorderLayout.NORTH);
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
		contentPane.setBackground(new Color(200, 200, 200));
		contentPane.setBorder(new CompoundBorder(
				new LineBorder(new Color(255, 160, 0), 2),
				new EmptyBorder(20, 20, 20, 20)));
		contentPane.setLayout(new BorderLayout());
		
		contentPane.add(createTitlePane(null), BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);
		
		return contentPane;
	}
	
	private JPanel createTitlePane(JPanel title) {
		JPanel titlePane = new JPanel(new BorderLayout());
		titlePane.setOpaque(false);
		
		JPanel closeButtonPane = new JPanel();
		closeButtonPane.setOpaque(false);
		closeButtonPane.add(createCloseButton());
		titlePane.add(closeButtonPane, BorderLayout.EAST);
		
		if (title != null) {
			titlePane.add(title, BorderLayout.WEST);
		}
		
		JPanel separatorPane = new JPanel(new BorderLayout());
		separatorPane.setOpaque(false);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(150, 150, 150));
		separator.setForeground(new Color(150, 150, 150));
		separatorPane.add(separator);
		separatorPane.setBorder(new EmptyBorder(10, 0, 10, 0));
		titlePane.add(separatorPane, BorderLayout.SOUTH);
		
		return titlePane;
	}
	
	private CustomButton createCloseButton() {
		CustomButton closeButton = new CustomButton("x", CustomButton.REVERSED);
		closeButton.setPreferredSize(new Dimension(45, 45));
		
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				hide();
			}
		});
		
		return closeButton;
	}
	
	private void recalculateBounds() {
		Rectangle bounds = glass.getBounds();
		
		setBounds(bounds);
		contentPane.setBounds(bounds.width / 4, bounds.height / 4,
				bounds.width / 2, bounds.height / 2);
	}

}
