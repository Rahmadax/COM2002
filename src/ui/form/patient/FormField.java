package ui.form.patient;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import ui.custom.CustomTextField;

class FormField extends FormData<String> {

	public String dbField;
	
	public FormField(String helper, String dbField) {
		super(new CustomTextField("", 14), helper, dbField);
	}

	@Override
	public String getValue() {
		return ((JTextField) dataComponent).getText();
	}
	
}
