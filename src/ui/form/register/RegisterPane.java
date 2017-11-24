package ui.form.register;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import ui.custom.button.CustomButton;
import ui.layout.AbsoluteCenteredPane;

public class RegisterPane extends JPanel {
	
	private JPanel formPane;
	private ArrayList<FormData> dataList;

	public RegisterPane() {
		super(new BorderLayout());
		dataList = new ArrayList<FormData>();
		
		setBackground(new Color(40, 40, 40));
		
		addComponents();
	}
	
	public void sendForm() {
		
	}
	
	private void addComponents() {
		formPane = createFormPane();
		
		addFormData(new FormComboBox(new String[] {"Mr", "Mrs", 
				"Miss", "Ms", "Sir", "Dr"},
				"Title", "Title"), formPane, 50, 50);
		addFormData(new FormField("First Name", "FirstName"), formPane, 50, 50);
		addFormData(new FormField("Last Name", "LastName"), formPane, 50, 50);
		addFormData(new FormDatePicker("Date of birth", "DOB"), formPane, 50, 50);
		
		JPanel[] addressPanes = createAddressPanes();
		
		for (JPanel addressPane: addressPanes) {
			formPane.add(addressPane);
		}
		
		addFormData(new FormSwitch("Subscribe to dental plan", "Subscribe"), formPane, 50, 50);
		addFormData(new FormComboBox(new String[] {"Plan 1", "Plan 2", "Plan 3"},
				"Dental Plan", "Plan"), formPane, 50, 50);
	
		JPanel contentPane = createContentPane();
		
		contentPane.add(formPane, BorderLayout.CENTER);
		contentPane.add(createSubmitPane(), BorderLayout.SOUTH);
		
		AbsoluteCenteredPane container = new AbsoluteCenteredPane(contentPane, this);
		container.setConstraints(750, 550, 3, 1.5);
		
		add(container);
	}
	
	private JPanel createContentPane() {
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(90, 90, 90));
		contentPane.setBorder(new CompoundBorder(
				new MatteBorder(2, 0, 0, 0, new Color(255, 160, 0)),
				new MatteBorder(20, 0, 0, 0, new Color(70, 70, 70))));
		
		return contentPane;
	}
	
	private JPanel[] createAddressPanes() {
		JPanel[] addressPanes = new JPanel[3];
		
		for (int i = 0; i < 3; i++) {
			addressPanes[i] = new JPanel(new GridLayout(1, 2));
			addressPanes[i].setOpaque(false);
		}
		
		addFormData(new FormField("House No.", "HouseNumber"), addressPanes[0], 40, 60);
		addFormData(new FormField("Street Name", "StreetName"), addressPanes[0], 35, 65);
		addFormData(new FormField("District", "DistrictName"), addressPanes[1], 40, 60);
		addFormData(new FormField("City", "CityName"), addressPanes[1], 35, 65);
		addFormData(new FormField("Postcode", "Postcode"), addressPanes[2], 40, 60);
		addFormData(new FormField("Contact No.", "ContactNumber"), addressPanes[2], 35, 65);
		
		return addressPanes;
	}
	
	private JPanel createFormPane() {
		JPanel formPane = new JPanel(new GridLayout(0, 1));
		formPane.setOpaque(false);
		formPane.setBorder(new EmptyBorder(20, 0, 20, 0));

		return formPane;
	}
	
	private void addFormData(FormData formData, JPanel container,
			double dist1, double dist2) {
		formData.setRowDistribution(dist1, dist2);
		container.add(formData);
		dataList.add(formData);
	}

	private HashMap<String, Object> getFormData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		for (FormData formData: dataList) {
			map.put(formData.dbField, formData.getValue());
		}
		
		return map;
	}
	
	private JPanel createSubmitPane() {
		JPanel submitPane = new JPanel();
		submitPane.setBackground(new Color(70, 70, 70));
		submitPane.setBorder(new EmptyBorder(10, 0, 10, 0));
		
		CustomButton submitButton = new CustomButton("Register new patient", 
				CustomButton.REVERSED_STYLE);
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println(getFormData());
			}
		});
		
		submitPane.add(submitButton);
		
		return submitPane;
	}
	
}
