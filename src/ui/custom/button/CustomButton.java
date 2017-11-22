package ui.custom.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ui.custom.button.ButtonStyle.DefaultStyle;
import ui.custom.button.ButtonStyle.Style;
import ui.listener.HoverListener;

public class CustomButton extends JPanel {
	
	public static final int DEFAULT_STYLE = 0;
	public static final int REVERSED_STYLE = 1;
	public static final int ERROR_STYLE = 2;
	
	private Style style;
	
	public CustomButton(String str, int style) {
		super();
		setStyle(style);
		initButton(str);
	}
	
	public CustomButton(String str) {
		this(str, DEFAULT_STYLE);
	}

	private void initButton(String str) {
		JLabel label = new JLabel(str);
		label.setFont(new Font("Serif", Font.BOLD, 20));
		label.setForeground(style.FONT_COLOR);
		add(label);
		
		addMouseListener(new HoverListener(style.DEFAULT_COLOR, style.HOVER_COLOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(style.PRESS_COLOR);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(style.DEFAULT_COLOR);
			}
		});
		
		setFocusable(true);
		setBackground(style.DEFAULT_COLOR);
		setBorder(new CompoundBorder(
				new LineBorder(style.BORDER_COLOR, 1),
				new EmptyBorder(0, 15, 0, 15)));
	}
	
	public void setStyle(int style) {
		switch (style) {
		case DEFAULT_STYLE:
			this.style = new ButtonStyle.DefaultStyle();
			break;
		case REVERSED_STYLE:
			this.style = new ButtonStyle.ReversedStyle();
			break;
		case ERROR_STYLE:
			this.style = new ButtonStyle.ErrorStyle();
		}
	}
	
}
