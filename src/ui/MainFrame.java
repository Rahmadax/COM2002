package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import ui.calendar.CalendarPane;
import ui.custom.tabbedpane.CustomTabbedPane;
import ui.custom.tabbedpane.TitleTabStyle;

public class MainFrame extends JFrame {
	
	public static MainFrame program;
	public static SplashScreen splashScreen;
	
	public static void main(String[] args) {
		splashScreen = new SplashScreen();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				program = new MainFrame();
			}
	    });
	}
	
	private MainFrame() {
		setTitle("Sheffield Dental Care");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(true);
	    setMinimumSize(new Dimension(1024, 768));

	    SwingUtilities.invokeLater(new Runnable () {
	    	@Override
	    	public void run() {
	    		addComponents();
	    		
	    		splashScreen.setVisible(false);
				program.setVisible(true);
	    	}
	    });
	}
	
	private void addComponents() {
		// initialize root pane for pop-ups
		JRootPane rootPane = new JRootPane();
		
		// glass pane for pop-ups
	    JPanel glass = (JPanel) rootPane.getGlassPane();
	    glass.setLayout(null);
	    glass.setVisible(true);
	    
	    // add the calendar pane with the current date
	    setContentPane(rootPane);
	    
	    // main frame container to support custom pop-ups
	    rootPane.setContentPane(createContentPane());
	}
	
	private JPanel createContentPane() {
		// initialize tabs objects
	    CustomTabbedPane mainTabbedPane = new CustomTabbedPane(new TitleTabStyle());
	    CustomTabbedPane calendarTabbedPane	 = new CustomTabbedPane();
	    JPanel mainTabsPane = mainTabbedPane.getTabsInstance();
	    JPanel calendarTabsPane = calendarTabbedPane.getTabsInstance();
	    
	    // add calendar tabs
	    calendarTabbedPane.addTab("Dentist", new CalendarPane(Calendar.getInstance(Locale.UK)));
	    calendarTabbedPane.addTab("Hygienist", new CalendarPane(Calendar.getInstance(Locale.UK)));
	    
	    // add main tabs
	    mainTabbedPane.addTab("Calendar", calendarTabbedPane);
	    mainTabbedPane.addTab("Register", new JPanel());
	    mainTabbedPane.addTab("Find", new JPanel());
	    
	    // remove standard location of tabs
	    mainTabbedPane.remove(mainTabsPane);
	    calendarTabbedPane.remove(calendarTabsPane);

	    // anchor calendar tabs on bottom of container
	    JPanel southContainer = new JPanel(new BorderLayout());
	    southContainer.setBackground(new Color(60, 60, 60));
	    southContainer.add(calendarTabsPane, BorderLayout.SOUTH);
	    
	    // create custom container for tabs panes
	    JPanel tabsContainer = new JPanel(new BorderLayout());
	    tabsContainer.add(mainTabsPane, BorderLayout.CENTER);
	    tabsContainer.add(southContainer, BorderLayout.WEST);
	    
	    // add and remove calendar tabs according to main tabs,
	    // while listening to tab changes
	    mainTabbedPane.setChangeTabListener(mainTabbedPane.new ChangeTabListener() {
			@Override
			public void listen(JPanel fromTab, JPanel toTabPane) {
				JLabel label = (JLabel) toTabPane.getComponent(0);
				
				if (label.getText() == "Calendar") {
					tabsContainer.add(southContainer, BorderLayout.WEST);
				} else if (southContainer.getParent() != null) {
					tabsContainer.remove(southContainer);
				}
			}
	    });

	    // content container
	    JPanel container = new JPanel(new BorderLayout());
	    container.add(tabsContainer, BorderLayout.NORTH);
	    container.add(mainTabbedPane, BorderLayout.CENTER);
	    
	    return container;
	}

}
