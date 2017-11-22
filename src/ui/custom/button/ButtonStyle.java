package ui.custom.button;

import java.awt.Color;

class ButtonStyle {

	static abstract class Style {
		
		public Color FONT_COLOR;
		public Color HOVER_COLOR;
		public Color PRESS_COLOR;
		public Color DEFAULT_COLOR;
		public Color BORDER_COLOR;
		
	}
	
	static class DefaultStyle extends Style {
		DefaultStyle() {
			FONT_COLOR    = new Color(255, 160, 0);
			HOVER_COLOR   = new Color(150, 100, 0);
			PRESS_COLOR   = new Color(150, 150, 150);
			DEFAULT_COLOR = new Color(80, 80, 80);
			BORDER_COLOR  = new Color(255, 160, 0);
		}
	}
	
	static class ReversedStyle extends Style {
		ReversedStyle() {
			FONT_COLOR    = new Color(80, 80, 80);
			HOVER_COLOR   = new Color(220, 130, 0);
			PRESS_COLOR   = new Color(150, 150, 150);
			DEFAULT_COLOR = new Color(255, 160, 0);
			BORDER_COLOR  = new Color(80, 80, 80);
		}
	}
	
	static class ErrorStyle extends Style {
		ErrorStyle() {
			FONT_COLOR    = new Color(255, 200, 200);
			HOVER_COLOR   = new Color(100, 0, 0);
			PRESS_COLOR   = new Color(150, 150, 150);
			DEFAULT_COLOR = new Color(210, 0, 0);
			BORDER_COLOR  = new Color(50, 50, 50);
		}
	}
	
}
