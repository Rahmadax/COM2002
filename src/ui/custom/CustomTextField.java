package ui.custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CustomTextField extends JTextField{

	private static final Color LIGHT_GRAY = new Color(150, 150, 150);
	private static final Color DARK_GRAY = new Color(70, 70, 70);
	private static final Color GRAY = new Color(100, 100, 100);
	private static final Color FONT_COLOR = new Color(230, 230, 230);
	private static final Color ORANGE = new Color(255, 160, 0);
	
	public CustomTextField(String replacement, int columns) {
		super(replacement, columns);

		setForeground(LIGHT_GRAY);
		setBackground(DARK_GRAY);
		setBorder(new CompoundBorder(
				new LineBorder(LIGHT_GRAY, 1),
				new EmptyBorder(5, 10, 5, 10)));
		
		setFont(new Font(getFont().getFontName(), Font.BOLD, 15));
				
		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setBackground(DARK_GRAY);
				setBorder(new CompoundBorder(
						new LineBorder(LIGHT_GRAY, 1),
						new EmptyBorder(5, 10, 5, 10)));
				
				if (getText().equals("")) {
					setForeground(LIGHT_GRAY);
					setText(replacement);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				setForeground(FONT_COLOR);
				setBackground(GRAY);
				setBorder(new CompoundBorder(
						new LineBorder(ORANGE, 1),
						new EmptyBorder(5, 10, 5, 10)));
				
				if (getText().equals(replacement)) {
					setText("");
				}
			}
		});
	}
	
}
