package ui.form.patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;

import ui.custom.tabbedpane.CustomTabbedPane;
import ui.popup.OverlayContentPane;
import ui.popup.OverlayPane;

public class PatientDetailsPane extends OverlayContentPane {
	
	private HashMap<String, String> generalData;

	public PatientDetailsPane(HashMap<String, String> generalData, OverlayPane overlay) {
		super(overlay);
		this.generalData = generalData;
		
		setLayout(new BorderLayout());
		setBackground(new Color(200, 200, 200));
		
		addComponents();
	}
	
	private void addComponents() {
		add(createTabbedPane());
	}
	
	private CustomTabbedPane createTabbedPane() {
		CustomTabbedPane tabbedPane = new CustomTabbedPane();
		tabbedPane.addTab("General", createGeneralPane());
		tabbedPane.addTab("Appointments", createAppointmentsPane());
		tabbedPane.addTab("Costs", createCostsPane());
		
		return tabbedPane;
	}
	
	private JPanel createGeneralPane() {
		JPanel generalPane = new JPanel();
		generalPane.setOpaque(false);
		
		return generalPane;
	}
	
	private JPanel createAppointmentsPane() {
		JPanel appointmentsPane = new JPanel();
		appointmentsPane.setOpaque(false);
		
		return appointmentsPane;
	}
	
	private JPanel createCostsPane() {
		JPanel costsPane = new JPanel();
		costsPane.setOpaque(false);
		
		return costsPane;
	}

}
