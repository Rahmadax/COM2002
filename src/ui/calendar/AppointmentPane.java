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
	
	// overlay pane instance to show the appointment details
	private OverlayPane overlay;
	
	// the appointment details pane
	private AppointmentDetailsPane detailsPane;

	private Appointment appointment;

	public AppointmentPane(Appointment appointment) {
		super(appointment);
		
		this.appointment = appointment;
		detailsPane = new AppointmentDetailsPane(appointment);
		
		addMouseListener(new HoverListener(BACKGROUND_COLOR, HOVER_COLOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JPanel panel = (JPanel) e.getSource();
				JRootPane rootPane = SwingUtilities.getRootPane(panel);

				overlay = new OverlayPane(rootPane, detailsPane, panel);
				overlay.setTitle("Appointment", "12 December 1999");
				overlay.show();
				panel.removeMouseListener(this);
			}
		});
		
		setBackground(BACKGROUND_COLOR);
		setPreferredSize(new Dimension(50, height));
		
		setLayout(new BorderLayout());
		addComponents();
	}
	
	// TO BE CONTINUED
	private void addComponents() {
		add(createTimePane(), BorderLayout.NORTH);
		add(createContentPane(), BorderLayout.CENTER);
	}

	private JPanel createTimePane() {
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:m a");
		String startTime = timeFormatter.format(appointment.getStartDate());
		String endTime = timeFormatter.format(appointment.getEndDate());
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 160, 50));
		
		panel.setLayout(new BorderLayout());
		panel.setBorder(new CompoundBorder(
				new MatteBorder(0, 0, 1, 0, new Color(100, 100, 100)),
				new EmptyBorder(5, 10, 4, 10)));
		
		Font font = new Font("serif", Font.BOLD, 13);

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
		JLabel patientName = new JLabel("John Smith");
		patientName.setFont(new Font("serif", Font.BOLD, 14));
		patientName.setForeground(new Color(80, 50, 0));
		
		return new CenteredPane(patientName);
	}

}
