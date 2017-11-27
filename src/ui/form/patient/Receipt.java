package ui.form.patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import mysql.query.CalculateReciept;
import ui.MainFrame;
import ui.listener.HoverListener;
import ui.popup.ErrorPane;
import ui.popup.OverlayContentPane;
import ui.popup.OverlayPane;

public class Receipt extends OverlayContentPane {

	public Receipt(OverlayPane overlay, int patientID) {
		super(overlay);
		setLayout(new BorderLayout());
		
		JPanel treatmentsPane = new JPanel();
		treatmentsPane.setOpaque(false);
		treatmentsPane.setLayout(new BoxLayout(treatmentsPane, BoxLayout.Y_AXIS));
		
		JPanel anchorTopContainer = new JPanel(new BorderLayout());
		anchorTopContainer.setBackground(new Color(90, 90, 90));
		anchorTopContainer.add(treatmentsPane, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(anchorTopContainer);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);;
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		
		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.add(scrollPane);
		
		CalculateReciept cr = new CalculateReciept();
		
		try {
			String[][] data = cr.getReciept(patientID);
			
			for (String[] strs: data) {
				if (strs[2].equals("Pre-Paid")) {
					treatmentsPane.add(new TreatmentsRowPane(strs));
				}
			}
			
			for (String[] strs: data) {
				if (strs[2].equals("Paid")) {
					treatmentsPane.add(new TreatmentsRowPane(strs));
				}
			}
		} catch (Exception e) {
			JRootPane rootPane = (JRootPane) MainFrame.program.getContentPane();
			getOverlay().hide();
			new ErrorPane(rootPane, "Cannot process receipt.").show();
		}
		
		add(container);
		
		setBackground(new Color(90, 90, 90));
	}
	
	private class TreatmentsRowPane extends JPanel {
		
		public TreatmentsRowPane(String[] data) {
			super(new GridLayout(1, 3));
			
			setBorder(new CompoundBorder(
					new CompoundBorder(
							new MatteBorder(10, 20, 5, 10, new Color(90, 90, 90)),
							new LineBorder(new Color(150, 150, 150), 1)),
					new EmptyBorder(10, 20, 10, 20)));
			setBackground(new Color(60, 60, 60));
			
			addMouseListener(new HoverListener(new Color(60, 60, 60), 
					new Color(120, 120, 120)));
			
			addData(data);
		}
		
		private void addData(String[] data) {			
			for (int i = 0; i < data.length; i++) {
				JLabel label = null;
				label = new JLabel(data[i]);
				
				label.setForeground(new Color(255, 160, 0));
				label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 15));
				
				add(label);
			}
		}
		
	}
	
}
