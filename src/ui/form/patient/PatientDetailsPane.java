package ui.form.patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

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
		appointmentsPane.setLayout(new BoxLayout(appointmentsPane, BoxLayout.Y_AXIS));
		
		JPanel anchorTopContainer = new JPanel(new BorderLayout());
		anchorTopContainer.setBackground(new Color(90, 90, 90));
		anchorTopContainer.add(appointmentsPane, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(anchorTopContainer);
		scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 1));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);;
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setOpaque(false);
		
		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.add(scrollPane);
		
		return container;
	}
	
	private JPanel createCostsPane() {
		JPanel costsPane = new JPanel();
		costsPane.setOpaque(false);
		
		return costsPane;
	}

}
