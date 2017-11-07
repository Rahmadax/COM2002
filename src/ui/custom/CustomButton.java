package ui.custom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import listener.HoverListener;

public class CustomButton extends JPanel {
	
	public static final int DEFAULT = 0;
	public static final int REVERSED = 1;
	
	private static final Color DARK_GRAY = new Color(80, 80, 80);
	private static final Color LIGHT_GRAY = new Color(150, 150, 150);
	private static final Color DARK_WHITE = new Color(220, 220, 220);
	private static final Color ORANGE = new Color(255, 160, 0);
	private static final Color DARK_ORANGE = new Color(220, 130, 0);
	private static final Color BROWN = new Color(150, 100, 0);
	
	public CustomButton(String str, int style) {
		super();
		
		switch (style) {
		case DEFAULT:
			setDefaultStyle(str);
			break;
		case REVERSED:
			setReversedStyle(str);
			break;
		}
	}
	
	public CustomButton(String str) {
		super();
		
		setDefaultStyle(str);
	}

	private void setDefaultStyle(String str) {
		JLabel label = new JLabel(str);
		label.setFont(new Font("Serif", Font.BOLD, 20));
		label.setForeground(ORANGE);
		add(label);
		
		addMouseListener(new HoverListener(DARK_GRAY, BROWN));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(LIGHT_GRAY);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(DARK_GRAY);
			}
		});
		
		setPreferredSize(new Dimension(50, 40));
		setBackground(DARK_GRAY);
		setBorder(new LineBorder(ORANGE, 1));
	}
	
	private void setReversedStyle(String str) {
		JLabel label = new JLabel(str);
		label.setFont(new Font("Serif", Font.BOLD, 20));
		label.setForeground(DARK_GRAY);
		add(label);
		
		addMouseListener(new HoverListener(ORANGE, DARK_ORANGE));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(LIGHT_GRAY);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(ORANGE);
			}
		});
		
		setPreferredSize(new Dimension(50, 40));
		setBackground(ORANGE);
		setBorder(new LineBorder(DARK_GRAY, 1));
	}
	
}
