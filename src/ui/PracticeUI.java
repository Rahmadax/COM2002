package ui;

import javax.swing.SwingUtilities;

public class PracticeUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame(ModeUI.PRACTICE);
			}
	    });
	}
	
}
