package ui.custom;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DatePicker extends JPanel {
	
	private int yearsBehind;
	private int yearsAfter;
	
	private CustomComboBox dayCB;
	private CustomComboBox monthCB;
	private CustomComboBox yearCB;
	
	private String[] days;
	private String[] months;
	private String[] years;

	public DatePicker(int yearsBehind, int yearsAfter) {
		super(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.yearsBehind = yearsBehind;
		this.yearsAfter = yearsAfter;
		
		setOpaque(false);
		
		initDates();
		initComboBoxes();
		addComponents();
		adjustDay();
	}
	
	public void setDate(int day, int month, int year) {
		dayCB.setSelectedItem(Integer.toString(day));
		monthCB.setSelectedItem(Integer.toString(month));
		yearCB.setSelectedItem(Integer.toString(year));
	}
	
	public String getDay() {
		return (String) dayCB.getSelectedItem();
	}
	
	public String getMonth() {
		return (String) monthCB.getSelectedItem();
	}
	
	public String getYear() {
		return (String) yearCB.getSelectedItem();
	}
	
	private void addComponents() {
		add(dayCB);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(monthCB);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(yearCB);
	}
	
	private void initComboBoxes() {
		Calendar calendar = Calendar.getInstance(Locale.UK);
		
		dayCB = new CustomComboBox(days);
		dayCB.setSelectedItem(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
		
		monthCB = new CustomComboBox(months);
		monthCB.setSelectedItem(Integer.toString(calendar.get(Calendar.MONTH) + 1));
		
		yearCB = new CustomComboBox(years);
		yearCB.setSelectedItem(Integer.toString(calendar.get(Calendar.YEAR)));
		
		monthCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adjustDay();
			}
		});
		
		yearCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adjustDay();
			}
		});
	}
	
	private void adjustDay() {
		int day = Integer.parseInt((String) dayCB.getSelectedItem());
		int month = Integer.parseInt((String) monthCB.getSelectedItem());
		int year = Integer.parseInt((String) yearCB.getSelectedItem());
		int maxDay = 0;
		
		switch (month) {
		case 1: case 3:case 5:  case 7: case 8: case 10: case 12:
			maxDay = 31;
            break;
        case 4: case 6: case 9: case 11:
        	maxDay = 30;
            break;
        case 2:
            if (((year % 4 == 0) && !(year % 100 == 0)) || (year % 400 == 0)) {
            	maxDay = 29;
            }
            else {
            	maxDay = 28;
            }
            break;
		}

		if (dayCB.getItemCount() != maxDay) {
			dayCB = new CustomComboBox(Arrays.copyOfRange(days, 0, maxDay));
			
			if (day > maxDay) {
				dayCB.setSelectedItem(Integer.toString(maxDay));
			} else {
				dayCB.setSelectedItem(Integer.toString(day));
			}
			
			remove(0);
			add(dayCB, 0);
			revalidate();
			repaint();
		}
	}
	
	private void initDates() {
		days = new String[31];
		months = new String[12];
		years = new String[yearsBehind + yearsAfter];
		
		for (int i = 0; i < 31; i++) {
			days[i] = Integer.toString(i + 1);
		}
		
		for (int i = 0; i < 12; i++) {
			months[i] = Integer.toString(i + 1);
		}
		
		Calendar calendar = Calendar.getInstance(Locale.UK);
		int currYear = calendar.get(Calendar.YEAR);
		
		for (int i = 0; i < yearsBehind + yearsAfter; i++) {
			years[i] = Integer.toString(currYear - yearsBehind + i);
		}
	}
	
}
