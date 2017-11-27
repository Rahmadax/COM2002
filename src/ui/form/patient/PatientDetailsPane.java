package ui.form.patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import mysql.MySQLAccess;
import mysql.query.AppointmentQuery;
import mysql.query.TreatmentApp_LinkerQuery;
import mysql.query.TreatmentQuery;
import mysql.query.TreatmentsStoreQuery;
import ui.MainFrame;
import ui.custom.button.CustomButton;
import ui.custom.tabbedpane.CustomTabbedPane;
import ui.listener.HoverListener;
import ui.popup.OverlayContentPane;
import ui.popup.OverlayPane;

public class PatientDetailsPane extends OverlayContentPane {
	
	private HashMap<String, String> generalData;

	public PatientDetailsPane(HashMap<String, String> generalData, OverlayPane overlay) {
		super(overlay);
		this.generalData = generalData;
		
		setLayout(new BorderLayout());
		setBackground(new Color(200, 200, 200));
		
		addComponents();
	}
	
	private void addComponents() {
		add(createTabbedPane());
	}
	
	private CustomTabbedPane createTabbedPane() {
		CustomTabbedPane tabbedPane = new CustomTabbedPane();
		tabbedPane.addTab("General", createGeneralPane());
		tabbedPane.addTab("Appointments", createAppointmentsPane());
		tabbedPane.addTab("Costs", createCostsPane());
		
		return tabbedPane;
	}
	
	private JPanel createGeneralPane() {
		JPanel generalPane = new JPanel();
		generalPane.setOpaque(false);
		
		return generalPane;
	}
	
	private JPanel createAppointmentsPane() {
		class Title extends JPanel {
			Title(String title) {
				super();
				setBackground(new Color(200, 200, 200));
				
				JLabel label = new JLabel(title);
				label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 16));
				label.setForeground(new Color(30, 30, 30));
				
				add(label);
				setBorder(new MatteBorder(0, 0, 2, 0, new Color(150, 150, 150)));
			}
		}
		
		JPanel appointmentsPane = new JPanel();
		appointmentsPane.setOpaque(false);
		appointmentsPane.setLayout(new BoxLayout(appointmentsPane, BoxLayout.Y_AXIS));
		
		MySQLAccess access = null;
		try {
			access = new MySQLAccess();
			AppointmentQuery appQuery = new AppointmentQuery(access);
			String[][] appointments = appQuery.get(Integer.parseInt(generalData.get("PatientID")));
			
			Calendar calendar = Calendar.getInstance(Locale.UK);
			
			appointmentsPane.add(new Title("Upcoming appointments"));
			
			boolean history = false;
			boolean used = false;
			
			for (String[] appointment: appointments) {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(appointment[0]);
				
				if (date.compareTo(calendar.getTime()) < 0) {
					history = true;
				}
				
				if (history && !used) {
					used = true;
					appointmentsPane.add(new Title("History of appointments"));
				}

				appointmentsPane.add(new AppointmentRowPane(appointment));
				
				TreatmentApp_LinkerQuery q1 = new TreatmentApp_LinkerQuery(access);
				TreatmentQuery q2 = new TreatmentQuery(access);
				TreatmentsStoreQuery q3 = new TreatmentsStoreQuery(access);
				
				String[][] ts = q3.getAll(q2.getTreatmentName(q1.getIDs(appointment[0], appointment[1], appointment[2])));

				for (String[] t: ts) {
					appointmentsPane.add(new TreatmentRowPane(t));
				}
			}
			
			access.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			if (access != null) {
				try {
					access.close();
				} catch (SQLException e1) {
					// empty catch
				}
			}
		}
		
		JPanel anchorTopContainer = new JPanel(new BorderLayout());
		anchorTopContainer.setBackground(new Color(90, 90, 90));
		anchorTopContainer.add(appointmentsPane, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(anchorTopContainer);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);;
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		
		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.add(scrollPane);
		container.add(createPayButton(), BorderLayout.SOUTH);
		
		return container;
	}
	
	private JPanel createPayButton() {
		CustomButton payButton = new CustomButton("Pay", CustomButton.REVERSED_STYLE);
		payButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
				OverlayPane overlay = new OverlayPane(rootPane, new JPanel());
				Receipt receipt = new Receipt(overlay, Integer.parseInt(generalData.get("PatientID")));
				overlay.setContentPane(receipt);
				overlay.setConstraints(800, 600, 2, 2);
				overlay.setTitle("Receipt", "Date: " + new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date()));
				
				getOverlay().hide();
				overlay.show();
			}
		});
		
		
		JPanel container = new JPanel();
		container.setBackground(new Color(60, 60, 60));
		
		container.add(payButton);
		
		return container;
	}

	private JPanel createCostsPane() {
		JPanel costsPane = new JPanel();
		costsPane.setOpaque(false);
		
		return costsPane;
	}
	
	private class AppointmentRowPane extends JPanel {
				
		public AppointmentRowPane(String[] data) {
			super(new GridLayout(1, 3));
			
			setBorder(new CompoundBorder(
					new CompoundBorder(
							new MatteBorder(10, 20, 5, 10, new Color(90, 90, 90)),
							new LineBorder(new Color(150, 150, 150), 1)),
					new EmptyBorder(10, 20, 10, 20)));
			setBackground(new Color(60, 60, 60));
			
			addMouseListener(new HoverListener(new Color(60, 60, 60), 
					new Color(120, 120, 120)));
			
			addData(data);
		}
		
		private void addData(String[] data) {			
			for (int i = 0; i < data.length - 1; i++) {
				JLabel label = null;
				label = new JLabel(data[i]);
				
				label.setForeground(new Color(255, 160, 0));
				label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 15));
				
				add(label);
			}
		}
		
	}
	
	private class TreatmentRowPane extends JPanel {
		
		public TreatmentRowPane(String[] data) {
			super(new GridLayout(1, 2));
			
			setBorder(new CompoundBorder(
					new CompoundBorder(
							new MatteBorder(5, 100, 5, 10, new Color(90, 90, 90)),
							new LineBorder(new Color(150, 150, 150), 1)),
					new EmptyBorder(5, 10, 5, 10)));
			setBackground(new Color(70, 70, 70));
			
			addData(data);
		}
		
		private void addData(String[] data) {			
			add(setLabel(data[0]));
			
			JLabel label = setLabel(data[1]);
			label.setForeground(new Color(220, 220, 220));
			
			JPanel container = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
			container.setOpaque(false);
			container.add(label);
			
			add(container);
		}
		
		private JLabel setLabel(String str) {
			JLabel label = new JLabel(str);
			
			label.setForeground(new Color(255, 160, 0));
			label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 15));
			
			return label;
		}
		
	}

}
