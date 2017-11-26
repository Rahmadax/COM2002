package ui.form.patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.custom.CustomRowPane;
import ui.custom.CustomTextField;

abstract class FormData<T> extends JPanel {

	protected String dbField;
	protected JComponent dataComponent;
	protected String helper;
	
	public abstract T getValue();
	
	public JComponent getComponent() {
		return dataComponent;
	}

	protected FormData(JComponent dataComponent, String helper, String dbField) {
		super(new BorderLayout());
		setOpaque(false);
		
		this.dbField = dbField;
		this.helper = helper;
		this.dataComponent = dataComponent;
		
		CustomRowPane rowPane = new CustomRowPane();
		
		rowPane.addCell(createHelperPane(helper), 50.0);
		rowPane.addCell(createDataField(dataComponent) , 50.0);
		
		add(rowPane);
	}
	
	public void setRowDistribution(double dist1, double dist2) {
		removeAll();
		
		CustomRowPane rowPane = new CustomRowPane();
		
		rowPane.addCell(createHelperPane(helper), dist1);
		rowPane.addCell(createDataField(dataComponent) , dist2);
		
		add(rowPane);
		
		revalidate();
		repaint();
	}
	
	private JPanel createDataField(JComponent dataField) {
		JPanel fieldPane = new JPanel(new BorderLayout());
		fieldPane.setOpaque(false);
		
		JComponent field = dataField;
		
		JPanel container = new JPanel(new GridBagLayout());
		container.setOpaque(false);
		container.add(field);
		
		fieldPane.add(container, BorderLayout.WEST);
		
		return fieldPane;
	}

	private JPanel createHelperPane(String helper) {
		JPanel helperPane = new JPanel(new BorderLayout());
		helperPane.setOpaque(false);
		helperPane.setBorder(new EmptyBorder(0, 0, 0, 20));
		
		JLabel helperLabel = new JLabel(helper);
		helperLabel.setForeground(new Color(200, 200, 200));
		helperLabel.setFont(new Font(helperLabel.getFont().getFontName(), 
				Font.BOLD, 15));
		
		JPanel container = new JPanel(new GridBagLayout());
		container.setOpaque(false);
		container.add(helperLabel);
		
		helperPane.add(container, BorderLayout.EAST);
		
		return helperPane;
	}
	
}
