package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

import calendar.Appointment;
import calendar.AppointmentComparator;
import calendar.EmptySlot;
import mysql.MySQLAccess;
import mysql.query.AppointmentQuery;
import ui.MainFrame;
import ui.popup.ErrorPane;

public class DayPane extends JPanel {
	
	private static final int GAP_BETWEEN_APPOINTMENTS = 2;
	private static final int GAP_BETWEEN_DAYS = 4;
	
	private Date beginDate;
	private Date finishDate;
	
	public DayPane(Calendar calendar) {
		super(new GridLayout(1, 5));
		calendar.set(Calendar.DAY_OF_WEEK, 2);

		initTimelineDates(calendar);

		try {
			addAppointments(calendar);
		} catch (Exception e) {
			e.printStackTrace();
			
			JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
			JPanel glassPane = (JPanel) rootPane.getGlassPane();
			boolean already = false;
			
			for (Component component: glassPane.getComponents()) {
				if (component instanceof ErrorPane) {
					already = true;
					break;
				}
			}

			if (!already) {
				ErrorPane error = new ErrorPane(rootPane,
						"Unable to connect to the database.");
				error.show();
			}
		}

		revalidate();
		repaint();

		setBackground(new Color(120, 120, 120));
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
	private void addAppointments(Calendar calendar) throws Exception {
		MySQLAccess access = new MySQLAccess();
		
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
			
			AppointmentQuery appointmentQuery = new AppointmentQuery(access);
			Appointment[] appointments = appointmentQuery.get(calendar.getTime());
			Arrays.sort(appointments, new AppointmentComparator());
			
			int length = appointments.length;
			
			if (length != 0 && hasEmptySlot(null, appointments[0])) {
				dayContainer.add(new EmptySlotPane(
						new EmptySlot(beginDate, 
								appointments[0].getStartDate())));
				

				// add vertical gap between appointments
				dayContainer.add(Box.createRigidArea(
						new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
			} else {
				dayContainer.add(new EmptySlotPane(
						new EmptySlot(beginDate, finishDate)));
				
			}
			
			for (int j = 0; j < length - 1; j++) {
				dayContainer.add(new AppointmentPane(appointments[j]));
				
				if (length > 1 && hasEmptySlot(appointments[j], appointments[j + 1])) {
					// add vertical gap between appointments
					dayContainer.add(Box.createRigidArea(
							new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
					
					dayContainer.add(new EmptySlotPane(
							new EmptySlot(appointments[j].getEndDate(), 
									appointments[j + 1].getStartDate())));
				}
				
				// add vertical gap between appointments
				dayContainer.add(Box.createRigidArea(
						new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
			}
			
			if (length != 0 && hasEmptySlot(appointments[length - 1], null)) {
				dayContainer.add(new EmptySlotPane(
						new EmptySlot(appointments[length - 1].getEndDate(), 
								finishDate)));
			}
			
			anchorTopContainer.add(dayContainer, BorderLayout.NORTH);
			add(anchorTopContainer);
			
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		access.close();
	}

	private void initTimelineDates(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);
		
		beginDate = calendar.getTime();
		
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 0);
		
		finishDate = calendar.getTime();
	}
	
	private boolean hasEmptySlot(Appointment a1, Appointment a2) {
		Date startDate = null;
		Date endDate = null;
		Calendar calendar = Calendar.getInstance(Locale.UK);
		
		if (a1 == null && a2 == null) {
			endDate = (Date) beginDate.clone();
			startDate = (Date) beginDate.clone();
		} else {
			if (a1 == null) {
				endDate = (Date) beginDate.clone();
			} else {
				endDate = a1.getEndDate();
			}
			
			if (a2 == null) {
				startDate = (Date) finishDate.clone();
			} else {
				startDate = a2.getStartDate();
			}
		}
		
		
		if (endDate.compareTo(startDate) == -1 && 
				startDate.getTime() - endDate.getTime() > 900000) {
			return true;
		}
		
		return false;
	}
	
}
