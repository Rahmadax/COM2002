package calendar;

import java.util.Date;

public abstract class TimeSlot {
	
	protected Date startDate;
	protected Date endDate;
	
	protected TimeSlot(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
}
