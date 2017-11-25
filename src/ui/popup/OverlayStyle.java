package ui.popup;

import java.awt.Color;

public class OverlayStyle {
	
	public static abstract class Style {
		public Color TOP_BORDER;
		public Color TITLE_COLOR;
	}
	
	public static class DefaultStyle extends Style {
		public DefaultStyle() {
			TOP_BORDER = new Color(255, 160, 0);
			TITLE_COLOR = new Color(255, 160, 0);
		}
	}
	
	public static class ErrorStyle extends Style {
		public ErrorStyle() {
			TOP_BORDER = Color.RED;
			TITLE_COLOR = Color.RED;
		}
	}
	
	public static class SuccessStyle extends Style {
		public SuccessStyle() {
			TOP_BORDER = new Color(0, 200, 0);
			TITLE_COLOR = new Color(0, 200, 0);;
		}
	}
	
	public static class LoadingStyle extends Style {
		public LoadingStyle() {
			TOP_BORDER = Color.WHITE;
			TITLE_COLOR = Color.WHITE;
		}
	}
	
}
