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
				"Title", "Title"));
		addFormData(new FormField("First Name", "FirstName"));
		addFormData(new FormField("Last Name", "LastName"));
		addFormData(new FormDatePicker("Date of birth", "DOB"));
		addFormData(new FormField("Contact Number", "ContactNumber"));
		addFormData(new FormField("House Number", "HouseNumber"));
		addFormData(new FormField("Postcode", "Postcode"));
		addFormData(new FormSwitch("Subscribe to dental plan", "Subscribe"));
		addFormData(new FormComboBox(new String[] {"Plan 1", "Plan 2", "Plan 3"},
				"Dental Plan", "Plan"));
	
		JPanel contentPane = createContentPane();
		
		contentPane.add(formPane, BorderLayout.CENTER);
		contentPane.add(createSubmitPane(), BorderLayout.SOUTH);
		
		AbsoluteCenteredPane container = new AbsoluteCenteredPane(contentPane, this);
		container.setConstraints(600, 550, 2, 1.5);
		
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
	
	private JPanel createFormPane() {
		JPanel formPane = new JPanel(new GridLayout(0, 1));
		formPane.setOpaque(false);
		formPane.setBorder(new EmptyBorder(20, 0, 20, 0));

		return formPane;
	}
	
	private void addFormData(FormData formData) {
		formPane.add(formData);
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
