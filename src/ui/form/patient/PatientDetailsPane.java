package ui.form.patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import mysql.query.AddressQuery;
import mysql.query.AppointmentQuery;
import mysql.query.HCPPatientLinkerQuery;
import mysql.query.HCPStoreQuery;
import mysql.query.HCPsQuery;
import mysql.query.TreatmentApp_LinkerQuery;
import mysql.query.TreatmentQuery;
import mysql.query.TreatmentsStoreQuery;
import sun.java2d.loops.CustomComponent;
import ui.MainFrame;
import ui.custom.CustomComboBox;
import ui.custom.CustomRowPane;
import ui.custom.button.CustomButton;
import ui.custom.tabbedpane.CustomTabbedPane;
import ui.listener.HoverListener;
import ui.popup.DialogPane;
import ui.popup.ErrorPane;
import ui.popup.LoadingPane;
import ui.popup.OverlayContentPane;
import ui.popup.OverlayPane;
import ui.popup.SuccessPane;

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
		
		return tabbedPane;
	}
	
	private JPanel createGeneralPane() {
		JPanel generalPane = new JPanel(new BorderLayout());
		generalPane.setBackground(new Color(90, 90, 90));
		
		JPanel container = new JPanel(new GridLayout(0, 1));
		container.setOpaque(false);
		
		String hcpName = null;
		try {
			MySQLAccess access = new MySQLAccess();
			AddressQuery q1 = new AddressQuery(access);
			HCPPatientLinkerQuery q2 = new HCPPatientLinkerQuery(access);
			HCPsQuery q3 = new HCPsQuery(access);
			
			String[] address = q1.get(Integer.parseInt(generalData.get("PatientID")));
			int[] hcp = q2.getHCPDetails(Integer.parseInt(generalData.get("PatientID")));
			hcpName = q3.getName(Integer.parseInt(generalData.get("PatientID")));
			
			container.add(new DataRowPane("House no.", address[0]));
			container.add(new DataRowPane("Street name", address[2]));
			container.add(new DataRowPane("District name", address[3]));
			container.add(new DataRowPane("City", address[4]));
			container.add(new DataRowPane("Postcode", address[1]));
			
			if (hcpName != null) {
				container.add(new DataRowPane("Dental Plan", hcpName));
				container.add(new DataRowPane("Remaining Checkups", Integer.toString(hcp[1])));
				container.add(new DataRowPane("Remaining Hygiene", Integer.toString(hcp[2])));
				container.add(new DataRowPane("Remaining Repairs", Integer.toString(hcp[3])));
			}
			
			access.close();
		} catch (Exception e) {
			e.printStackTrace();
			getOverlay().hide();
			JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
			new ErrorPane(rootPane, "Unable to access the database.").show();
		}
		
		JPanel buttonPane = new JPanel();
		buttonPane.setOpaque(false);
		
		try {
			MySQLAccess access = new MySQLAccess();
			HCPStoreQuery q = new HCPStoreQuery(access);
			String[] strs = q.getAll();
			
			if (hcpName == null) {
				CustomComboBox plansComboBox = new CustomComboBox(strs);
				buttonPane.add(plansComboBox);
				
				CustomButton subscribe = new CustomButton("Subscribe", CustomButton.REVERSED_STYLE);
				subscribe.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
						
						try {
							MySQLAccess access = new MySQLAccess();
							HCPsQuery q = new HCPsQuery(access);
							
							Pattern p = Pattern.compile("^(.*)\\s\\(.*\\)$");
							Matcher m = p.matcher((String) plansComboBox.getSelectedItem());
							
							m.find();
							q.addHCP(m.group(1), Integer.parseInt(generalData.get("PatientID")));
						
							getOverlay().hide();
							new SuccessPane(rootPane, "Now the patient is subscribet to a plan.").show();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				buttonPane.add(subscribe);
			} else {
				CustomButton unsubscribe = new CustomButton("Unsubscribe", CustomButton.REVERSED_STYLE);
				unsubscribe.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
						
						try {
							MySQLAccess access = new MySQLAccess();
							HCPsQuery q = new HCPsQuery(access);
							
							q.removeHCP(Integer.parseInt(generalData.get("PatientID")));
						
							getOverlay().hide();
							new SuccessPane(rootPane, "Now the patient is unsubscribed to a plan.").show();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				buttonPane.add(unsubscribe);
			}	
			
			access.close();
		} catch (Exception e) {
			getOverlay().hide();
			e.printStackTrace();
		}
		
		generalPane.add(container);
		generalPane.add(buttonPane, BorderLayout.SOUTH);
		
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
				LoadingPane loading = new LoadingPane(rootPane);
				
				getOverlay().hide();
				loading.show();
				
				new Thread() {
					@Override
					public void run() {
						try {
							Receipt receipt = new Receipt(overlay, Integer.parseInt(generalData.get("PatientID")));
							overlay.setContentPane(receipt);
							overlay.setConstraints(800, 600, 2, 2);
							overlay.setTitle("Receipt", "Date: " + new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date()));
							
							loading.hide();
							overlay.show();
						} catch (SQLException e1) {
							new ErrorPane(rootPane, "Cannot process receipt.").show();
							e1.printStackTrace();
						} catch (Exception e2) {
							new ErrorPane(rootPane, "There is nothing to pay here.").show();
							e2.printStackTrace();
						}
						
						loading.hide();
					}
				}.start();		
			}
		});
		
		
		JPanel container = new JPanel();
		container.setBackground(new Color(60, 60, 60));
		
		container.add(payButton);
		
		return container;
	}
	
	private class AppointmentRowPane extends JPanel {
				
		public AppointmentRowPane(String[] data) {
			super(new GridLayout(1, 4));
			
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
			for (int i = 0; i < data.length; i++) {
				JLabel label = null;
				label = new JLabel(data[i]);
				if (i == 3) {
					if (data[3].equals("Y")) {
						label = new JLabel("Paid");
					} else {
						label = new JLabel("Not Paid");
					}
				}
				
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
	
	
	private class DataRowPane extends JPanel {

		private String field;
		private String data;
		
		protected DataRowPane(String field, String data) {
			super(new BorderLayout());
			setOpaque(false);
			
			this.field = field;
			this.data = data;
			
			CustomRowPane rowPane = new CustomRowPane();
			
			rowPane.addCell(createDataPane(field), 50.0);
			rowPane.addCell(createDataField(data) , 50.0);
			
			add(rowPane);
		}
		
		public void setRowDistribution(double dist1, double dist2) {
			removeAll();
			
			CustomRowPane rowPane = new CustomRowPane();
			
			rowPane.addCell(createDataPane(field), dist1);
			rowPane.addCell(createDataField(data) , dist2);
			
			add(rowPane);
			
			revalidate();
			repaint();
		}
		
		private JPanel createDataField(String field) {
			JPanel fieldPane = new JPanel(new BorderLayout());
			fieldPane.setOpaque(false);
						
			JLabel fieldPabel = new JLabel(field);
			fieldPabel.setForeground(new Color(200, 200, 200));
			fieldPabel.setFont(new Font(fieldPabel.getFont().getFontName(), 
					Font.BOLD, 15));
			
			JPanel container = new JPanel(new GridBagLayout());
			container.setOpaque(false);
			container.add(fieldPabel);
			
			fieldPane.add(container, BorderLayout.WEST);
			
			return fieldPane;
		}

		private JPanel createDataPane(String data) {
			JPanel dataPane = new JPanel(new BorderLayout());
			dataPane.setOpaque(false);
			dataPane.setBorder(new EmptyBorder(0, 0, 0, 20));
			
			JLabel dataLabel = new JLabel(data);
			dataLabel.setForeground(new Color(255, 150, 0));
			dataLabel.setFont(new Font(dataLabel.getFont().getFontName(), 
					Font.BOLD, 15));
			
			JPanel container = new JPanel(new GridBagLayout());
			container.setOpaque(false);
			container.add(dataLabel);
			
			dataPane.add(container, BorderLayout.EAST);
			
			return dataPane;
		}
		
	}


}
