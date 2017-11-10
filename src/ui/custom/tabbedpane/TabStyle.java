package ui.custom.tabbedpane;

import javax.swing.JPanel;

public abstract class TabStyle {

	protected abstract JPanel createTabsPane();
	protected abstract JPanel createTabPane(String name);
	protected abstract void changeToSelectedState(JPanel tabPane);
	protected abstract void changeToUnselectedState(JPanel tabPane);

}
