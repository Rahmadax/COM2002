package ui.custom.tabbedpane;

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
	
	private int currentTabIndex;
	
	private JPanel tabsPane;
	private JPanel contentPane;

	private ArrayList<JPanel> tabList;
	private ArrayList<JPanel> contentList;
	
	private TabStyle tabStyle;
	private ChangeTabListener tabListener;
	
	public CustomTabbedPane(TabStyle tabStyle) {
		super(new BorderLayout());
		this.tabStyle = tabStyle;
		
		tabListener = new ChangeTabListener() {
			@Override
			public void listen(JPanel fromTab, JPanel toTabPane) {
				// empty listener
			}
		};
		
		tabList = new ArrayList<JPanel>();
		contentList = new ArrayList<JPanel>();
		
		tabsPane = tabStyle.createTabsPane();
		contentPane = new JPanel(new BorderLayout());
		
		add(tabsPane, BorderLayout.NORTH);
		add(contentPane, BorderLayout.CENTER);
	}
	
	public CustomTabbedPane() {
		this(new DefaultTabStyle());
	}
	
	public abstract class ChangeTabListener {
		public abstract void listen(JPanel fromTab, JPanel toTabPane);
	}
	
	public void addTab(String name, JPanel contentPane) {
		JPanel tabPane = tabStyle.createTabPane(name);
		
		tabPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int tabIndex = tabList.indexOf(tabPane);
				
				if (tabIndex != currentTabIndex) {
					selectTab(tabIndex);
				}
			}
		});
		
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

	public JPanel getTabsInstance() {
		return tabsPane;
	}
	
	public JPanel getCurrentContentPane() {
		return contentList.get(currentTabIndex);
	}

	public void setChangeTabListener(ChangeTabListener tabListener) {
		this.tabListener = tabListener;
	}

	public void selectTab(int tabIndex) {
		JPanel oldContentPane = contentList.get(currentTabIndex);
		JPanel oldTabPane = tabList.get(currentTabIndex);
		JPanel newContentPane = contentList.get(tabIndex);
		JPanel newTabPane = tabList.get(tabIndex);
		
		tabStyle.changeToUnselectedState(oldTabPane);
		tabStyle.changeToSelectedState(newTabPane);
		
		if (tabList.size() > 1) {
			contentPane.remove(oldContentPane);
		}

		tabListener.listen(oldTabPane, newTabPane);
		currentTabIndex = tabIndex;
		contentPane.add(newContentPane, BorderLayout.CENTER);
		contentPane.revalidate();
		contentPane.repaint();
	}

}
