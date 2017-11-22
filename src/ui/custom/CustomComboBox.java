package ui.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.CellRendererPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalComboBoxButton;

public class CustomComboBox extends JComboBox {
	
	public CustomComboBox(String[] strs) {
		super(strs);
		
		setFocusable(false);
		setFont(new Font("Serif", Font.BOLD, 20));
		setBackground(new Color(80, 80, 80));
		setForeground(new Color(255, 160, 0));
		
		setRenderer(new CustomRenderer());
	}

	private class CustomRenderer extends JPanel implements ListCellRenderer {

		CustomRenderer() {
			super(new FlowLayout(FlowLayout.LEFT, 10, 5));
		}
		
		@Override
		public Component getListCellRendererComponent(JList list, Object obj,
				int index, boolean isSelected, boolean cellHasFocus) {
			String name = (String) obj;
            
            setText(name);
            
            if (isSelected) {
                setBackground(new Color(150, 50, 0));
            } else {
                setBackground(list.getBackground());
            }
			
			return this;
		}

		private void setText(String name) {
			if (getComponents().length != 0) {
				remove(0);
			}
			
			JLabel label = new JLabel(name);
			label.setForeground(new Color(255, 160, 0));
			label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, 18));
			
			add(label);
			
			revalidate();
			repaint();
		}
		
	}

}
