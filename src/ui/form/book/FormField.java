package ui.form.book;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import ui.custom.CustomTextField;

class FormField extends FormData<String> {

	public String dbField;
	
	public FormField(String helper, String dbField) {
		super(new CustomTextField("", 14), helper, dbField);
		dataComponent.setEnabled(false);
	}

	@Override
	public String getValue() {
		return ((JTextField) dataComponent).getText();
	}
	
}
