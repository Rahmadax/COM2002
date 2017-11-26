package ui.form.patient;

import mysql.MySQLAccess;
import mysql.query.PatientQuery;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import ui.MainFrame;
import ui.custom.CustomTextField;
import ui.custom.button.CustomButton;
import ui.layout.AbsoluteCenteredPane;
import ui.listener.HoverListener;
import ui.popup.LoadingPane;
import ui.popup.OverlayPane;

public class FindPane extends JPanel {
		
	private JTextField findTextForename;
	private JTextField findTextSurname;
	private JTextField findTextPostcode;
	private JTextField findTextHouseNum;
	private JTextField findTextDOB;
	
	private JPanel displayPane;

	public FindPane() {
		super(new GridLayout(2, 0));
		
		setBackground(new Color(40, 40, 40));
		
		addComponents();
	}
	
	private void addComponents() {
		JPanel container1 = new JPanel(new BorderLayout());
		container1.setOpaque(false);
		AbsoluteCenteredPane centeredPane1 = new AbsoluteCenteredPane(createSearchBarPane(), container1);
		centeredPane1.setConstraints(900, 200, 1.7, 1);
		container1.add(centeredPane1);
		add(container1);
		
		JPanel container2 = new JPanel(new BorderLayout());
		container2.setOpaque(false);
		AbsoluteCenteredPane centeredPane2 = new AbsoluteCenteredPane(createResultPane(), container1);
		centeredPane2.setConstraints(900, 200, 1.7, 1);
		container2.add(centeredPane2);
		add(container2);
	}
	
	private void clear() {
		findTextForename.setText("");
		findTextSurname.setText("");
		findTextPostcode.setText("");
		findTextHouseNum.setText("");
		findTextDOB.setText("");
		
		displayPane.removeAll();
		displayPane.revalidate();
		displayPane.repaint();
	}

	private JPanel createResultPane() {
		JPanel resultPane = new JPanel();
		resultPane.setLayout(new BoxLayout(resultPane, BoxLayout.Y_AXIS));
		resultPane.setBackground(new Color(90, 90, 90));
		
		displayPane = resultPane;
		
		JPanel anchorTopContainer = new JPanel(new BorderLayout());
		anchorTopContainer.setBackground(new Color(90, 90, 90));
		anchorTopContainer.add(resultPane, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(anchorTopContainer);
		
		scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 1));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);;
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.setBorder(new CompoundBorder(
				new MatteBorder(10, 50, 10, 50, new Color(40, 40, 40)),
				new EmptyBorder(10, 50, 10, 50)));
		container.add(scrollPane);
		
		return container;
	}

	private CustomButton createSearchButton() {
		CustomButton searchButton = new CustomButton("Search", CustomButton.REVERSED_STYLE);
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
				LoadingPane loading = new LoadingPane(rootPane);
				loading.show();
				
				String searchTextForename = findTextForename.getText();
				String searchTextSurname = findTextSurname.getText();
				String searchTextPostcode = findTextPostcode.getText();
				String searchTextHouseNum = findTextHouseNum.getText();
				String searchTextDOB = findTextDOB.getText();
				
				new Thread() {
					public void run() {
						try {
							String[][] data = new String[][] {};
							
							MySQLAccess access = new MySQLAccess();
							PatientQuery query = new PatientQuery(access);
							if (!searchTextForename.equals("") && searchTextSurname.equals("") && searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithFirstName(searchTextForename);
								data = handleSearch(resultSet);
							} else if (searchTextForename.equals("") && !searchTextSurname.equals("") && searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithLastName(searchTextSurname);
								data = handleSearch(resultSet);
							} else if (!searchTextForename.equals("") && !searchTextSurname.equals("") && searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithWholeName(searchTextForename, searchTextSurname);
								data = handleSearch(resultSet);
							} else if (!searchTextForename.equals("") && searchTextSurname.equals("") && searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && !searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithFirstNameDOB(searchTextForename, searchTextDOB);
								data = handleSearch(resultSet);
							} else if (!searchTextForename.equals("") && !searchTextSurname.equals("") && searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && !searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithWholeNameDOB(searchTextForename, searchTextSurname,
										searchTextDOB);
								data = handleSearch(resultSet);
							} else if (searchTextForename.equals("") && !searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithLastNamePostCode(searchTextSurname, searchTextPostcode);
								data = handleSearch(resultSet);
							} else if (searchTextForename.equals("") && !searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& !searchTextHouseNum.equals("") && !searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithLastNamePostCodeHouseNum(searchTextSurname,
										searchTextPostcode, searchTextHouseNum);
								data = handleSearch(resultSet);
							}else if (searchTextForename.equals("") && searchTextSurname.equals("") && searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && !searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithDOB(searchTextDOB);
								data = handleSearch(resultSet);
							}else if (searchTextForename.equals("") && searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithPostCode(searchTextPostcode);
								data = handleSearch(resultSet);
							}else if (searchTextForename.equals("") && searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& !searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithPostCodeHouseNum(searchTextPostcode, searchTextHouseNum);
								data = handleSearch(resultSet);
							}else if (searchTextForename.equals("") && searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& !searchTextHouseNum.equals("") && !searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithPostCodeHouseNumDOB(searchTextPostcode, searchTextHouseNum,
										searchTextDOB);
								data = handleSearch(resultSet);
							}else if (searchTextForename.equals("") && !searchTextSurname.equals("") && searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && !searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithLastNameDOB(searchTextSurname, searchTextDOB);
								data = handleSearch(resultSet);
							}else if (!searchTextForename.equals("") && searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithFirstNamePostCode(searchTextForename, searchTextPostcode);
								data = handleSearch(resultSet);
							}else if (!searchTextForename.equals("") && !searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& !searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithWholeNamePostCodeHouseNum(searchTextForename, searchTextSurname,
										searchTextPostcode, searchTextHouseNum);
								data = handleSearch(resultSet);
							}else if (!searchTextForename.equals("") && searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& !searchTextHouseNum.equals("") && searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithFirstNamePostCodeHouseNum(searchTextForename, searchTextPostcode,
										searchTextHouseNum);
								data = handleSearch(resultSet);
							}else if (!searchTextForename.equals("") && !searchTextSurname.equals("") && !searchTextPostcode.equals("")
									&& !searchTextHouseNum.equals("") && !searchTextDOB.equals("")) {
								ResultSet resultSet = query.findWithWholeNamePostCodeHouseNumDOB(searchTextForename,
										searchTextSurname, searchTextPostcode, searchTextHouseNum, searchTextDOB);
								data = handleSearch(resultSet);
							}
							access.close();

							displayResults(data);
						} catch (Exception t) {
							t.printStackTrace();
						}
						
						loading.hide();
					};
				}.start();
				
			}
		});
		
		return searchButton;
	}
	
	private JPanel createSearchBarPane() {
		JPanel searchPane = new JPanel(new GridLayout(0, 1));
		searchPane.setPreferredSize(new Dimension(800, 350));
		searchPane.setBorder(new CompoundBorder(
				new MatteBorder(20, 100, 0, 100, new Color(40, 40, 40)),
				new	CompoundBorder(
						new LineBorder(new Color(200, 200, 200), 1),
						new EmptyBorder(20, 100, 20, 100))));
		searchPane.setBackground(new Color(90, 90, 90));
		
		FormField field1 = new FormField("Forename", "");
		findTextForename = (CustomTextField) field1.dataComponent;
		searchPane.add(field1);
		
		FormField field2 = new FormField("Surname", "");
		findTextSurname = (CustomTextField) field2.dataComponent;
		searchPane.add(field2);
		
		FormField field3 = new FormField("Postcode", "");
		findTextPostcode = (CustomTextField) field3.dataComponent;
		searchPane.add(field3);
		
		FormField field4 = new FormField("House Num", "");
		findTextHouseNum = (CustomTextField) field4.dataComponent;
		searchPane.add(field4);

		FormField field5 = new FormField("DOB", "");
		findTextDOB = (CustomTextField) field5.dataComponent;
		searchPane.add(field5);
		
		searchPane.add(createControlPane());

		return searchPane;
	}
	
	private JPanel createControlPane() {
		JPanel controlPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		controlPane.setOpaque(false);
		
		controlPane.add(createClearButton());
		controlPane.add(createSearchButton());
		
		return controlPane;
	}

	private CustomButton createClearButton() {
		CustomButton clearButton = new CustomButton("Clear");
		clearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clear();
			}
		});
		
		return clearButton;
	}

	private String[][] handleSearch(ResultSet searchInput) throws Exception {
		ArrayList<String> allPatientIDs = new ArrayList<String>();
		ArrayList<String> allFirstNames = new ArrayList<String>();
		ArrayList<String> allSecondNames = new ArrayList<String>();
		ArrayList<String> allDOB = new ArrayList<String>();
		ArrayList<String> allContactNumbers = new ArrayList<String>();
		ArrayList<String> allHouseNums = new ArrayList<String>();
		ArrayList<String> allPostCodes = new ArrayList<String>();
		
		int count = 0;
		while (count < 10 && searchInput.next()) {
			allPatientIDs.add(Integer.toString(searchInput.getInt(1)));
			allFirstNames.add(searchInput.getString(3));
			allSecondNames.add(searchInput.getString(4));
			allDOB.add(searchInput.getDate(5).toString());
			allContactNumbers.add(searchInput.getString(6));
			allHouseNums.add(searchInput.getString(7));
			allPostCodes.add(searchInput.getString(8));
			count++;
		}
		
		String[][] resultsBack = new String[allPatientIDs.size()][7];
		
		for (int i = 0; i < resultsBack.length; i++) {
			resultsBack[i][0] = allPatientIDs.get(i);
			resultsBack[i][1] = allFirstNames.get(i);
			resultsBack[i][2] = allSecondNames.get(i);
			resultsBack[i][3] = allDOB.get(i);
			resultsBack[i][4] = allContactNumbers.get(i);
			resultsBack[i][5] = allHouseNums.get(i);
			resultsBack[i][6] = allPostCodes.get(i);
		}
		return resultsBack;
	}

	private void displayResults(String[][] data) {
		displayPane.removeAll();
		
		for (String[] strs: data) {		
			displayPane.add(new ResultRowPane(strs));
		}
		
		displayPane.revalidate();
		displayPane.repaint();
	}
	
	private class ResultRowPane extends JPanel {
				
		public ResultRowPane(String[] data) {
			super(new GridLayout(1, 4));
			
			setBorder(new CompoundBorder(
					new CompoundBorder(
							new MatteBorder(5, 10, 5, 10, new Color(90, 90, 90)),
							new LineBorder(new Color(150, 150, 150), 1)),
					new EmptyBorder(5, 10, 5, 10)));
			setBackground(new Color(60, 60, 60));
			
			addMouseListener(new HoverListener(new Color(60, 60, 60), 
					new Color(120, 120, 120)));
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
					OverlayPane overlay = new OverlayPane(rootPane, new JPanel());
					PatientDetailsPane detailspane = new PatientDetailsPane(convertToHashMapp(data), overlay);
					
					overlay.setContentPane(detailspane);
					overlay.setConstraints(800, 600, 2, 2);
					overlay.setTitle(data[1] + " " + data[2], "Patient details");
					overlay.show();
				}
			});
			
			addData(data);
		}
		
		private HashMap<String, String> convertToHashMapp(String[] data) {
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("PatientID", data[0]);
			map.put("PatientName", data[1] + " " + data[2]);
			map.put("PatientDOB", data[3]);
			map.put("ContactNumber", data[4]);
			map.put("HouseNumber", data[5]);
			map.put("Postcode", data[6]);
			
			return map;
		}
		
		private void addData(String[] data) {
			for (int i = 2; i < data.length; i++) {
				JLabel label = null;
				
				if (i == 2) {
					label = new JLabel(data[1] + " " + data[2]);
				} else {
					label = new JLabel(data[i]);
				}
				
				label.setForeground(new Color(255, 160, 0));
				label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 15));
				
				add(label);
			}
		}
		
	}

}
