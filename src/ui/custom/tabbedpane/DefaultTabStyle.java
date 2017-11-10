package ui.custom.tabbedpane;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class DefaultTabStyle extends TabStyle {
	
	private int tabPadding = 5;
	
	private static final Color LIGHT_GRAY = new Color(200, 200, 200);
	private static final Color DARK_GRAY = new Color(60, 60, 60);
	private static final Color GRAY = new Color(90, 90, 90);
	private static final Color ORANGE = new Color(255, 160, 0);

	@Override
	protected JPanel createTabsPane() {
		JPanel tabsPane = new JPanel(new FlowLayout(
				FlowLayout.LEFT, 5, 0));
		tabsPane.setBackground(DARK_GRAY);
		tabsPane.setBorder(new MatteBorder(0, 0, 2, 0, ORANGE));
		
		return tabsPane;
	}

	@Override
	protected JPanel createTabPane(String name) {
		JPanel newTabPane = new JPanel();
		newTabPane.setBackground(GRAY);
		newTabPane.setBorder(new EmptyBorder(
				tabPadding, 2 * tabPadding, tabPadding, 2 * tabPadding));
		
		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(new Font(
				nameLabel.getFont().getFontName(), Font.BOLD, 15));
		nameLabel.setForeground(LIGHT_GRAY);
		newTabPane.add(nameLabel);
		
		return newTabPane;
	}

	@Override
	protected void changeToSelectedState(JPanel tabPane) {
		changeTabColors(tabPane, ORANGE, DARK_GRAY);
	}

	@Override
	protected void changeToUnselectedState(JPanel tabPane) {
		changeTabColors(tabPane, GRAY, LIGHT_GRAY);
	}
	
	private void changeTabColors(JPanel tabPane, Color bg, Color fg) {
		JLabel tabLabel = (JLabel) tabPane.getComponent(0);
		
		tabPane.setBackground(bg);
		tabLabel.setForeground(fg);
	}

}
