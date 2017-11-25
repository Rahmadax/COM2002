package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ui.MainFrame;
import ui.ModeUI;
import ui.custom.CustomComboBox;
import ui.custom.button.CustomButton;
import ui.form.book.BookPane;
import ui.popup.LoadingPane;
import ui.popup.OverlayPane;

public class CalendarPane extends JPanel {
	
	// how many weeks to select before the displayed week
	private static final int PICK_WEEKS_BEFORE = 5;
	
	// how many weeks to select after the displayed week
	private static final int PICK_WEEKS_AFTER = 25;
	
	private Calendar calendar;

	private String partner;
	
	public CalendarPane(Calendar calendar, String partner) {
		super(new BorderLayout(20, 20));
		
		this.partner = partner;
		this.calendar = calendar;
		this.calendar.set(Calendar.DAY_OF_WEEK, 2);

		setBackground(new Color(90, 90, 90));
		setBorder(new EmptyBorder(20, 20, 20, 20));
		
		addComponents();
	}

	// the control button class to change the week (up or down)
	private class ControlButton extends CustomButton {
		ControlButton(String str, boolean up) {
			super(str);
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (up) {
						calendar.add(Calendar.WEEK_OF_YEAR, 1);
					} else {
						calendar.add(Calendar.WEEK_OF_YEAR, -1);
					}
					
					changeWeek(calendar.getTime());
				}
			});
		}
	}
	
	// control combo box class to select a desired week
	private class DatePicker extends CustomComboBox {
		DatePicker(String[] strs) {
			super(strs);
						
			setSelectedIndex(PICK_WEEKS_BEFORE);
			
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
					
					try {
						changeWeek(dateFormatter.parse((String) getSelectedItem()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			});
			
			setPreferredSize(new Dimension(150, 40));
		}
	}

	public Calendar getCalendar() {
		return calendar;
	}
	
	// change week by specific date
	public void changeWeek(Date date) {
		JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
		LoadingPane loadingPane = new LoadingPane(rootPane);
		loadingPane.show();

		Thread queryThread = new Thread() {
			@Override
			public void run() {
				removeAll();
				
				calendar.setTime(date);
				addComponents();
				
				loadingPane.hide();
			}
	    };
	    queryThread.start();
	}
	
	// add all content to the week pane
	private void addComponents() {
		JPanel container = new JPanel();
		container.setOpaque(false);
		container.setLayout(new BorderLayout(0, 10));
		
		if (MainFrame.mode == ModeUI.SECRETARY) {
			container.add(createBookButton(), BorderLayout.WEST);
		}
		
		if (!isDateInCurrentWeek(calendar.getTime())) {
			String alignment;
			
			if (MainFrame.mode == ModeUI.SECRETARY) {
				alignment = BorderLayout.CENTER;
			} else {
				alignment = BorderLayout.WEST;
			}
			
			container.add(createBackButton(), alignment);
		}
		
		container.add(createControlPane(), BorderLayout.EAST);
		container.add(new DayLabelPane((Calendar) calendar.clone()), 
				BorderLayout.SOUTH);
        
		add(container, BorderLayout.NORTH);
		revalidate();
		repaint();

		try {
			add(createDayPane(), BorderLayout.CENTER);
			
			revalidate();
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JScrollPane createDayPane() {
		JScrollPane dayPane = new JScrollPane(
				new DayPane((Calendar) calendar.clone(), partner));
		
		dayPane.setOpaque(false);
		dayPane.setBorder(new LineBorder(new Color(255, 160, 0), 1));
		dayPane.getVerticalScrollBar().setUnitIncrement(16);;
		dayPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dayPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	
		return dayPane;
	}
	
	private JPanel createControlPane() {
		JPanel controlPane = new JPanel();
		controlPane.setOpaque(false);
		controlPane.add(new ControlButton("<", false));
		controlPane.add(new DatePicker(generateDates()));
		controlPane.add(new ControlButton(">", true));
		
		return controlPane;
	}
	
	private JPanel createBackButton() {
		CustomButton backButton = new CustomButton("Back to current week");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				changeWeek(Calendar.getInstance(Locale.UK).getTime());
			}
		});
		
		JPanel backPane = new JPanel();
		backPane.setOpaque(false);
		backPane.add(backButton);
		
		return backPane;
	}
	
	private JPanel createBookButton() {
		CustomButton bookButton = new CustomButton("Book appoitnment", 
				CustomButton.REVERSED_STYLE);
		
		bookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				MainFrame.mainMenu.selectTab(MainFrame.MENU_BOOK_INDEX);
				BookPane bookPane = (BookPane) MainFrame.mainMenu.getCurrentContentPane();
				
				bookPane.setInitialDate(calendar);
				bookPane.setInitialPartner(partner);
			}
		});

		JPanel bookPane = new JPanel();
		bookPane.setOpaque(false);
		bookPane.add(bookButton);
		
		return bookPane;
	}

	// generate dates for combo box
	private String[] generateDates() {
		Calendar calendar = (Calendar) this.calendar.clone();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		String[] dates = new String[PICK_WEEKS_BEFORE + PICK_WEEKS_AFTER + 1];
		
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.add(Calendar.WEEK_OF_YEAR, -PICK_WEEKS_BEFORE);

		for (int i = 0; i <= PICK_WEEKS_BEFORE + PICK_WEEKS_AFTER; i++) {
			dates[i] = dateFormatter.format(calendar.getTime());
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
		}
		
		return dates;
	}
	
	private boolean isDateInCurrentWeek(Date date) {
		Calendar current = Calendar.getInstance(Locale.UK);
		
		int week = current.get(Calendar.WEEK_OF_YEAR);
		int year = current.get(Calendar.YEAR);
		
		Calendar target = Calendar.getInstance(Locale.UK);
		target.setTime(date);
		
		int targetWeek = target.get(Calendar.WEEK_OF_YEAR);
		int targetYear = target.get(Calendar.YEAR);
		
		return week == targetWeek && year == targetYear;
	}

}
