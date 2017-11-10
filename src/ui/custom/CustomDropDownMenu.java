package ui.custom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import ui.listener.HoverListener;
import ui.popup.PopupPane;

public class CustomDropDownMenu extends CustomButton {

	private static final int OPTION_WIDTH   = 200;
	private static final int OPTION_HEIGHT  = 30;
	private static final int OPTION_BORDER  = 5;
	private static final int OPTION_PADDING = 5;
	private static final int TOP_BORDER     = 2;
	private static final int BOTTOM_BORDER  = 1;
	
	private JRootPane rootPane;
	private PopupPane dropDownPane;
	
	private ArrayList<JPanel> optionList;
	
	public CustomDropDownMenu(String name, JRootPane rootPane) {
		super(name);
		
		this.rootPane = rootPane;
		dropDownPane = createDropDownPane();
		optionList = new ArrayList<JPanel>();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (dropDownPane.isHidden()) {
					dropDownPane.show(recalculateLocation());
				} else {
					dropDownPane.hide();
				}
			}
		});
		
		dropDownPane.getGlass().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (!dropDownPane.isHidden()) {
					dropDownPane.show(recalculateLocation());
				}
			}
		});
		
		setPreferredSize(new Dimension(100, 40));
	}
	
	public void addOption(String name) {
		JPanel optionPane = createOptionPane(name);

		optionList.add(optionPane);
		dropDownPane.add(optionPane);
		
		recalculateSize();
	}
	
	private JPanel createOptionPane(String name) {
		JPanel optionPane = new JPanel();
		JLabel nameLabel = new JLabel(name);
		
		optionPane.addMouseListener(new HoverListener(
				new Color(80, 80, 80), new Color(150, 70, 0)));
		
		nameLabel.setForeground(new Color(255, 160, 0));
		optionPane.setBackground(new Color(80, 80, 80));
		
		optionPane.setPreferredSize(new Dimension(OPTION_WIDTH, OPTION_HEIGHT));
		optionPane.add(nameLabel);
		
		return optionPane;
	}
	
	private PopupPane createDropDownPane() {
		PopupPane dropDownPane = new PopupPane(rootPane);
		dropDownPane.setLayout(new FlowLayout(
				FlowLayout.CENTER, 2 * OPTION_PADDING, OPTION_PADDING));
		dropDownPane.setBackground(new Color(60, 60, 60));
		dropDownPane.setBorder(new MatteBorder(
				TOP_BORDER, 0, BOTTOM_BORDER, 0, new Color(255, 160, 0)));
		
		return dropDownPane;
	}
	
	private void recalculateSize() {
		int width = OPTION_WIDTH + 2 * OPTION_PADDING;
		int height = optionList.size() * (OPTION_HEIGHT + 2 * OPTION_PADDING) 
				+ TOP_BORDER + BOTTOM_BORDER - OPTION_PADDING;
		
		dropDownPane.setSize(width, height);
	}
	
	private Point recalculateLocation() {
		Dimension size = getPreferredSize();
		int x = size.width - (OPTION_WIDTH + 2 * OPTION_PADDING);
		int y = size.height - 1;
		
		Point absolute = SwingUtilities.convertPoint(
				this, new Point(x, y), dropDownPane.getGlass());
		
		return new Point(absolute.x, absolute.y);
	}
	
}
