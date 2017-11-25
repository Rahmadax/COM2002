package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.CellRendererPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.metal.MetalComboBoxButton;

public class CustomComboBox extends JComboBox<String> {
	
	public CustomComboBox(String[] strs) {
		super(strs);

		setFont(new Font("Serif", Font.BOLD, 20));
		setBackground(new Color(80, 80, 80));
		
		setRenderer(new CustomRenderer());
		setEditor(new CustomEditor());
		
		setEditable(true);
	}
	
	private class CustomEditor extends BasicComboBoxEditor {
		
		private JLabel label = new JLabel();
		private JPanel panel = new JPanel();
		private Object selectedItem;
		
		public CustomEditor() {
			super();
			
			label.setOpaque(false);
	        label.setFont(new Font("Arial", Font.BOLD, 18));
	        label.setForeground(new Color(255, 160, 0));
	         
	        panel.setLayout(new GridBagLayout());
	        panel.setBorder(new LineBorder(new Color(255, 160, 0), 1));
	        
	        panel.add(label);
	        panel.setBackground(new Color(80, 80, 80));
	    }
	     
	    public Component getEditorComponent() {
	        return this.panel;
	    }
	     
	    public Object getItem() {
	        return this.selectedItem.toString();
	    }

	    public void setItem(Object item) {
	        this.selectedItem = item;
	        label.setText(item.toString());
	    }
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
