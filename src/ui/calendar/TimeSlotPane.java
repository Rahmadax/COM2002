package ui.calendar;

import java.awt.Dimension;

import javax.swing.JPanel;

import calendar.TimeSlot;

public abstract class TimeSlotPane extends JPanel {
	
	public static final int HEIGHT_SCALE = 25;
	
	protected int height;
	protected TimeSlot timeSlot;

	protected TimeSlotPane(TimeSlot timeSlot) {
		super();
		
		this.timeSlot = timeSlot;
		height = 150;
		
		setPreferredSize(new Dimension(0, height));
	}
	
	/*private int calculateHeight() {
		int interval = (int) (timeSlot.getEndDate().getTime() -
				timeSlot.getStartDate().getTime());
		
		return interval / (HEIGHT_SCALE * 1000);
	}*/
	
}
