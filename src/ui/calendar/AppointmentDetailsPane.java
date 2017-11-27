package ui.calendar;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import calendar.Appointment;
import mysql.MySQLAccess;
import mysql.query.AppointmentQuery;
import mysql.query.PatientQuery;
import mysql.query.TreatmentApp_LinkerQuery;
import mysql.query.TreatmentQuery;
import mysql.query.TreatmentsStoreQuery;
import ui.MainFrame;
import ui.ModeUI;
import ui.custom.CustomComboBox;
import ui.custom.CustomTextField;
import ui.custom.button.CustomButton;
import ui.layout.AnchorTopPane;
import ui.layout.CenteredPane;
import ui.popup.DialogPane;
import ui.popup.OverlayContentPane;
import ui.popup.OverlayPane;

public class AppointmentDetailsPane extends OverlayContentPane {

	private Appointment appointment;

	private ArrayList<TreatmentPane> treatmentList;
	private JPanel treatmentsPane;
	private String[][] treatments;

	public AppointmentDetailsPane(Appointment appointment, OverlayPane overlay) throws Exception {
		super(overlay);
		this.appointment = appointment;
		treatmentList = new ArrayList<TreatmentPane>();
		
		setOpaque(false);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setFocusable(true);
		setLayout(new BorderLayout());
		addComponents();
	}
	
	private void addComponents() throws Exception {
		fetchTreatments();
		
		JPanel detailsContainer = new JPanel(new GridLayout(1, 2));
		detailsContainer.setOpaque(false);
		detailsContainer.setBorder(new CompoundBorder(
				new MatteBorder(10, 15, 15, 15, new Color(70, 70, 70)),
				new EmptyBorder(20, 0, 20, 0)));
		
		detailsContainer.add(createLeftPane());
		detailsContainer.add(createRightPane());
		
		add(detailsContainer, BorderLayout.NORTH);
		add(createTreatmentsPane(), BorderLayout.CENTER);
		add(createControlPane(), BorderLayout.SOUTH);
	}
	
	private void fetchTreatments() {
		try {
			MySQLAccess access = new MySQLAccess();
			TreatmentApp_LinkerQuery q1 = new TreatmentApp_LinkerQuery(access);
			TreatmentQuery q2 = new TreatmentQuery(access);
			TreatmentsStoreQuery q3 = new TreatmentsStoreQuery(access);
			
			String[][] ts = q3.getAll(q2.getTreatmentName(q1.getIDs(appointment)));
			access.close();
			
			treatments = ts;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JPanel createLeftPane() throws Exception {
		JPanel leftPane = new JPanel();
		leftPane.setOpaque(false);
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));

        MySQLAccess access = new MySQLAccess();
        PatientQuery q = new PatientQuery(access);
		JPanel patientPane = new JPanel();
		String patientName = q.getFullName(appointment.getPatientID());
		access.close();
		
		patientPane.setOpaque(false);
		patientPane.add(new JLabel("Patient: "));
        patientPane.add(new JLabel(patientName));

        JPanel partnerPane = new JPanel();
		partnerPane.setOpaque(false);
		partnerPane.add(new JLabel("Partner: "));
		partnerPane.add(new JLabel(appointment.getPartner()));
		
		leftPane.add(patientPane);
		leftPane.add(partnerPane);
		
		return new AnchorTopPane(leftPane);
	}
	
	private JPanel createRightPane() {
		JPanel rightPane = new JPanel();
		rightPane.setOpaque(false);
		rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
		
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
		
		JPanel startTime = new JPanel();
		startTime.setOpaque(false);
		startTime.add(new JLabel("Start time: "));
		startTime.add(new JLabel(
				timeFormatter.format(appointment.getStartDate())));
		
		JPanel endTime = new JPanel();
		endTime.setOpaque(false);
		endTime.add(new JLabel("End time: "));
		endTime.add(new JLabel(
				timeFormatter.format(appointment.getEndDate())));
		
		rightPane.add(startTime);
		rightPane.add(endTime);
		
		return new AnchorTopPane(rightPane);
	}
	
	private JPanel createTreatmentsPane() {
		JPanel treatmentsPane = new JPanel(new GridLayout(1, 1));
		treatmentsPane.setOpaque(false);
		
		JPanel treatments = new JPanel(new GridLayout(1, 2));
		treatments.setOpaque(false);
		treatments.setBorder(new EmptyBorder(5, 20, 5, 20));
		
		treatments.add(createTreatmentsViewPane(), BorderLayout.CENTER);
		for (String[] data: this.treatments) {
			addTreatment(new TreatmentPane(data[0], Double.parseDouble(data[1])));
		}

		if (MainFrame.mode == ModeUI.PRACTICE) {
			if (this.treatments.length == 0) {
				treatments.add(createAddTreatmentPane(), BorderLayout.SOUTH);
			}
		}
		
		treatmentsPane.add(treatments);
		
		JPanel titlePane = new JPanel();
		titlePane.setOpaque(false);
		
		JLabel titleLabel = new JLabel("Treatments");
		titleLabel.setForeground(new Color(50, 50, 50));
		titleLabel.setFont(new Font("serif", Font.BOLD, 23));
		titleLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
		
		titlePane.add(titleLabel);
		
		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.setBorder(
				new MatteBorder(0, 15, 0, 15, new Color(70, 70, 70)));
		
		container.add(titlePane, BorderLayout.NORTH);
		container.add(treatments, BorderLayout.CENTER);
		
		return container;
	}
	
	private JScrollPane createTreatmentsViewPane() {
		JPanel anchorTopContainer = new JPanel();
		anchorTopContainer.setLayout(new BorderLayout());
		anchorTopContainer.setBackground(new Color(150, 150, 150));;
		
		treatmentsPane = new JPanel();
		treatmentsPane.setLayout(
				new BoxLayout(treatmentsPane, BoxLayout.Y_AXIS));
		treatmentsPane.setOpaque(false);
		
		anchorTopContainer.add(treatmentsPane, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(anchorTopContainer);
		
		scrollPane.setOpaque(false);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);;
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(new LineBorder(new Color(50, 50, 50), 1));
		
		return scrollPane;
	}
	
	private JPanel createControlPane() {
		JPanel controlPane = new JPanel();
		controlPane.setOpaque(false);
		controlPane.setBorder(new CompoundBorder(
				new MatteBorder(0, 15, 15, 15, new Color(70, 70, 70)),
				new EmptyBorder(10, 10, 10, 10)));

		CustomButton cancelButton = createCancelButton();
		CustomButton finishButton = createFinishButton();
		
		if (MainFrame.mode == ModeUI.SECRETARY) {
			controlPane.add(cancelButton, BorderLayout.CENTER);
		} else {
			if (treatments.length == 0) {
				controlPane.add(finishButton, BorderLayout.CENTER);
			}
		}

		return controlPane;
	}
	
	private CustomButton createFinishButton() {
		CustomButton finishButton = new CustomButton("Save and finish");
		finishButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
				getOverlay().hide();
				
				DialogPane dialogPane = new DialogPane(rootPane,
						"Are you sure you want to finish this appointment?");
				
				dialogPane.getOKButton().addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						try {
							MySQLAccess access = new MySQLAccess();
							TreatmentQuery q = new TreatmentQuery(access);
							
							String[] treatmentNames = new String[treatmentList.size()];
							for (int i = 0; i < treatmentList.size(); i++) {
								treatmentNames[i] = treatmentList.get(i).getName();
							}
							
							q.add(treatmentNames, appointment);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				dialogPane.getCancelButton().addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						getOverlay().show();
					}
				});

				dialogPane.show();
			}
		});
		
		return finishButton;
	}
	
	private CustomButton createCancelButton() {
		CustomButton cancelButton = 
				new CustomButton("Cancel appointment", CustomButton.ERROR_STYLE);
		cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
				getOverlay().hide();
				
				DialogPane dialogPane = new DialogPane(rootPane,
						"Are you sure you want to delete this appointment?");
				
				dialogPane.getOKButton().addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						try {
		                	MySQLAccess access = new MySQLAccess();
		                	new AppointmentQuery(access).remove(appointment);
		                	
		                	access.close();
		            		
		            		MainFrame.program.refreshCalendar();
		            		dialogPane.hide();
		            	} catch (Exception e1) {
		            		e1.printStackTrace();
		            	}
					}
				});
				
				dialogPane.getCancelButton().addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						getOverlay().show();
					}
				});

				dialogPane.show();
            }
        });
		
		return  cancelButton;
	}
	
	private JPanel createAddTreatmentPane() {
		JPanel addTreatmentPane = new JPanel();
		addTreatmentPane.setLayout(
				new BoxLayout(addTreatmentPane, BoxLayout.Y_AXIS));
		addTreatmentPane.setOpaque(false);
		
		try {
			MySQLAccess access = new MySQLAccess();
			TreatmentsStoreQuery q = new TreatmentsStoreQuery(access);
			String[] treatments;
			
			if (appointment.getPartner().equals("Dentist")) {
				String[] repair = q.getAll("Repair");
				String[] checkup = q.getAll("Checkup");
				
				treatments = new String[repair.length + checkup.length];
				for (int i = 0; i < repair.length; i++) {
					treatments[i] = repair[i];
				}
				for (int i = 0; i < checkup.length; i++) {
					treatments[repair.length + i] = checkup[i];
				}
			} else {
				String[] hygiene = q.getAll("Hygiene");
				treatments = new String[hygiene.length];
				for (int i = 0; i < hygiene.length; i++) {
					treatments[i] = hygiene[i];
				}
			}
			
			access.close();

			CustomComboBox comboBox = new CustomComboBox(treatments);
			comboBox.setPreferredSize(new Dimension(200, 40));
			
			CustomButton addButton = new CustomButton("Add treatment", CustomButton.REVERSED_STYLE);
			addButton.setPreferredSize(new Dimension(150, 40));
			
			addButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					String string = (String) comboBox.getSelectedItem();
					String pattern = "^(.*)\\s\\((([1-9]\\d*|0)\\.\\d{2})\\)$";
					Pattern p = Pattern.compile(pattern);
					Matcher m = p.matcher(string);

					if (m.find()) {
						String name = m.group(1);
						double price = Double.parseDouble(m.group(2));
						
						addTreatment(new TreatmentPane(name, price));
					}
				}
			});
			
			addTreatmentPane.add(comboBox);
			addTreatmentPane.add(Box.createRigidArea(new Dimension(0, 15)));
			addTreatmentPane.add(addButton);
		} catch (Exception e) {
			e.printStackTrace();
		}

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		
		CenteredPane centeredPane = new CenteredPane(addTreatmentPane, c);
		centeredPane.setBorder(new EmptyBorder(0, 20, 0, 0));
		
		return centeredPane;
	}
	
	private void addTreatment(TreatmentPane treatment) {
		treatmentsPane.add(Box.createRigidArea(
				new Dimension(0, 5)));
		treatmentsPane.add(treatment);

		treatmentList.add(treatment);
		
		treatmentsPane.revalidate();
		treatmentsPane.repaint();
	}
	
	private class TreatmentPane extends JPanel {
				
		private double price;
		private String name;
		
		TreatmentPane(String name, double price) {
			super(new BorderLayout());
			this.price = price;
			this.name = name;
			
			setBackground(new Color(90, 90, 90));
			
			JLabel nameLabel = new JLabel(name);
			nameLabel.setForeground(new Color(255, 160, 0));
			
			JLabel priceLabel = new JLabel(String.format("%.2f", price));
			priceLabel.setForeground(new Color(230, 230, 230));
			
			add(nameLabel, BorderLayout.CENTER);
			add(priceLabel, BorderLayout.EAST);
			
			setPreferredSize(new Dimension(200, 30));
			setBorder(new EmptyBorder(5, 10, 5, 10));
		}
		
		public String getName() {
			return name;
		}
		
		public double getPrice() {
			return price;
		}
		
	}

}
