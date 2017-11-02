package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
 import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DayPane extends JPanel {
	
	private static final int GAP_BETWEEN_APPOINTMENTS = 2;
	private static final int GAP_BETWEEN_DAYS = 4;

	public DayPane() {
		super();
				
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		// add day panes for all week days
		for (int i = 0; i < 5; i++) {
			// anchor pane for top alignment
			JPanel anchorTopContainer = new JPanel();
			anchorTopContainer.setLayout(new BorderLayout());
			
			JPanel dayContainer = new JPanel();
			dayContainer.setLayout(new BoxLayout(dayContainer, BoxLayout.Y_AXIS));
			
			// add horizontal gap between appointments
			dayContainer.setBorder(
					new EmptyBorder(0, GAP_BETWEEN_DAYS / 2, 0, GAP_BETWEEN_DAYS / 2));
			
			// add random appointment panes (just for presentation)
			// TO BE CONTINUED
			for (int j = 0; j < 10; j++) {
				int randomNum = ThreadLocalRandom.current().nextInt(100, 200 + 1);
				
				dayContainer.add(new AppointmentPane(randomNum));
				
				// add vertical gap between appointments
				dayContainer.add(Box.createRigidArea(
						new Dimension(0, GAP_BETWEEN_APPOINTMENTS)));
			}
			
			anchorTopContainer.add(dayContainer, BorderLayout.NORTH);
			add(anchorTopContainer);
		}
	}
	
}
