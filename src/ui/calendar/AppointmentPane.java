package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import calendar.Appointment;
import ui.layout.CenteredPane;
import ui.listener.HoverListener;
import ui.popup.OverlayPane;

public class AppointmentPane extends TimeSlotPane {
	
	private static final Color BACKGROUND_COLOR = new Color(255, 200, 100);
	private static final Color HOVER_COLOR = new Color(255, 180, 80);

	private Appointment appointment;

	public AppointmentPane(Appointment appointment) {
		super(appointment);
		
		this.appointment = appointment;
		
		addMouseListener(new HoverListener(BACKGROUND_COLOR, HOVER_COLOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JPanel panel = (JPanel) e.getSource();
				JRootPane rootPane = SwingUtilities.getRootPane(panel);

				SimpleDateFormat dateFormatter = 
						new SimpleDateFormat("d MMMM yyyy");
				
				OverlayPane overlay = new OverlayPane(rootPane, 
						new JPanel());
				overlay.setContentPane(new AppointmentDetailsPane(appointment, overlay));
				overlay.setTitle("Appointment", 
						dateFormatter.format(appointment.getStartDate()));
				overlay.setConstraints(650, 500, 2, 1.9);
				overlay.show();
			}
		});
		
		setBackground(BACKGROUND_COLOR);
		setPreferredSize(new Dimension(50, height));
		
		setLayout(new BorderLayout());
		addComponents();
	}
	
	private void addComponents() {
		add(createTimePane(), BorderLayout.NORTH);
		add(createContentPane(), BorderLayout.CENTER);
	}

	private JPanel createTimePane() {
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
		String startTime = timeFormatter.format(appointment.getStartDate());
		String endTime = timeFormatter.format(appointment.getEndDate());
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 160, 50));
		
		panel.setLayout(new BorderLayout());
		panel.setBorder(new CompoundBorder(
				new MatteBorder(0, 0, 1, 0, new Color(100, 100, 100)),
				new EmptyBorder(5, 10, 4, 10)));
		
		Font font = new Font("Aller", Font.BOLD, 13);

		JLabel startLabel = new JLabel(startTime);
		JLabel endLabel = new JLabel(endTime);
		startLabel.setForeground(new Color(70, 70, 70));
		startLabel.setFont(font);
		endLabel.setForeground(new Color(70, 70, 70));
		endLabel.setFont(font);

		panel.add(new CenteredPane(startLabel), BorderLayout.WEST);
		panel.add(new CenteredPane(endLabel), BorderLayout.EAST);

		return panel;
	}
	
	private JPanel createContentPane() {
		JLabel patientName = new JLabel(new Integer(appointment.getPatientID()).toString());
		patientName.setFont(new Font("Aller", Font.BOLD, 14));
		patientName.setForeground(new Color(80, 50, 0));
		
		return new CenteredPane(patientName);
	}

}
