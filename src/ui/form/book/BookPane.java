package ui.form.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import mysql.query.AppointmentQuery;
import mysql.query.PatientQuery;
import mysql.query.TreatmentApp_LinkerQuery;
import mysql.query.TreatmentQuery;
import mysql.query.TreatmentsStoreQuery;
import ui.MainFrame;
import ui.custom.CustomComboBox;
import ui.custom.CustomTextField;
import ui.custom.DatePicker;
import ui.custom.TimePicker;
import ui.custom.button.CustomButton;
import ui.form.patient.PatientDetailsPane;
import ui.layout.AbsoluteCenteredPane;
import ui.listener.HoverListener;
import ui.popup.ErrorPane;
import ui.popup.OverlayContentPane;
import ui.popup.OverlayPane;
import ui.popup.SuccessPane;

public class BookPane extends JPanel {
	
	private int patientID = -1;
	private JPanel formPane;
	private JPanel appointmentsPane;
	private ArrayList<FormData> dataList;

	public BookPane() {
		super(new BorderLayout());
		dataList = new ArrayList<FormData>();
		
		setBackground(new Color(40, 40, 40));
		
		addComponents();
	}
	
	public void sendForm() {
		HashMap<String, Object> data = getFormData();
		
		if (validateData(data)) {
			try {
				MySQLAccess access = new MySQLAccess();
				AppointmentQuery q = new AppointmentQuery(access);
				
				JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
				
				if (q.isValidTimeSlot(data)) {
					q.add(data);
					
            		MainFrame.program.refreshCalendar();
					new SuccessPane(rootPane, "Appointment added successfully.").show();
				} else {
					new ErrorPane(rootPane, "The time selected is not available for booking.").show();
				}
				
				access.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean validateData(HashMap<String, Object> data) {
		Calendar start = null;
		Calendar end = null;

		try {			
			start = convertStringToDate(
					(String) data.get("StartTime"), (String) data.get("AppointmentDate"));
			end = convertStringToDate(
					(String) data.get("EndTime"), (String) data.get("AppointmentDate"));
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
			new ErrorPane(rootPane, "Times do not form a period of day.").show();
			
			return false;
		}
		
		if (start.compareTo(end) > -1) {
			JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
			new ErrorPane(rootPane, "Times do not form a period of day.").show();
			
			return false;
		}
		
		return true;
	}
	
	public void clearPatient() {
		setPatient(-1, "");
	}
	
	public void setInitialPartner(String partner) {
		for (FormData formData: dataList) {
			if (formData.dbField == "Partner") {
				((CustomComboBox) formData.dataComponent).setSelectedItem(partner);;
			}
		}
	}
	
	public void setInitialDate(Calendar date) {
		for (FormData formData: dataList) {
			if (formData.dbField == "AppointmentDate") {
				((DatePicker) formData.dataComponent).setDate(
						date.get(Calendar.DAY_OF_MONTH), 
						date.get(Calendar.MONTH) + 1,
						date.get(Calendar.YEAR));
			}
		}
	}
	
	public void setInitialTimes(Date startTime, Date endTime) {
		Calendar start = Calendar.getInstance();
		start.setTime(startTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		
		for (FormData formData: dataList) {
			if (formData.dbField == "StartTime") {
				((TimePicker) formData.dataComponent).setTime(
						start.get(Calendar.HOUR_OF_DAY),
						start.get(Calendar.MINUTE));
			}
			
			if (formData.dbField == "EndTime") {
				((TimePicker) formData.dataComponent).setTime(
						end.get(Calendar.HOUR_OF_DAY),
						end.get(Calendar.MINUTE));
			}
		}
	}
	
	public static Calendar convertStringToDate(String time, String date) {
		Calendar calendar = Calendar.getInstance(Locale.UK);
		Pattern p1 = Pattern.compile("^(\\d{2}):(\\d{2})\\s(AM|PM)$");
		Matcher m1 = p1.matcher(time);
		Pattern p2 = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})$");
		Matcher m2 = p2.matcher(date);
	
		if (m1.find() && m2.find()) {
			if (m1.group(3).equals("AM")) {
				calendar.set(Calendar.AM_PM, Calendar.AM);
			} else {
				calendar.set(Calendar.AM_PM, Calendar.PM);
			}

			calendar.set(Calendar.HOUR, Integer.parseInt(m1.group(1)));
			calendar.set(Calendar.MINUTE, Integer.parseInt(m1.group(2)));

			calendar.set(Calendar.YEAR, Integer.parseInt(m2.group(1)));
			calendar.set(Calendar.MONTH, Integer.parseInt(m2.group(2)) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(m2.group(3)));
		} else {
			throw new IllegalStateException();
		}
		
		return calendar;
	}
	
	private void addComponents() {
		formPane = createFormPane();

		add(createContentPane());
	}
	
	private JPanel createContentPane() {
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(90, 90, 90));
		contentPane.setBorder(new CompoundBorder(
				new MatteBorder(2, 0, 0, 0, new Color(255, 160, 0)),
				new MatteBorder(20, 0, 0, 0, new Color(70, 70, 70))));
		
		contentPane.add(createPatientSearchPane(), BorderLayout.NORTH);
		contentPane.add(formPane, BorderLayout.CENTER);
		contentPane.add(createSubmitPane(), BorderLayout.SOUTH);
		
		AbsoluteCenteredPane container = new AbsoluteCenteredPane(contentPane, this);
		container.setConstraints(700, 500, 3, 2);
		
		return container;
	}
	
	private JPanel createPatientSearchPane() {
		BookPane bookPane = this;
		
		JPanel patientSearchPane = new JPanel(
				new FlowLayout(FlowLayout.CENTER, 10, 5));
		patientSearchPane.setOpaque(false);
		patientSearchPane.setBorder(new CompoundBorder(
				new MatteBorder(0, 0, 20, 0, new Color(40, 40, 40)),
				new EmptyBorder(10, 10, 10, 10)));
		
		JLabel helperLabel = new JLabel("Select patient");
		helperLabel.setForeground(new Color(240, 140, 0));
		helperLabel.setFont(
				new Font(helperLabel.getFont().getFontName(), Font.BOLD, 18));
		patientSearchPane.add(helperLabel);
		
		CustomTextField searchField = new CustomTextField("e.g. John Smith", 20);
		patientSearchPane.add(searchField);
		
		CustomButton searchButton = new CustomButton("Search", CustomButton.REVERSED_STYLE);
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					String text = searchField.getText();
					MySQLAccess access = new MySQLAccess();
					PatientQuery query = new PatientQuery(access);
					
					ResultSet resultSet = query.getAll();
					String[][] patients = handleSearch(resultSet);
					
					ArrayList<String[]> data = new ArrayList<String[]>();
					
					for (String[] patient: patients) {						
						Pattern p = Pattern.compile(text.toLowerCase());
						Matcher m1 = p.matcher(patient[1].toLowerCase() + " " + patient[2].toLowerCase());
						Matcher m2 = p.matcher(patient[1].toLowerCase());
						Matcher m3 = p.matcher(patient[2].toLowerCase());
						
						if (m1.find()) {
							data.add(0, patient);
						} else if (m2.find()) {
							data.add(patient);
						} else if (m3.find()) {
							data.add(patient);
						}
					}
					
					JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
					OverlayPane overlay = new OverlayPane(rootPane, new JPanel());
					overlay.setContentPane(new SearchResultsPane(data, bookPane, overlay));
					overlay.setTitle("Search results", "Text query: " + text);
					overlay.show();
					
					access.close();	
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		patientSearchPane.add(searchButton);
		
		return patientSearchPane;
	}
	
	private String[][] handleSearch(ResultSet searchInput) throws Exception {
		ArrayList<String> allPatientIDs = new ArrayList<String>();
		ArrayList<String> allFirstNames = new ArrayList<String>();
		ArrayList<String> allSecondNames = new ArrayList<String>();
		ArrayList<String> allDOB = new ArrayList<String>();
		ArrayList<String> allContactNumbers = new ArrayList<String>();
		ArrayList<String> allHouseNums = new ArrayList<String>();
		ArrayList<String> allPostCodes = new ArrayList<String>();

		while (searchInput.next()) {
			allPatientIDs.add(Integer.toString(searchInput.getInt(1)));
			allFirstNames.add(searchInput.getString(3));
			allSecondNames.add(searchInput.getString(4));
			allDOB.add(searchInput.getDate(5).toString());
			allContactNumbers.add(searchInput.getString(6));
			allHouseNums.add(searchInput.getString(7));
			allPostCodes.add(searchInput.getString(8));
		}
		
		String[][] resultsBack = new String[allPatientIDs.size()][7];
		
		for (int i = 0; i < resultsBack.length; i++) {
			resultsBack[i][0] = allPatientIDs.get(i);
			resultsBack[i][1] = allFirstNames.get(i);
			resultsBack[i][2] = allSecondNames.get(i);
			resultsBack[i][3] = allDOB.get(i);
			resultsBack[i][4] = allContactNumbers.get(i);
			resultsBack[i][5] = allHouseNums.get(i);
			resultsBack[i][6] = allPostCodes.get(i);
		}
		return resultsBack;
	}

	
	private JPanel createFormPane() {
		JPanel formPane = new JPanel(new BorderLayout());
		formPane.setOpaque(false);
		formPane.setBorder(new EmptyBorder(20, 0, 20, 0));

		appointmentsPane = createAppointmentsPane();
		addAppointmentData();
		
		formPane.add(appointmentsPane, BorderLayout.CENTER);

		return formPane;
	}
	
	private JPanel createAppointmentsPane() {
		JPanel appointmentsPane = new JPanel(new BorderLayout());
		appointmentsPane.setOpaque(false);
		
		return appointmentsPane;
	}
	
	private void addAppointmentData() {
		JPanel appointmentPane = new JPanel(new GridLayout(0, 1, 10, 10));
		appointmentPane.setOpaque(false);
		
		FormField patientFIeld = new FormField("Patient", "PatientName");
		dataList.add(patientFIeld);
		
		FormComboBox practice = new FormComboBox(
				new String[] {"Hygienist", "Dentist"}, "Partner", "Partner");
		FormDatePicker startDate = new FormDatePicker("Date", "AppointmentDate");
		FormTimePicker startTime = new FormTimePicker("Start Time", "StartTime");
		FormTimePicker endTime = new FormTimePicker("End Time", "EndTime");
		
		appointmentPane.add(patientFIeld);
		appointmentPane.add(practice);
		appointmentPane.add(startDate);
		appointmentPane.add(startTime);
		appointmentPane.add(endTime);
		
		dataList.add(practice);
		dataList.add(startDate);
		dataList.add(startTime);
		dataList.add(endTime);
		
		appointmentsPane.add(appointmentPane);
	}

	private HashMap<String, Object> getFormData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("PatientID", patientID);
		
		for (FormData formData: dataList) {
			map.put(formData.dbField, formData.getValue());
		}
		
		return map;
	}
	
	private JPanel createSubmitPane() {
		JPanel submitPane = new JPanel();
		submitPane.setBackground(new Color(70, 70, 70));
		submitPane.setBorder(new EmptyBorder(10, 0, 10, 0));
		
		CustomButton submitButton = new CustomButton("Book appointment", 
				CustomButton.REVERSED_STYLE);
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				sendForm();
			}
		});
		
		CustomButton clearButton = new CustomButton("Clear patient");
		clearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clearPatient();
			}
		});
		
		submitPane.add(clearButton);
		submitPane.add(submitButton);
		
		return submitPane;
	}
	
	private class SearchResultsPane extends OverlayContentPane {

		private BookPane bookPane;
		
		public SearchResultsPane(ArrayList<String[]> data, BookPane bookPane, OverlayPane overlay) {
			super(overlay);
			setLayout(new BorderLayout());
			this.bookPane = bookPane;
			
			JPanel resultPane = new JPanel();
			resultPane.setLayout(new BoxLayout(resultPane, BoxLayout.Y_AXIS));
			resultPane.setBackground(new Color(90, 90, 90));
				
			for (String[] strs: data) {
				ResultRowPane rowPane = new ResultRowPane(strs);
				rowPane.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						overlay.hide();
						bookPane.setPatient(Integer.parseInt(strs[0]), strs[1] + " " + strs[2]);
					}
				});
				resultPane.add(rowPane);
			}
			
			JPanel anchorTopContainer = new JPanel(new BorderLayout());
			anchorTopContainer.setBackground(new Color(90, 90, 90));
			anchorTopContainer.add(resultPane, BorderLayout.NORTH);

			JScrollPane scrollPane = new JScrollPane(anchorTopContainer);
			
			scrollPane.getVerticalScrollBar().setUnitIncrement(16);;
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setBorder(null);
			
			add(scrollPane);
		}
		
	}
	
	private class ResultRowPane extends JPanel {
				
		public ResultRowPane(String[] data) {
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
		
		private HashMap<String, String> convertToHashMapp(String[] data) {
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("PatientID", data[0]);
			map.put("PatientName", data[1] + " " + data[2]);
			map.put("PatientDOB", data[3]);
			map.put("ContactNumber", data[4]);
			map.put("HouseNumber", data[5]);
			map.put("Postcode", data[6]);
			
			return map;
		}
		
		private void addData(String[] data) {
			for (int i = 2; i < data.length; i++) {
				JLabel label = null;
				
				if (i == 2) {
					label = new JLabel(data[1] + " " + data[2]);
				} else {
					label = new JLabel(data[i]);
				}
				
				label.setForeground(new Color(255, 160, 0));
				label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 15));
				
				add(label);
			}
		}
		
	}

	public void setPatient(int patientID, String name) {
		this.patientID = patientID;
		
		for (FormData formData: dataList) {
			if (formData.dbField == "PatientName") {
				((CustomTextField) formData.dataComponent).setText(name);;
			}
		}
	}

	
}
