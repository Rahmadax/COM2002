package ui.form.book;

import java.awt.Dimension;

import javax.swing.JComponent;

import ui.custom.DatePicker;
import ui.custom.TimePicker;

class FormTimePicker extends FormData<String> {

	protected FormTimePicker(String helper, String dbField) {
		super(new TimePicker(), helper, dbField);
		setPreferredSize(new Dimension(150, 40));
	}

	@Override
	public String getValue() {
		TimePicker dayPicker = (TimePicker) dataComponent;
		String hour = dayPicker.getHour();
		String minute = dayPicker.getMinute();
		String time = "";
		
		if (minute.length() == 1) {
			time += "0";
		}
		
		time += hour + ":";
		
		if (hour.length() == 1) {
			time += "0";
		}
		
		time += minute;
		
		return time;
	}

}
