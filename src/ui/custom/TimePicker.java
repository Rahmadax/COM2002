package ui.custom;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TimePicker extends JPanel {
	
	private CustomTextField hourField;
	private CustomTextField minuteField;
	private CustomComboBox periodField;

	public TimePicker() {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		setOpaque(false);

		initFields();
		addComponents();
	}
	
	public Calendar getTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, Integer.parseInt(getHour()));
		calendar.set(Calendar.MINUTE, Integer.parseInt(getMinute()));
		
		if (getPeriod() == "AM") {
			calendar.set(Calendar.AM_PM, Calendar.AM);
		} else {
			calendar.set(Calendar.AM_PM, Calendar.PM);
		}
		
		return calendar;
	}
	
	public void setTime(int hour, int minute) {
		String period = null;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		
		if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
			period = "AM";
		} else {
			period = "PM";
		}
		
		hourField.setText(Integer.toString(calendar.get(Calendar.HOUR)));
		minuteField.setText(Integer.toString(calendar.get(Calendar.MINUTE)));
		periodField.setSelectedItem(period);
	}
	
	public String getHour() {
		return hourField.getText();
	}
	
	public String getMinute() {
		return minuteField.getText();
	}
	
	public String getPeriod() {
		return (String) periodField.getSelectedItem();
	}
	
	private void addComponents() {
		add(hourField);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(minuteField);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(periodField);
	}
	
	private void initFields() {		
		hourField = new CustomTextField("h", 2);
		minuteField = new CustomTextField("m", 2);
		periodField = new CustomComboBox(new String[] {"AM", "PM"});
				
		hourField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				adjustInputs();
			}
		});
		
		minuteField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				adjustInputs();
			}
		});
	}
	
	private void adjustInputs() {
		if (getMinute().length() > 2) {
			minuteField.setText(getMinute().substring(0, 2));
		}
		if (getHour().length() > 2) {
			hourField.setText(getHour().substring(0, 2));
		}
		
		minuteField.setText(getMinute().replaceAll("[a-zA-Z]", ""));
		hourField.setText(getHour().replaceAll("[a-zA-Z]", ""));
		
		if (getMinute().length() > 0) {
			int parsedMinute = Integer.parseInt(getMinute());
			
			if (parsedMinute > 59) {
				minuteField.setText("59");
			}
		}
		
		if (getHour().length() > 0) {
			int parseHour = Integer.parseInt(getHour());
			
			if (parseHour > 12) {
				hourField.setText("12");
			}
		}
	}
	
}
