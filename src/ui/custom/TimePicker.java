package ui.custom;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TimePicker extends JPanel {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		frame.setTitle("Sheffield Dental Care");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setMinimumSize(new Dimension(1024, 768));
		
		frame.add(new TimePicker());
		frame.setVisible(true);
	}
	
	private CustomTextField hourField;
	private CustomTextField minuteField;
	private CustomComboBox periodField;

	public TimePicker() {
		super(new FlowLayout(FlowLayout.CENTER, 5, 0));
		
		setOpaque(false);

		initFields();
		addComponents();
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
		add(minuteField);
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
