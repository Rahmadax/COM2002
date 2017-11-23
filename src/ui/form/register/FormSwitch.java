package ui.form.register;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.custom.CustomSwitch;
import ui.custom.CustomTextField;

class FormSwitch extends FormData<Boolean> {

	public String dbField;
	
	public FormSwitch(String helper, String dbField) {
		super(new CustomSwitch(), helper, dbField);
	}

	@Override
	public Boolean getValue() {
		return ((CustomSwitch) dataComponent).getValue();
	}
	
}