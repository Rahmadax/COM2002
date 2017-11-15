package ui.calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

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
		
		setBorder(new CompoundBorder(
				new LineBorder(new Color(120, 120, 120), 2),
				new EmptyBorder(10, 20, 15, 0)));
		setBackground(new Color(70, 70, 70));
	}
	
	// pane class that shows the specified day of the week and date
	private class DayLabel extends JPanel {
		DayLabel(String day, Date date) {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setOpaque(false);
			
			if (day != "Friday") {
				setBorder(BorderFactory.createCompoundBorder(
						new EmptyBorder(0, 0, 0, 15),
						new MatteBorder(0, 0, 0, 3, new Color(120, 120, 120))));
			}
			
			JLabel dayLabel = new JLabel(day);
			dayLabel.setFont(new Font("Aller", Font.BOLD, 20));
			dayLabel.setForeground(new Color(255, 140, 0));
			
			SimpleDateFormat dateFormatter = new SimpleDateFormat("d MMMM yyyy");
			JLabel dateLabel = new JLabel(dateFormatter.format(date));
			dateLabel.setFont(new Font("Aller", Font.BOLD, 13));
			dateLabel.setForeground(new Color(200, 200, 200));

			add(dayLabel);			
			add(dateLabel);
		}
	}
	
}
