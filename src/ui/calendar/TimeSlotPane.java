package ui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.JPanel;

import calendar.TimeSlot;
import listener.HoverListener;

public abstract class TimeSlotPane extends JPanel {
	
	public static final int HEIGHT_SCALE = 25;
	
	protected int height;
	protected TimeSlot timeSlot;

	protected TimeSlotPane(TimeSlot timeSlot) {
		super();
		
		this.timeSlot = timeSlot;
		height = calculateHeight();
		
		setPreferredSize(new Dimension(0, height));
	}
	
	private int calculateHeight() {
		int interval = (int) (timeSlot.getEndDate().getTime() -
				timeSlot.getStartDate().getTime());
		
		return interval / (HEIGHT_SCALE * 1000);
	}
	
}
