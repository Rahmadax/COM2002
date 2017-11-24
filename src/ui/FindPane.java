package ui;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import ui.popup.LoadingPane;
import ui.popup.OverlayPane;




public class FindPane extends JPanel {	
	public FindPane() {
		this.setLayout(new GridLayout(1,3));
		setBackground(new Color(90,90,90));
		JPanel findPane = new JPanel(new GridLayout(5,1));	
		for (int i=0; i<5; i++){
			findPane.add(new SearchBarPane(i));
			
		}
		this.add(findPane);
	}
	public class SearchBarPane extends JPanel {		
		private JTextField findTextForename;	
		private JTextField findTextSurname;	
		private JTextField findTextPostcode;	
		private JTextField findTextHouseNum;	
		private JTextField findTextDOB;	

		private JButton searchForename;
		private JButton searchSurname;
		private JButton searchPostcode;
		private JButton searchHouseNum;
		private JButton searchDOB;

		private DefaultListModel<String> model;

		
		public SearchBarPane(int searchBarNum) {
			JPanel searchPane = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			ActionHandler handler = new ActionHandler();
			
			gbc.gridx = 0;
			gbc.gridy = searchBarNum;
			gbc.insets = new Insets(2,2,2,2);
			if(searchBarNum == 0) {
				searchPane.add(new JLabel("Forename: "), gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1;
				JTextField findTextForename = new JTextField(20);
				searchPane.add(findTextForename, gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;
				JButton searchForename = new JButton("Search");
				searchPane.add(searchForename, gbc);
				searchForename.addActionListener(handler);
				findTextForename.addActionListener(handler);
			}else if (searchBarNum == 1) {
				searchPane.add(new JLabel("Surname: "), gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1;
				JTextField findTextSurname = new JTextField(20);
				searchPane.add(findTextSurname, gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;
				JButton searchSurname = new JButton("Search");
				searchPane.add(searchSurname, gbc);
				searchSurname.addActionListener(handler);
				findTextSurname.addActionListener(handler);
			}else if (searchBarNum == 2) {
				searchPane.add(new JLabel("Postcode: "), gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1;
				JTextField findTextPostcode = new JTextField(20);
				searchPane.add(findTextPostcode, gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;
				JButton searchPostcode = new JButton("Search");
				searchPane.add(searchPostcode, gbc);
				searchPostcode.addActionListener(handler);
				findTextPostcode.addActionListener(handler);
			}else if (searchBarNum == 3) {
				searchPane.add(new JLabel("House Num: "), gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1;
				JTextField findTextHouseNum = new JTextField(20);
				searchPane.add(findTextHouseNum, gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;
				JButton searchHouseNum = new JButton("Search");
				searchPane.add(searchHouseNum, gbc);
				searchHouseNum.addActionListener(handler);
				findTextHouseNum.addActionListener(handler);
			}else if (searchBarNum == 4) {
				searchPane.add(new JLabel("Date of Birth: "), gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1;
				JTextField findTextDOB = new JTextField(20);
				searchPane.add(findTextDOB, gbc);
				gbc.gridx++;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;
				JButton searchDOB = new JButton("Search");
				searchPane.add(searchDOB, gbc);
				searchDOB.addActionListener(handler);
				findTextDOB.addActionListener(handler);
			}
			add(searchPane, BorderLayout.NORTH);	
		}
		
		public class ActionHandler implements ActionListener {				
			@Override 
			public void actionPerformed(ActionEvent e) {
				model.removeAllElements();
				
				String searchTextForename = findTextForename.getText();
				String searchTextSurname = findTextSurname.getText();
				String searchTextPostcode =  findTextPostcode.getText();
				String searchTextHouseNum = findTextHouseNum.getText();
				String searchTextDOB = findTextDOB.getText();
				
			}	
	}
	public class ResultsBarPane extends JPanel{
		
		public ResultsBarPane(DefaultListModel<String> model) {			
			model = new DefaultListModel<>();
			JList list = new JList(model);
			JScrollPane scrollPane = new JScrollPane(list);		
		}

		
	}
	public class furtherDetailsPane extends JPanel{
		
	}

}