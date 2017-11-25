package calendar;

import java.util.Date;

public class EmptySlot extends TimeSlot {

	private String partner;
	
	public EmptySlot(Date startDate, Date endDate, String partner) {
		super(startDate, endDate);
		this.partner = partner;
	}
	
	public String getPartner() {
		return partner;
	}

}
