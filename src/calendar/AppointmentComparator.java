package calendar;

import java.util.Comparator;

public class AppointmentComparator implements Comparator<Appointment> {

	@Override
	public int compare(Appointment a1, Appointment a2) {
		return a1.startDate.compareTo(a2.endDate);
	}

}
