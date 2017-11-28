package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import calendar.Appointment;
import mysql.MySQLAccess;
import mysql.query.PatientQuery;
import mysql.query.TreatmentApp_LinkerQuery;
import ui.MainFrame;
import ui.layout.CenteredPane;
import ui.listener.HoverListener;
import ui.popup.ErrorPane;
import ui.popup.LoadingPane;
import ui.popup.OverlayPane;

public class AppointmentPane extends TimeSlotPane {
	
	private static final Color BACKGROUND_COLOR = new Color(255, 200, 100);
	private static final Color HOVER_COLOR = new Color(255, 180, 80);

	private Appointment appointment;

	public AppointmentPane(Appointment appointment) throws Exception {
		super(appointment);
		
		this.appointment = appointment;
		
		addMouseListener(new HoverListener(BACKGROUND_COLOR, HOVER_COLOR));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();

				SimpleDateFormat dateFormatter = 
						new SimpleDateFormat("d MMMM yyyy");
				
				OverlayPane overlay = new OverlayPane(rootPane, 
						new JPanel());
				
				LoadingPane loading = new LoadingPane(rootPane);
				loading.show();
                
            	new Thread() {
					@Override
					public void run() {
						try {
    						overlay.setContentPane(new AppointmentDetailsPane(appointment, overlay));
		                    
    						String title = "Appointment";
    						if (!appointment.isPaid()) {
    							title += " (Not paid)";
    						}
    						
    						overlay.setTitle(title,
		    						dateFormatter.format(appointment.getStartDate()));
		    				overlay.setConstraints(700, 550, 2, 1.9);
		    				overlay.show();
						} catch (Exception e1) {
		                    e1.printStackTrace();
		                    
		                    new ErrorPane(rootPane, "Unable to view the appointment.").show();
		                }
						
	    				loading.hide();
					}
				}.start();
                
			}
		});
		
		setBackground(BACKGROUND_COLOR);
		setPreferredSize(new Dimension(50, height));
		
		setLayout(new BorderLayout());
		addComponents();
		
		MySQLAccess access = new MySQLAccess();
		TreatmentApp_LinkerQuery q1 = new TreatmentApp_LinkerQuery(access);
		
		if (q1.getIDs(appointment).length != 0) {
			MouseListener[] ls = getMouseListeners();
			
			for (MouseListener l: ls) {
				if (l instanceof HoverListener) {
					removeMouseListener(l);
				}
			}
			
			addMouseListener(
					new HoverListener(new Color(200, 200, 250), new Color(180, 180, 230)));
			
			setBackground(new Color(200, 200, 250));
		}
		
		if (appointment.isPaid()) {
			MouseListener[] ls = getMouseListeners();
			
			for (MouseListener l: ls) {
				if (l instanceof HoverListener) {
					removeMouseListener(l);
				}
			}
			
			addMouseListener(
					new HoverListener(new Color(200, 200, 200), new Color(170, 170, 170)));
			
			setBackground(new Color(200, 200, 200));
		}
		
		access.close();
	}
	
	private void addComponents() throws Exception {
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
	
	private JPanel createContentPane() throws Exception {
        MySQLAccess access = new MySQLAccess();
        PatientQuery q = new PatientQuery(access);
		JLabel patientName = new JLabel(q.getFullName(appointment.getPatientID()));
		
		patientName.setFont(new Font("Aller", Font.BOLD, 14));
		patientName.setForeground(new Color(80, 50, 0));
		
		access.close();
		
		return new CenteredPane(patientName);
	}

}
