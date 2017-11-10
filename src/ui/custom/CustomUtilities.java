package ui.custom;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public final class CustomUtilities {
	
	public static int DEFAULT_WIDTH;
	public static int DEFAULT_HEIGHT;
	
	static {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		DEFAULT_WIDTH  = screenSize.width;
		DEFAULT_HEIGHT = screenSize.height;
	}

	public static final double getScaleX(JFrame frame) {
		double frameWidth = frame.getWidth();
		double scale = frameWidth / DEFAULT_WIDTH;
		
		return scale;
	}
	
	public static final double getScaleY(JFrame frame) {
		double frameHeight = frame.getHeight();
		double scale = frameHeight / DEFAULT_HEIGHT;
		
		return scale;
	}
	
	// re-scale width for bigger sized resolutions
	public static final int rescaleX(int width, JFrame frame) {
		double scale = getScaleX(frame);
		
		return (int) (scale > 1 ? scale * width : width);
	}
	
	// re-scale height for bigger sized resolutions
	public static final int rescaleY(int height, JFrame frame) {
		double scale = getScaleY(frame);
		
		return (int) (scale > 1 ? scale * height : height);
	}
	
}
