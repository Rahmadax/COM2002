package ui.calendar;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DayLabelPane extends JPanel {

	public DayLabelPane(Calendar calendar) {
		super();

		// move calendar to the first day of week (Monday)
		calendar.set(Calendar.DAY_OF_WEEK, 2);

		setLayout(new GridLayout(1, 5));
		
		// add day label for all week days
		String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		for (String day: days) {
			add(new DayLabel(day, calendar.getTime()));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		setBorder(new EmptyBorder(0, 10, 0, 10));
	}
	
	// pane class that shows the specified day of the week and date
	private class DayLabel extends JPanel {
		DayLabel(String str, Date date) {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JLabel label = new JLabel(str);
			label.setFont(new Font("Serif", Font.BOLD, 20));

			SimpleDateFormat dateFormatter = new SimpleDateFormat("d MMMM yyyy");
			
			add(label);			
			add(new JLabel(dateFormatter.format(date)));
		}
	}
	
}
