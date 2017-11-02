package ui;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class PopupPane extends JPanel {

	protected JFrame frame;
	protected JPanel glass;
	protected boolean hidden;

	// pop-up pane abstract class that is shown over all other elements
	// at a specific location and frame
	protected PopupPane(JFrame frame) {
		this.frame = frame;
		glass = (JPanel) frame.getGlassPane();
		hidden = true;
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public boolean isHidden() {
		return hidden;
	}

	// show the pop-up at a specific location
	public void show(Point location) {
		setLocation(location);
		glass.add(this);
		
		hidden = false;
		
		glass.revalidate();
		glass.repaint();
	}

	// hide pop-up from view
	public void hide() {
		glass.remove(this);
		
		hidden = true;
		
		glass.revalidate();
		glass.repaint();
	}

}
