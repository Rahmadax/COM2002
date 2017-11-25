package ui;
import ui.form.register.*;
import mysql.MySQLAccess;
import mysql.query.AddressQuery;
import mysql.query.PatientQuery;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ui.MainFrame;
import ui.ModeUI;
import ui.custom.CustomComboBox;
import ui.custom.button.CustomButton;
import ui.popup.ErrorPane;
import ui.popup.LoadingPane;
import ui.popup.OverlayPane;
import ui.popup.SuccessPane;




public class FindPane extends JPanel {	
	private DefaultListModel<String> model;
	private String[][] data;

	public FindPane() {
		this.setLayout(new GridLayout(1,3));
		setBackground(new Color(90,90,90));		
		this.add(new SearchBarPane());	
		if(data[0][0] != "") {
			System.out.println(data);
		}
	}
	
	public class SearchBarPane extends JPanel {		
		private JTextField findTextForename;	
		private JTextField findTextSurname;	
		private JTextField findTextPostcode;	
		private JTextField findTextHouseNum;	
		private JTextField findTextDOB;	
		private CustomButton searchButton;

		
		
	
		
		public SearchBarPane() {
			JPanel searchPane = new JPanel(new GridLayout(6,2));
			
			searchPane.add(new JLabel("Forename: ")); 
			findTextForename = new JTextField(20);
			searchPane.add(findTextForename);			
			searchPane.add(new JLabel("Surname: "));
			findTextSurname = new JTextField(20);
			searchPane.add(findTextSurname);
			searchPane.add(new JLabel("Postcode: "));
			findTextPostcode = new JTextField(20);
			searchPane.add(findTextPostcode);
			searchPane.add(new JLabel("House Num: "));
			findTextHouseNum = new JTextField(20);
			searchPane.add(findTextHouseNum);
			searchPane.add(new JLabel("DOB: "));
			findTextDOB = new JTextField(20);
			searchPane.add(findTextDOB);
		    searchPane.add(new JLabel("      "));		    
		    CustomButton searchButton = new CustomButton("Search", 
					CustomButton.DEFAULT_STYLE);
			searchButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					model.removeAllElements();
					String searchTextForename = findTextForename.getText();
					String searchTextSurname = findTextSurname.getText();
					System.out.println(searchTextForename);					
					String searchTextPostcode =  findTextPostcode.getText();
					String searchTextHouseNum = findTextHouseNum.getText();
					String searchTextDOB = findTextDOB.getText();
					try {
						MySQLAccess access = new MySQLAccess();
						PatientQuery query = new PatientQuery(access);
					if(searchTextForename != "" && searchTextSurname == "" && searchTextPostcode == "" && searchTextHouseNum == "" && searchTextDOB == "") {
						ResultSet resultSet = query.findWithFirstName(searchTextForename);
						System.out.println(resultSet);						
						data = searchHandler(resultSet);
					} else if(searchTextForename == "" && searchTextSurname != "" && searchTextPostcode == "" && searchTextHouseNum == "" && searchTextDOB == ""){
						ResultSet resultSet = query.findWithLastName(searchTextSurname);
						System.out.println(resultSet);						
						data = searchHandler(resultSet);						
					} else if(searchTextForename != "" && searchTextSurname != "" && searchTextPostcode == "" && searchTextHouseNum == "" && searchTextDOB == ""){
						ResultSet resultSet = query.findWithWholeName(searchTextForename,searchTextSurname);
						System.out.println(resultSet);						
						data = searchHandler(resultSet);
					}  else if(searchTextForename != "" && searchTextSurname == "" && searchTextPostcode == "" && searchTextHouseNum == "" && searchTextDOB != ""){
						ResultSet resultSet = query.findWithFirstNameDOB(searchTextForename, searchTextDOB);
						System.out.println(resultSet);						
						data = searchHandler(resultSet);
					}  else if(searchTextForename != "" && searchTextSurname != "" && searchTextPostcode == "" && searchTextHouseNum == "" && searchTextDOB != ""){
						ResultSet resultSet = query.findWithWholeNameDOB(searchTextForename, searchTextSurname, searchTextDOB);
						System.out.println(resultSet);						
						data = searchHandler(resultSet);
					}  else if(searchTextForename == "" && searchTextSurname != "" && searchTextPostcode != "" && searchTextHouseNum == "" && searchTextDOB == ""){
						ResultSet resultSet = query.findWithLastNamePostCode(searchTextSurname, searchTextPostcode);
						System.out.println(resultSet);						
						data = searchHandler(resultSet);
					}
						else if(searchTextForename == "" && searchTextSurname != "" && searchTextPostcode != "" && searchTextHouseNum != "" && searchTextDOB == ""){
								ResultSet resultSet = query.findWithLastNamePostCodeHouseNum(searchTextSurname, searchTextPostcode, searchTextHouseNum);
								System.out.println(resultSet);						
								data = searchHandler(resultSet);
					}
					access.close();
						}catch(Exception t) {
							t.printStackTrace();
							System.err.println("WTF!!!");
					}			
				}
			});
			searchPane.add(searchButton);
			
			add(searchPane, BorderLayout.NORTH);
		}		
		String[][] searchHandler(ResultSet searchInput) throws Exception{			
			String[] allPatientIDs = new String[10];
			String[] allFirstNames = new String[10];
			String[] allSecondNames = new String[10];
			String[] allDOB = new String[10];
			String[] allContactNumbers = new String[10];
			String[] allHouseNums = new String[10];
			String[] allPostCodes = new String[10];
			while(searchInput.next()){
				allPatientIDs[searchInput.getRow()] = Integer.toString(searchInput.getInt(1));
				allFirstNames[searchInput.getRow()] = searchInput.getString(2);
				allSecondNames[searchInput.getRow()] = searchInput.getString(3);
				allDOB[searchInput.getRow()] = searchInput.getDate(4).toString();
				allContactNumbers[searchInput.getRow()] = (searchInput.getString(5));
				allHouseNums[searchInput.getRow()] = searchInput.getString(6);
				allPostCodes[searchInput.getRow()] = searchInput.getString(7);
			}
			String[][] resultsBack = new String[10][7];
			for(int i = 0; i < 10; i++) {
				resultsBack[i][0] = allPatientIDs[i];
				resultsBack[i][1] = allFirstNames[i];
				resultsBack[i][2] = allSecondNames[i];
				resultsBack[i][3] = allDOB[i];
				resultsBack[i][4] = allContactNumbers[i];
				resultsBack[i][5] = allHouseNums[i];
				resultsBack[i][6] = allPostCodes[i];
			}
			return resultsBack;
		}
	}
	
}

