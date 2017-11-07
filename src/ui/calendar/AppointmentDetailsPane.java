package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.layout.AnchorTopPane;

public class AppointmentDetailsPane extends JPanel {

	// TO BE CONTINUED
	// pane class that shows details of a specific appointment
	// and the actions that can be performed over it 
	// (eg. reschedule, delete, etc.)
	public AppointmentDetailsPane() {
		super();
		
		setOpaque(false);
		setLayout(new GridLayout(0, 2));
		
		addComponents();
	}
	
	private void addComponents() {
		add(createLeftPane());
		add(createRightPane());
	}
	
	public JPanel getTitlePane() {
		JPanel titlePane = new JPanel();
		titlePane.setOpaque(false);
		titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
		
		JLabel date = new JLabel("10 Novemner 2011");
		date.setBorder(new EmptyBorder(0, 5, 0, 0));
		
		JLabel title = new JLabel("Appoitnment");
		Font large = new Font("serif", Font.BOLD, 25);
		title.setFont(large);
		
		titlePane.add(title);
		titlePane.add(date);
		
		return titlePane;
	}
	
	private JPanel createLeftPane() {
		JPanel leftPane = new JPanel();
		leftPane.setOpaque(false);
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));
		
		JPanel patientPane = new JPanel();
		patientPane.setOpaque(false);
		patientPane.add(new JLabel("Patient: "));
		patientPane.add(new JLabel("John Smith"));
		
		JPanel partnerPane = new JPanel();
		partnerPane.setOpaque(false);
		partnerPane.add(new JLabel("Partner: "));
		partnerPane.add(new JLabel("DOC"));
		
		leftPane.add(patientPane);
		leftPane.add(partnerPane);
		
		return new AnchorTopPane(leftPane);
	}
	
	private JPanel createRightPane() {
		JPanel rightPane = new JPanel();
		rightPane.setOpaque(false);
		rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
		
		JPanel startTime = new JPanel();
		startTime.setOpaque(false);
		startTime.add(new JLabel("Start time: "));
		startTime.add(new JLabel("9:00 AM"));
		
		JPanel endTime = new JPanel();
		endTime.setOpaque(false);
		endTime.add(new JLabel("End time: "));
		endTime.add(new JLabel("10:00 AM"));
		
		rightPane.add(startTime);
		rightPane.add(endTime);
		
		return new AnchorTopPane(rightPane);
	}

}
