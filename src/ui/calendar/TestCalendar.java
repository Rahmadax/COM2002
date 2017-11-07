package ui.calendar;

import java.awt.Dimension;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import ui.animation.FadeAnimation;

public class TestCalendar {

	public static void main(String[] args) {
		// main window rootPane
	    JFrame frame = new JFrame("Calendar");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(true);
	    
	    JRootPane rootPane = new JRootPane();
	    rootPane.setContentPane(new CalendarPane(Calendar.getInstance(Locale.UK)));
	    
	    // glass pane for pop-ups
	    JPanel glass = (JPanel) rootPane.getGlassPane();
	    glass.setLayout(null);
	    glass.setVisible(true);
	    
	    // add the calendar pane with the current date
	    frame.add(rootPane);

	    frame.setMinimumSize(new Dimension(800, 600));
	    frame.setVisible(true);
	}

}
