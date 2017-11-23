package ui.form.book;

import java.awt.Dimension;

import javax.swing.JComponent;

import ui.custom.DatePicker;

class FormDatePicker extends FormData<String> {

	protected FormDatePicker(String helper, String dbField) {
		super(new DatePicker(100, 1), helper, dbField);
	}

	@Override
	public String getValue() {
		DatePicker dayPicker = (DatePicker) dataComponent;
		String day = dayPicker.getDay();
		String month = dayPicker.getMonth();
		String year = dayPicker.getYear();
		String date = year + "-";
		
		if (month.length() == 1) {
			date += "0";
		}
		
		date += month + "-";
		
		if (day.length() == 1) {
			date += "0";
		}
		
		date += day;
		
		return date;
	}

}
