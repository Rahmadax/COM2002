package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;

import calendar.EmptySlot;
import ui.MainFrame;
import ui.form.book.BookPane;
import ui.layout.CenteredPane;
import ui.listener.HoverListener;

public class EmptySlotPane extends TimeSlotPane {
	
	private static final Color BACKGROUND_COLOR = new Color(255, 220, 150);
	private static final Color HOVER_COLOR = new Color(255, 200, 120);
	
	public EmptySlotPane(EmptySlot timeSlot) {
		super(timeSlot);
		
		setBackground(BACKGROUND_COLOR);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				MainFrame.mainMenu.selectTab(MainFrame.MENU_BOOK_INDEX);
				
				Calendar calendar = Calendar.getInstance(Locale.UK);
				calendar.setTime(timeSlot.getStartDate());

				BookPane bookPane = (BookPane) MainFrame.mainMenu.getCurrentContentPane();
				bookPane.setInitialTimes(timeSlot.getStartDate(), timeSlot.getEndDate());			
				bookPane.setInitialDate(calendar);
				bookPane.setInitialPartner(timeSlot.getPartner());
				
				setBackground(BACKGROUND_COLOR);
			}
		});
		addMouseListener(new HoverListener(BACKGROUND_COLOR, HOVER_COLOR));

		setLayout(new BorderLayout());
		add(createContentPane(), BorderLayout.CENTER);
	}
	
	private JPanel createContentPane() {
		JLabel label = new JLabel(
				"<html><center>Empty slot<br>(Click to book)</center></html>");
		label.setForeground(new Color(255, 140, 0));

		return new CenteredPane(label);
	}
	
}
