package ui.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import ui.custom.CustomButton;

public class OverlayPane extends PopupPane {

	private JPanel contentPane;
	
	private ComponentAdapter resizeListener;
	private MouseAdapter closeListener;
	
	public OverlayPane(JRootPane rootPane, JPanel panel, JPanel trigger) {
		super(rootPane);
		
		contentPane = createContentPane(panel);
		
		resizeListener = createResizeListener();
		closeListener = createCloseListener();

		OverlayPane overlay = this;
		trigger.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (overlay.isHidden()) {
					overlay.show();
				} else {
					overlay.hide();
				}
			}
		});
		
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
	
	public void setTitle(String main, String second) {
		contentPane.add(createTitlePane(main, second), BorderLayout.NORTH);
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
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(200, 200, 200));
		contentPane.setBorder(new CompoundBorder(
				new MatteBorder(2, 0, 0, 0, new Color(255, 160, 0)),
				new MatteBorder(0, 0, 20, 0, new Color(80, 80, 80)))
				);
		
		contentPane.add(createTitlePane("", ""), BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);
		
		return contentPane;
	}
	
	private JPanel createTitlePane(String main, String second) {
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(new CompoundBorder(
				new EmptyBorder(10, 15, 10, 15),
				new MatteBorder(0, 0, 2, 0, new Color(130, 130, 130))));
		container.setBackground(new Color(70, 70, 70));
		
		JPanel closeButtonPane = new JPanel();
		closeButtonPane.setOpaque(false);
		closeButtonPane.add(createCloseButton());

		JPanel titlePane = new JPanel();
		titlePane.setOpaque(false);
		titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel(main);
		Font large = new Font("serif", Font.BOLD, 25);
		title.setForeground(new Color(230, 130, 0));
		title.setFont(large);
		
		JLabel date = new JLabel(second);
		date.setForeground(new Color(200, 200, 200));
		date.setBorder(new EmptyBorder(0, 5, 0, 0));
		
		titlePane.add(title);
		titlePane.add(date);

		container.add(closeButtonPane, BorderLayout.EAST);
		container.add(titlePane, BorderLayout.WEST);
		
		return container;
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
		int width = Math.max(bounds.width / 2, 600);
		int height = Math.max(bounds.height / 2, 400);
		
		setBounds(bounds);
		contentPane.setBounds(
				(bounds.width - width) / 2, (bounds.height - height) / 2,
				width, height);
	}

}
