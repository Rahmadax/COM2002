package calendar;

import java.util.Date;

public class Appointment extends TimeSlot {

	private Status status;
	private Appointment rescheduledAppointment;
	
	private int patientID;
	private String partner;

	public Appointment(Date startDate, Date endDate, 
			String partner, int patientID) {
		super(startDate, endDate);
		
		this.patientID = patientID;
		this.partner = partner;
		
		status = Status.PENDING;
		rescheduledAppointment = null;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Appointment getRescheduledAppointment() {
		return rescheduledAppointment;
	}
	
	public void reschedule(Appointment rescheduledAppointment) {
		setStatus(Status.RESCHEDULED);
		this.rescheduledAppointment = rescheduledAppointment;
	}
	
	public int compareTo(Appointment that) {
		return this.startDate.compareTo(that.endDate);
	}

}
