package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import calendar.Appointment;
import calendar.AppointmentComparator;
import calendar.EmptySlot;
import mysql.MySQLAccess;
import mysql.query.AppointmentQuery;
import ui.MainFrame;

public class DayPane extends JPanel {
	
	private static final int GAP_BETWEEN_APPOINTMENTS = 2;
	private static final int GAP_BETWEEN_DAYS = 4;

	public DayPane(Calendar calendar) throws Exception {
		super();
		calendar.set(Calendar.DAY_OF_WEEK, 2);

		setLayout(new GridLayout(1, 5));

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

				MySQLAccess access = new MySQLAccess();
				AppointmentQuery appointmentQuery = new AppointmentQuery(access);
				Appointment[] appointments = appointmentQuery.get(calendar.getTime());
				Arrays.sort(appointments, new AppointmentComparator());
				access.close();
				
				for (int j = 0; j < appointments.length; j++) {				
					dayContainer.add(new AppointmentPane(appointments[j]));
					
					// add vertical gap between appointments
					dayContainer.add(Box.createRigidArea(
							new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
				}
				
				anchorTopContainer.add(dayContainer, BorderLayout.NORTH);
				add(anchorTopContainer);
				
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		revalidate();
		repaint();

		setBackground(new Color(120, 120, 120));
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
}
