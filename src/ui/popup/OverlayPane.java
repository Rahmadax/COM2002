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

import ui.custom.button.CustomButton;

public class OverlayPane extends PopupPane {

	public static final int DEFAULT_STYLE = 0;
	public static final int ERROR_STYLE = 1;
	public static final int SUCCESS_STYLE = 2;
	public static final int LOADING_STYLE = 3;
	
	private int minWidth = 600;
	private int minHeignt = 400;
	private double scaleX = 2;
	private double scaleY = 2;
	
	private JPanel mainPane;
	private JPanel contentPane;
	private JPanel titlePane;
	private CustomButton closeButton;
	
	private ComponentAdapter resizeListener;
	private MouseAdapter closeListener;
	private OverlayStyle.Style style;
	
	public OverlayPane(JRootPane rootPane, JPanel contentPane, int style) {
		super(rootPane);

		this.closeButton = new CustomButton("");
		this.style = getStyle(style);
		this.contentPane = contentPane;
		mainPane = createMainPane();
		
		resizeListener = createResizeListener();
		closeListener = createCloseListener();
		
		setLayout(null);
		add(mainPane);

		setBackground(new Color(0, 0, 0, 127));
		setBounds(glass.getBounds());
	}
	
	public OverlayPane(JRootPane rootPane, JPanel contentPane) {
		this(rootPane, contentPane, DEFAULT_STYLE);
	}
	
	public void setCloseButtonVisibility(boolean visible) {
		closeButton.setVisible(visible);
	}
	
	public void setConstraints(int minWidth, int minHeignt, 
			double scaleX, double scaleY) {
		this.minWidth = minWidth;
		this.minHeignt = minHeignt;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	public void setTrigger(JPanel trigger) {
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
	}
	
	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
		this.mainPane.add(contentPane, BorderLayout.CENTER);
		
		mainPane.revalidate();
		mainPane.repaint();
	}

	public void show() {		
		glass.addComponentListener(resizeListener);
		
		if (closeListener != null) {
			glass.addMouseListener(closeListener);			
		}

		recalculateBounds();

		super.show(new Point(0, 0));
	}

	@Override
	public void hide() {
		glass.removeComponentListener(resizeListener);
		
		if (closeListener != null) {
			glass.removeMouseListener(closeListener);			
		}
		
		super.hide();
	}
	
	public void disableTitlePane() {
		mainPane.remove(titlePane);
	}
	
	public void disableOutOfBoundsClose() {
		closeListener = new MouseAdapter() {};
	}
	
	public void setTitle(String main, String second) {
		titlePane = createTitlePane(main, second);
		
		mainPane.add(titlePane, BorderLayout.NORTH);
		
		mainPane.setBorder(new CompoundBorder(
				new MatteBorder(2, 0, 0, 0, style.TOP_BORDER),
				new MatteBorder(0, 0, 20, 0, new Color(80, 80, 80)))
				);
	}
	
	private OverlayStyle.Style getStyle(int styleIndex) {
		OverlayStyle.Style style = null;
		
		switch (styleIndex) {
		case 0:
			style = new OverlayStyle.DefaultStyle();
			break;
		case 1:
			style = new OverlayStyle.ErrorStyle();
			break;
		case 2:
			style = new OverlayStyle.SuccessStyle();
			break;
		case 3:
			style = new OverlayStyle.LoadingStyle();
			break;
		}

		return style;
	}

	private ComponentAdapter createResizeListener() {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				recalculateBounds();
				
				mainPane.revalidate();
				mainPane.repaint();
			}
		};
	}
	
	private MouseAdapter createCloseListener() {		
		return new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Point click = e.getPoint();
				Rectangle bounds = mainPane.getBounds();
				
				if (click.x < bounds.x || click.y < bounds.y || 
						click.x > bounds.x + bounds.width ||
						click.y > bounds.y + bounds.height) {
					hide();
				}
			}
		};
	}
	
	private JPanel createMainPane() {		
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.setBackground(new Color(200, 200, 200));
		mainPane.setBorder(new CompoundBorder(
				new MatteBorder(2, 0, 0, 0, style.TOP_BORDER),
				new MatteBorder(30, 0, 20, 0, new Color(80, 80, 80)))
				);
		
		titlePane = new JPanel();
		
		mainPane.setPreferredSize(new Dimension(minWidth, minHeignt));
		
		mainPane.add(titlePane, BorderLayout.NORTH);
		mainPane.add(contentPane, BorderLayout.CENTER);
		
		return mainPane;
	}
	
	private JPanel createTitlePane(String main, String second) {
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(new CompoundBorder(
				new EmptyBorder(10, 15, 10, 15),
				new CompoundBorder(
						new MatteBorder(0, 0, 2, 0, new Color(130, 130, 130)),
						new EmptyBorder(0, 0, 6, 0))));
		container.setBackground(new Color(70, 70, 70));
		
		JPanel closeButtonPane = new JPanel();
		closeButtonPane.setOpaque(false);
		closeButtonPane.add(createCloseButton());

		JPanel titlePane = new JPanel();
		titlePane.setOpaque(false);
		titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel(main);
		Font large = new Font("serif", Font.BOLD, 25);
		title.setForeground(style.TITLE_COLOR);
		title.setFont(large);
		
		JLabel small = new JLabel(second);
		small.setForeground(new Color(200, 200, 200));
		small.setBorder(new EmptyBorder(0, 5, 0, 0));
		
		titlePane.add(title);
		titlePane.add(small);

		container.add(closeButtonPane, BorderLayout.EAST);
		container.add(titlePane, BorderLayout.WEST);
		
		return container;
	}
	
	private CustomButton createCloseButton() {
		CustomButton closeButton = 
				new CustomButton("x", CustomButton.REVERSED_STYLE);
		closeButton.setPreferredSize(new Dimension(45, 45));
		
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				hide();
			}
		});
		
		this.closeButton = closeButton;
		
		return closeButton;
	}
	
	private void recalculateBounds() {
		Rectangle bounds = glass.getBounds();
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

		mainPane.setBounds(
				(bounds.width - width) / 2, (bounds.height - height) / 2,
				width, height);

		
		setBounds(bounds);
	}

}
