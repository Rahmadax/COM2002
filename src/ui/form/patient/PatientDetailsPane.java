package ui.form.patient;

import java.awt.BorderLayout;
import java.awt.Color;
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
import ui.MainFrame;
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
			AppointmentQuery q = new AppointmentQuery(access);
			String[][] data = q.get(Integer.parseInt(generalData.get("PatientID")));
			
			Calendar calendar = Calendar.getInstance(Locale.UK);
			
			appointmentsPane.add(new Title("Upcoming appointments"));
			
			boolean history = false;
			boolean used = false;
			
			for (String[] strs: data) {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strs[0]);
				
				if (date.compareTo(calendar.getTime()) < 0) {
					history = true;
				}
				
				if (history && !used) {
					used = true;
					appointmentsPane.add(new Title("History of appointments"));
				}

				appointmentsPane.add(new AppointmentRowPane(strs));
			}
			
			access.close();
		} catch (Exception e) {
			if (access != null) {
				try {
					access.close();
				} catch (SQLException e1) {
					// empty catch
				}
			}
		}
		
		JPanel anchorTopContainer = new JPanel(new BorderLayout());
		anchorTopContainer.setBorder(new LineBorder(new Color(200, 200, 200), 1));
		anchorTopContainer.setBackground(new Color(90, 90, 90));
		anchorTopContainer.add(appointmentsPane, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(anchorTopContainer);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);;
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setOpaque(false);
		
		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.add(scrollPane);
		
		return container;
	}
	
	private JPanel createCostsPane() {
		JPanel costsPane = new JPanel();
		costsPane.setOpaque(false);
		
		return costsPane;
	}
	
	private class AppointmentRowPane extends JPanel {
				
		public AppointmentRowPane(String[] data) {
			super(new GridLayout(1, 4));
			
			setBorder(new CompoundBorder(
					new CompoundBorder(
							new MatteBorder(5, 10, 5, 10, new Color(90, 90, 90)),
							new LineBorder(new Color(150, 150, 150), 1)),
					new EmptyBorder(5, 10, 5, 10)));
			setBackground(new Color(60, 60, 60));
			
			addMouseListener(new HoverListener(new Color(60, 60, 60), 
					new Color(120, 120, 120)));
			
			addData(data);
		}
		
		private void addData(String[] data) {			
			for (int i = 0; i < data.length; i++) {
				JLabel label = null;
				label = new JLabel(data[i]);
				
				label.setForeground(new Color(255, 160, 0));
				label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 15));
				
				add(label);
			}
		}
		
	}

}
