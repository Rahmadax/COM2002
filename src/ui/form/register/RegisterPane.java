package ui.form.register;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import mysql.MySQLAccess;
import mysql.query.AddressQuery;
import mysql.query.HCPStoreQuery;
import mysql.query.HCPsQuery;
import mysql.query.PatientQuery;
import ui.MainFrame;
import ui.custom.CustomComboBox;
import ui.custom.CustomSwitch;
import ui.custom.CustomTextField;
import ui.custom.DatePicker;
import ui.custom.button.CustomButton;
import ui.layout.AbsoluteCenteredPane;
import ui.popup.ErrorPane;
import ui.popup.SuccessPane;

public class RegisterPane extends JPanel {
	
	private JPanel formPane;
	private ArrayList<FormData> dataList;

	public RegisterPane() {
		super(new BorderLayout());
		dataList = new ArrayList<FormData>();
		
		setBackground(new Color(40, 40, 40));
		
		addComponents();
	}
	
	private void clearInputs() {
		for (FormData formData: dataList) {
			JComponent component = formData.dataComponent;
			
			if (component instanceof CustomComboBox) {
				((CustomComboBox) component).setSelectedIndex(0);
			} else if (component instanceof CustomTextField) {
				((CustomTextField) component).setText("");
			} else if (component instanceof DatePicker) {
				// -----------
			} else if (component instanceof CustomSwitch) {
				((CustomSwitch) component).makeSwitch(0);
			}
		}
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
		
		HashMap<String, Object> map = getFormData();

		try {
			MySQLAccess access = new MySQLAccess();
			HCPStoreQuery q = new HCPStoreQuery(access);
			String[] strs = q.getAll();
			
			addFormData(new FormComboBox(strs, "Dental Plan", "Plan"), formPane, 50, 50);
			
			access.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
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
				HashMap<String, Object> map = getFormData();

				try {
					MySQLAccess access = new MySQLAccess();
					AddressQuery adQuery = new AddressQuery(access);
					PatientQuery patQuery = new PatientQuery(access);

					int houseNumber = Integer.parseInt((String) map.get("HouseNumber"));
					String postCode = map.get("Postcode").toString();
					String streetName = map.get("StreetName").toString();
					String districtName = map.get("DistrictName").toString();
					String cityName = map.get("CityName").toString();
					String title = map.get("Title").toString();
					String firstName = map.get("FirstName").toString();
					String lastName = map.get("LastName").toString();
					String dob = map.get("DOB").toString();
					String contactNumber = map.get("ContactNumber").toString();

					ResultSet rs = adQuery.get(houseNumber, postCode);
					
					if (rs.next()) {
						if (postCode == rs.getString(2) && houseNumber == rs.getInt(1)) {
							patQuery.add(title, firstName, lastName, dob, contactNumber, houseNumber, postCode);
							
							JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
							new SuccessPane(rootPane, "New Patient added successfully!").show();
						} 
					} else {
						adQuery.add(houseNumber, postCode, streetName, districtName, cityName);
						patQuery.add(title, firstName, lastName, dob, contactNumber, houseNumber, postCode);
					}
					
					if ((boolean) map.get("Subscribe")) {
						int patientID = patQuery.getLastadded();
						
						Pattern p = Pattern.compile("^(.*)\\s\\(.*\\)$");
						Matcher m = p.matcher(map.get("Plan").toString());
						
						if (m.find()) {
							String planName = m.group(1);
							
							HCPsQuery q = new HCPsQuery(access);
							q.addHCP(planName, patientID);
						}	
					}
					
					JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
					new SuccessPane(rootPane, "New Patient added successfully!").show();
					
					access.close();
					
					clearInputs();
				} catch (SQLException e1) {
					e1.printStackTrace();
					
					JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
					new ErrorPane(rootPane, "Unable to connect to the database.").show();
				} catch (Exception e2) {
					e2.printStackTrace();
					
					JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
					new ErrorPane(rootPane, "Something went wrong. Please check your imput.").show();
				}
				
			}
		});
		
		submitPane.add(submitButton);
		
		return submitPane;
	}
	
}
