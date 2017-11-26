package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

import calendar.Appointment;
import calendar.AppointmentComparator;
import calendar.EmptySlot;
import mysql.MySQLAccess;
import mysql.query.AppointmentQuery;
import ui.MainFrame;
import ui.ModeUI;
import ui.popup.ErrorPane;

public class DayPane extends JPanel {
	
	private static final int GAP_BETWEEN_APPOINTMENTS = 2;
	private static final int GAP_BETWEEN_DAYS = 4;
	
	private Date beginDate;
	private Date finishDate;
	private String partner;
	
	public DayPane(Calendar calendar, String partner) {
		super(new GridLayout(1, 5));
		this.partner = partner;
		
		calendar.set(Calendar.DAY_OF_WEEK, 2);

		try {
			addAppointments(calendar);
		} catch (Exception e) {
			e.printStackTrace();
			
			JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
			JLayeredPane glassPane = (JLayeredPane) rootPane.getGlassPane();
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
		
		try {
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
				ArrayList<Appointment> selectedApps = new ArrayList<Appointment>();
							
				for (Appointment appointment: appointments) {				
					if (appointment.getPartner().equals(partner)) {
						selectedApps.add(appointment);
					}
				}
							
				int length = selectedApps.size();
				
				if (length > 0) {
					initTimelineDates(selectedApps.get(0).getStartDate());
					
					if (MainFrame.mode == ModeUI.SECRETARY && hasEmptySlot(null, selectedApps.get(0))) {
						dayContainer.add(new EmptySlotPane(
								new EmptySlot(beginDate, selectedApps.get(0).getStartDate(), partner)));
						// add vertical gap between appointments
						dayContainer.add(Box.createRigidArea(
								new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
					}
					
					dayContainer.add(new AppointmentPane(selectedApps.get(0)));
					
					// add vertical gap between appointments
					dayContainer.add(Box.createRigidArea(
							new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
				}
				
				for (int j = 1; j < length - 1; j++) {
					if (j == 1) {
						initTimelineDates(selectedApps.get(0).getStartDate());
						
						if (MainFrame.mode == ModeUI.SECRETARY && hasEmptySlot(selectedApps.get(0), selectedApps.get(1))) {
							dayContainer.add(new EmptySlotPane(new EmptySlot(
									selectedApps.get(0).getEndDate(), selectedApps.get(1).getStartDate(),
									partner)));
							// add vertical gap between appointments
							dayContainer.add(Box.createRigidArea(
									new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
						}
					}
					
					dayContainer.add(new AppointmentPane(selectedApps.get(j)));
					
					// add vertical gap between appointments
					dayContainer.add(Box.createRigidArea(
							new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
					
					initTimelineDates(selectedApps.get(0).getStartDate());
					
					if (MainFrame.mode == ModeUI.SECRETARY && hasEmptySlot(selectedApps.get(j), selectedApps.get(j + 1))) {
						dayContainer.add(new EmptySlotPane(new EmptySlot(
								selectedApps.get(j).getEndDate(), selectedApps.get(j + 1).getStartDate(),
								partner)));
						// add vertical gap between appointments
						dayContainer.add(Box.createRigidArea(
								new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
					}
				}
				
				if (MainFrame.mode == ModeUI.SECRETARY && length == 0) {
					initTimelineDates(calendar.getTime());
					
					dayContainer.add(new EmptySlotPane(new EmptySlot(beginDate, finishDate,partner)));
				}
				
				if (length == 2) {
					initTimelineDates(selectedApps.get(0).getStartDate());
					
					if (MainFrame.mode == ModeUI.SECRETARY && hasEmptySlot(selectedApps.get(0), selectedApps.get(1))) {
						dayContainer.add(new EmptySlotPane(new EmptySlot(
								selectedApps.get(0).getEndDate(), selectedApps.get(1).getStartDate(),
								partner)));
						// add vertical gap between appointments
						dayContainer.add(Box.createRigidArea(
								new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
					}
				}
				
				if (length > 0) {
					if (length != 1) {
						dayContainer.add(new AppointmentPane(selectedApps.get(length - 1)));
					}
				
					// add vertical gap between appointments
					dayContainer.add(Box.createRigidArea(
							new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
					
					initTimelineDates(selectedApps.get(0).getStartDate());
					
					if (MainFrame.mode == ModeUI.SECRETARY && hasEmptySlot(selectedApps.get(length - 1), null)) {
						dayContainer.add(new EmptySlotPane(new EmptySlot(
								selectedApps.get(length - 1).getEndDate(), finishDate, partner)));
						// add vertical gap between appointments
						dayContainer.add(Box.createRigidArea(
								new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
					}
				}
				
				anchorTopContainer.add(dayContainer, BorderLayout.NORTH);
				add(anchorTopContainer);
				
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (Exception e) {
			access.close();
			throw e;
		}
				
		access.close();
	}

	private void initTimelineDates(Date date) {
		Calendar calendar = Calendar.getInstance(Locale.UK);
		calendar.setTime(date);
		
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		beginDate = calendar.getTime();
				
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		finishDate = calendar.getTime();
	}
	
	private boolean hasEmptySlot(Appointment a1, Appointment a2) {
		Date startDate = null;
		Date endDate = null;

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
		
		long gap = startDate.getTime() - endDate.getTime();
		
		if (endDate.compareTo(startDate) < 0 && gap > 600000) {
			return true;
		}
		
		return false;
	}
	
}
