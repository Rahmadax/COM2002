package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class CalendarPane extends JPanel {
	
	// how many weeks to select before the displayed week
	private static final int PICK_WEEKS_BEFORE = 5;
	
	// how many weeks to select after the displayed week
	private static final int PICK_WEEKS_AFTER = 25;
	
	private Calendar calendar;

	public CalendarPane(Calendar calendar) {
		super();
		
		this.calendar = calendar;
		this.calendar.set(Calendar.DAY_OF_WEEK, 2);

		setLayout(new BorderLayout(20, 20));
		setBorder(new EmptyBorder(20, 20, 20, 20));

		addComponents();
	}
	
	// the control button class to change the week (up or down)
	private class ControlButton extends JButton {
		ControlButton(String str, boolean up) {
			super(str);
			
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					changeWeek(up);
				}
			});
			
			setFont(new Font("Serif", Font.BOLD, 20));
			setPreferredSize(new Dimension(50, 40));
		}
	}
	
	// control combo box class to select a desired week
	private class DatePicker extends JComboBox {
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

			setFont(new Font("Serif", Font.BOLD, 20));
			setPreferredSize(new Dimension(150, 40));
		}
	}
	
	// add all content to the week pane
	private void addComponents() {
		JPanel container = new JPanel();
		JPanel controlPane = new JPanel();

		controlPane.add(new ControlButton("<", false));
		controlPane.add(new DatePicker(generateDates()));
		controlPane.add(new ControlButton(">", true));
		
		container.setLayout(new BorderLayout(0, 10));
		container.add(controlPane, BorderLayout.EAST);
		container.add(new DayLabelPane(calendar), BorderLayout.SOUTH);
		
		JScrollPane dayPane = new JScrollPane(new DayPane());
		dayPane.getVerticalScrollBar().setUnitIncrement(16);;
		dayPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dayPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
		add(container, BorderLayout.NORTH);
		add(dayPane, BorderLayout.CENTER);
	}
	
	// change week up or down
	private void changeWeek(boolean up) {	
		removeAll();
		
		if (up == true) {
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
		} else {
			calendar.add(Calendar.WEEK_OF_YEAR, -1);
		}
		
		addComponents();
		revalidate();
		repaint();
	}

	// change week by specific date
	private void changeWeek(Date date) {
		removeAll();
		
		calendar.setTime(date);
		
		addComponents();
		revalidate();
		repaint();
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

}
