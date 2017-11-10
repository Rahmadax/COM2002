package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import calendar.EmptySlot;
import ui.layout.CenteredPane;
import ui.listener.HoverListener;

public class EmptySlotPane extends TimeSlotPane {
	
	private static final Color BACKGROUND_COLOR = new Color(255, 220, 150);
	private static final Color HOVER_COLOR = new Color(255, 200, 120);
	
	public EmptySlotPane(EmptySlot timeSlot) {
		super(timeSlot);
		
		setBackground(BACKGROUND_COLOR);
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
