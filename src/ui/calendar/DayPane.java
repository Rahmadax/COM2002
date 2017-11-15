package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import calendar.Appointment;
import calendar.EmptySlot;

public class DayPane extends JPanel {
	
	private static final int GAP_BETWEEN_APPOINTMENTS = 2;
	private static final int GAP_BETWEEN_DAYS = 4;
	
	private Calendar calendar;

	public DayPane(Calendar calendar) {
		super();
		
		this.calendar = calendar;

		setLayout(new GridLayout(1, 5));
		
		// add day panes for all week days
		for (int i = 0; i < 5; i++) {
			// anchor pane for top alignment
			JPanel anchorTopContainer = new JPanel();
			anchorTopContainer.setLayout(new BorderLayout());
			anchorTopContainer.setOpaque(false);
			
			JPanel dayContainer = new JPanel();
			dayContainer.setLayout(new BoxLayout(dayContainer, BoxLayout.Y_AXIS));
			dayContainer.setOpaque(false);
			
			// add horizontal gap between appointments
			dayContainer.setBorder(
					new EmptyBorder(0, GAP_BETWEEN_DAYS / 2, 0, GAP_BETWEEN_DAYS / 2));
			
			// add random appointment panes (just for presentation)
			// TO BE CONTINUED
			for (int j = 0; j < 10; j++) {
				int randomNum = ThreadLocalRandom.current().nextInt(1, 1 + 1);
				Date startDate = calendar.getTime();
				calendar.add(Calendar.HOUR_OF_DAY, randomNum);
				Date endDate = calendar.getTime();
				
				Appointment apponitment = new Appointment(startDate, endDate, 
						null, 0);
				
				TimeSlotPane[] arr = new TimeSlotPane[2];
				arr[0] = new EmptySlotPane(new EmptySlot(startDate, endDate));
				arr[1] = new AppointmentPane(apponitment);
				
				int randomNum2 = ThreadLocalRandom.current().nextInt(0, 1 + 1);
				
				dayContainer.add(arr[randomNum2]);
				
				// add vertical gap between appointments
				dayContainer.add(Box.createRigidArea(
						new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
			}
			
			anchorTopContainer.add(dayContainer, BorderLayout.NORTH);
			add(anchorTopContainer);
		}

		setBackground(new Color(120, 120, 120));
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
}
