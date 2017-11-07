package ui.calendar;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import calendar.Appointment;
import listener.HoverListener;
import ui.OverlayPane;
import ui.animation.FadeAnimation;
import ui.layout.CenteredPane;

public class AppointmentPane extends TimeSlotPane {
	
	// overlay pane instance to show the appointment details
	private OverlayPane overlay;
	
	// the appointment details pane
	private AppointmentDetailsPane detailsPane;

	private Appointment appointment;

	public AppointmentPane(Appointment appointment) {
		super(appointment);
		
		this.appointment = appointment;
		
		JPanel panel = this;
		detailsPane = new AppointmentDetailsPane();
		
		addMouseListener(new MouseAdapter() {
			// click listener to show the details pane overlay
			@Override
			public void mouseReleased(MouseEvent e) {
				if (overlay == null) {
					JRootPane rootPane = SwingUtilities.getRootPane(panel);
					overlay = new OverlayPane(rootPane, detailsPane);
					overlay.setTitlePane(detailsPane.getTitlePane());
				}

				if (overlay.isHidden()) {
					overlay.show();
				} else {
					overlay.hide();
				}
			}
		});
		
		setLayout(new BorderLayout());
		
		addComponents();
				
		addMouseListener(new HoverListener(
				new Color(255, 200, 200), new Color(255, 170, 170)));
		setBackground(new Color(255, 200, 200));
		setPreferredSize(new Dimension(50, height));
	}
	
	// TO BE CONTINUED
	private void addComponents() {
		add(createTimePane(), BorderLayout.CENTER);
	}

	private JPanel createTimePane() {
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:m a");
		String startTime = timeFormatter.format(appointment.getStartDate());
		String endTime = timeFormatter.format(appointment.getEndDate());
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		Font font = new Font("serif", Font.BOLD, 12);

		JLabel startLabel = new JLabel(startTime);
		JLabel endLabel = new JLabel(endTime);
		startLabel.setForeground(new Color(255, 0, 0));
		startLabel.setFont(font);
		endLabel.setFont(font);

		panel.add(new CenteredPane(startLabel), BorderLayout.WEST);
		panel.add(new CenteredPane(endLabel), BorderLayout.EAST);

		return panel;
	}

}
