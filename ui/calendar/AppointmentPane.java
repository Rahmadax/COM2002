package ui.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import ui.OverlayPane;

public class AppointmentPane extends JPanel {
	
	// overlay pane instance to show the appointment details
	private OverlayPane overlay;
	
	// the appointment details pane
	private AppointmentDetailsPane detailsPane;

	public AppointmentPane(int t) {
		super();
		
		JPanel panel = this;
		detailsPane = new AppointmentDetailsPane();
		
		addMouseListener(new MouseAdapter() {
			// click listener to show the details pane overlay
			@Override
			public void mouseReleased(MouseEvent e) {
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);

				if (overlay == null) {
					overlay = new OverlayPane(topFrame, detailsPane);
				}

				if (overlay.isHidden()) {
					overlay.show(new Point(0, 0));
				} else {
					overlay.hide();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Color.RED);
				
				revalidate();
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Color.BLUE);
				
				revalidate();
				repaint();
			}
		});
		
		setLayout(new BorderLayout());
		
		addComponents();
				
		setBackground(Color.BLUE);
		setPreferredSize(new Dimension(50, t));
	}
	
	// TO BE CONTINUED
	private void addComponents() {
		add(createTimePane(), BorderLayout.NORTH);
	}
	
	private JPanel createTimePane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBackground(new Color(130, 130, 255));
		
		JLabel label1 = new JLabel("9:00 AM");
		JLabel label2 = new JLabel("10:00 AM");
		
		panel.add(label1, BorderLayout.WEST);
		panel.add(label2, BorderLayout.EAST);

		return panel;
	}

}
