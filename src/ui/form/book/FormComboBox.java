package ui.form.book;

import java.awt.Dimension;

import javax.swing.JComboBox;

import ui.custom.CustomComboBox;

class FormComboBox extends FormData<String> {

	public FormComboBox(String[] strs, String helper, String dbField) {
		super(new CustomComboBox(strs), helper, dbField);
	}

	@Override
	public String getValue() {
		return (String) ((JComboBox) dataComponent).getSelectedItem();
	}

}
