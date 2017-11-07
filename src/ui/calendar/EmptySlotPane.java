package ui.calendar;

import java.awt.Color;

import calendar.EmptySlot;
import listener.HoverListener;

public class EmptySlotPane extends TimeSlotPane {

	public EmptySlotPane(EmptySlot timeSlot) {
		super(timeSlot);
		
		addMouseListener(new HoverListener(new Color(255, 220, 150), 
				new Color(255, 200, 120)));
		setBackground(new Color(255, 220, 150));
	}
	
}
