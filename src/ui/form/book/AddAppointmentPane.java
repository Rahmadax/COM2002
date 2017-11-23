package ui.form.book;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JRootPane;

import ui.popup.OverlayPane;

public class AddAppointmentPane extends OverlayPane {

	private SubmitListener submitListener;
	private ArrayList<FormData> dataList;
	
	public AddAppointmentPane(JRootPane rootPane) {
		super(rootPane, createContentPane());
		
		dataList = new ArrayList<FormData>();
		submitListener = new SubmitListener() {
			@Override public void listen(HashMap<String, String> data) {}
		};
	}
	
	private void submit(HashMap<String, String> data) {
		submitListener.listen(data);
	}
	
	private static JPanel createContentPane() {
		JPanel contentPane = new JPanel();
		
		//contentPane.add(createAddPane());
		
		return contentPane;
	}
	
	private static JPanel createAddPane() {
		JPanel addPane = new JPanel();
		addPane.setOpaque(false);
		
		FormComboBox practice = new FormComboBox(
				new String[] {"Hygenist", "Dentist"}, "Partner", "Partner");
		FormDatePicker startDate = new FormDatePicker("Start Date", "StartDate");
		FormDatePicker endDate = new FormDatePicker("End Date", "EndDate");
		FormTimePicker startTime = new FormTimePicker("Start Time", "StartTime");
		FormTimePicker endTime = new FormTimePicker("End Time", "EndTime");
		
		addPane.add(practice);
		addPane.add(startDate);
		addPane.add(endDate);
		addPane.add(startTime);
		addPane.add(endTime);
		
		return addPane;
	}
	
	public void addSubmitListener(SubmitListener submitListener) {
		this.submitListener = submitListener;
	};

	abstract class SubmitListener {
		public abstract void listen(HashMap<String, String> data);
	}
	
}
