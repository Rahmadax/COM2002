package ui.form.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import javafx.scene.shape.Box;
import ui.MainFrame;
import ui.custom.CustomTextField;
import ui.custom.button.CustomButton;
import ui.layout.AbsoluteCenteredPane;

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
		container.setConstraints(800, 550, 2, 1.5);
		
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
		
		FormField patientFIeld = new FormField("Patient", "patientID");
		dataList.add(patientFIeld);
		
		FormComboBox practice = new FormComboBox(
				new String[] {"Hygenist", "Dentist"}, "Partner", "Partner");
		FormDatePicker startDate = new FormDatePicker("Start Date", "StartDate");
		FormDatePicker endDate = new FormDatePicker("End Date", "EndDate");
		FormTimePicker startTime = new FormTimePicker("Start Time", "StartTime");
		FormTimePicker endTime = new FormTimePicker("End Time", "EndTime");
		
		appointmentPane.add(patientFIeld);
		appointmentPane.add(practice);
		appointmentPane.add(startDate);
		appointmentPane.add(startTime);
		appointmentPane.add(endDate);
		appointmentPane.add(endTime);
		
		dataList.add(practice);
		dataList.add(startDate);
		dataList.add(endDate);
		dataList.add(startTime);
		dataList.add(endTime);
		
		appointmentsPane.add(appointmentPane);
	}

	private HashMap<String, Object> getFormData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("patientID", patientID);
		
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
				System.out.println(getFormData());
			}
		});
		
		submitPane.add(submitButton);
		
		return submitPane;
	}
	
}
