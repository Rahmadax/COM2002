package ui.form.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import ui.MainFrame;
import ui.custom.CustomTextField;
import ui.custom.DatePicker;
import ui.custom.TimePicker;
import ui.custom.button.CustomButton;
import ui.layout.AbsoluteCenteredPane;
import ui.popup.ErrorPane;

public class BookPane extends JPanel {
	
	private int patientID;
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
			if (m1.group(3) == "AM") {
				calendar.set(Calendar.AM_PM, Calendar.AM);
			} else {
				calendar.set(Calendar.AM_PM, Calendar.PM);
			}
			
			calendar.set(Calendar.HOUR, Integer.parseInt(m1.group(1)));
			calendar.set(Calendar.MINUTE, Integer.parseInt(m1.group(2)));
			
			calendar.set(Calendar.YEAR, Integer.parseInt(m2.group(1)));
			calendar.set(Calendar.MONTH, Integer.parseInt(m2.group(2) + 1));
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(m2.group(3)));
			
			System.out.println(calendar.getTime());
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
		patientSearchPane.add(
				new CustomTextField("e.g. John Smith", 20));
		patientSearchPane.add(new CustomButton("Search", CustomButton.REVERSED_STYLE));
		
		return patientSearchPane;
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
				new String[] {"Hygenist", "Dentist"}, "Partner", "Partner");
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
		
		submitPane.add(submitButton);
		
		return submitPane;
	}
	
}
