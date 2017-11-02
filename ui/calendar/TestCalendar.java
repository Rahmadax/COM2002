package ui.calendar;

import java.awt.Dimension;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestCalendar {

	public static void main(String[] args) {
		// main window frame
	    JFrame frame = new JFrame("Calendar");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(true);

	    // glass pane for pop-ups
	    JPanel glass = (JPanel) frame.getGlassPane();
	    glass.setLayout(null);
	    glass.setVisible(true);
	    
	    // add the calendar pane with the current date
	    frame.add(new CalendarPane(Calendar.getInstance(Locale.UK)));

	    frame.setMinimumSize(new Dimension(800, 600));
	    frame.setVisible(true);
	}

}
