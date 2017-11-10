package ui.custom.tabbedpane;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import ui.listener.HoverListener;

public class TitleTabStyle extends TabStyle {
	
	private int tabPadding = 5;
	
	private static final Color LIGHT_GRAY = new Color(200, 200, 200);
	private static final Color GRAY = new Color(150, 150, 150);
	private static final Color DARK_GRAY = new Color(60, 60, 60);
	private static final Color ORANGE = new Color(255, 160, 0);
	
	@Override
	protected JPanel createTabsPane() {
		JPanel tabsPane = new JPanel(new FlowLayout(
				FlowLayout.RIGHT, 5, 0));
		tabsPane.setBackground(DARK_GRAY);
		tabsPane.setBorder(new MatteBorder(0, 0, 2, 0, ORANGE));
		
		return tabsPane;
	}

	@Override
	protected JPanel createTabPane(String name) {
		JPanel newTabPane = new JPanel();
		newTabPane.setOpaque(false);
		newTabPane.setBorder(new EmptyBorder(
				tabPadding, 2 * tabPadding, tabPadding, 5 * tabPadding));

		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(new Font(
				nameLabel.getFont().getFontName(), Font.BOLD, 28));
		nameLabel.setForeground(GRAY);
		
		newTabPane.addMouseListener(new HoverListener(GRAY, LIGHT_GRAY, nameLabel));
		newTabPane.add(nameLabel);
		
		return newTabPane;
	}

	@Override
	protected void changeToSelectedState(JPanel tabPane) {
		for(MouseListener listener: tabPane.getMouseListeners()) {
			if (listener instanceof HoverListener) {
				tabPane.removeMouseListener(listener);
			}
		}
		
		changeTabColors(tabPane, ORANGE);
	}

	@Override
	protected void changeToUnselectedState(JPanel tabPane) {	
		tabPane.addMouseListener(new HoverListener(
				GRAY, LIGHT_GRAY, tabPane.getComponent(0)));
		
		changeTabColors(tabPane, GRAY);
	}
	
	private void changeTabColors(JPanel tabPane, Color fg) {
		JLabel tabLabel = (JLabel) tabPane.getComponent(0);

		tabLabel.setForeground(fg);
	}

}
