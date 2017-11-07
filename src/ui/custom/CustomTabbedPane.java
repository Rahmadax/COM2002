package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class CustomTabbedPane extends JPanel {
	
	private int tabPadding = 5;
	private int currentTabIndex;
	
	private JPanel tabsPane;
	private JPanel contentPane;

	private ArrayList<JPanel> tabList;
	private ArrayList<JPanel> contentList;
	
	private static final Color LIGHT_GRAY = new Color(200, 200, 200);
	private static final Color DARK_GRAY = new Color(60, 60, 60);
	private static final Color GRAY = new Color(90, 90, 90);
	private static final Color ORANGE = new Color(255, 160, 0);
	
	public CustomTabbedPane() {
		super(new BorderLayout());
		
		tabList = new ArrayList<JPanel>();
		contentList = new ArrayList<JPanel>();
		
		tabsPane = createTabsPane();
		contentPane = new JPanel(new BorderLayout());
		
		add(tabsPane, BorderLayout.NORTH);
		add(contentPane, BorderLayout.CENTER);
	}
	
	public void addTab(String name, JPanel contentPane) {
		JPanel tabPane = createTabPane(name);
		
		// add tab to tabs pane
		tabsPane.add(tabPane);

		// add tab and content to list
		tabList.add(tabPane);
		contentList.add(contentPane);
		
		// select first tab to display
		if (tabList.size() == 1) {
			selectTab(0);
		}
	}
	
	private JPanel createTabsPane() {
		JPanel tabsPane = new JPanel(new FlowLayout(
				FlowLayout.LEFT, 5, 0));
		tabsPane.setBackground(DARK_GRAY);
		tabsPane.setBorder(new MatteBorder(0, 0, 2, 0, ORANGE));
		
		return tabsPane;
	}
	
	private JPanel createTabPane(String name) {
		JPanel newTabPane = new JPanel();
		newTabPane.setBackground(GRAY);
		newTabPane.setBorder(new EmptyBorder(
				tabPadding, 2 * tabPadding, tabPadding, 2 * tabPadding));
		
		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(new Font(
				nameLabel.getFont().getFontName(), Font.BOLD, 15));
		nameLabel.setForeground(LIGHT_GRAY);
		newTabPane.add(nameLabel);
		
		newTabPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int tabIndex = tabList.indexOf(newTabPane);
				
				if (tabIndex != currentTabIndex) {
					selectTab(tabIndex);
				}
			}
		});
		
		return newTabPane;
	}
	
	private void changeTabColors(JPanel tabPane, Color bg, Color fg) {
		JLabel tabLabel = (JLabel) tabPane.getComponent(0);
		
		tabPane.setBackground(bg);
		tabLabel.setForeground(fg);
	}
	
	private void selectTab(int tabIndex) {
		JPanel oldContentPane = contentList.get(currentTabIndex);
		JPanel oldTabPane = tabList.get(currentTabIndex);
		JPanel newContentPane = contentList.get(tabIndex);
		JPanel newTabPane = tabList.get(tabIndex);
		
		changeTabColors(oldTabPane, GRAY, LIGHT_GRAY);
		changeTabColors(newTabPane, ORANGE, DARK_GRAY);
		
		if (tabList.size() > 1) {
			contentPane.remove(oldContentPane);
		}
		
		contentPane.add(newContentPane, BorderLayout.CENTER);
		
		currentTabIndex = tabIndex;
		
		oldTabPane.setBackground(GRAY);
		newTabPane.setBackground(ORANGE);

		contentPane.revalidate();
		contentPane.repaint();
	}

}
