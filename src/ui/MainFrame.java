package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import ui.calendar.CalendarPane;
import ui.custom.tabbedpane.CustomTabbedPane;
import ui.custom.tabbedpane.TitleTabStyle;
import ui.form.book.BookPane;
import ui.form.register.RegisterPane;
import ui.popup.PopupPane;

public class MainFrame extends JFrame {
	
	public static MainFrame program;
	
	public static SplashScreen splashScreen;
	public static ModeUI mode;
	
	public static CustomTabbedPane mainMenu;
	public static int MENU_CALENDAR_INDEX = 0;
	public static int MENU_REGISTER_INDEX = 1;
	public static int MENU_FIND_INDEX = 2;
	public static int MENU_BOOK_INDEX = 3;

	public MainFrame(ModeUI mode) {
		MainFrame.mode = mode;
		
		new MainFrame();
	}

	private MainFrame() {
		splashScreen = new SplashScreen();
		program = this;

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
		rootPane.setGlassPane(new JLayeredPane());
		
		// glass pane for pop-ups
		JLayeredPane glass = (JLayeredPane) rootPane.getGlassPane();
	    glass.setLayout(null);
	    glass.setVisible(true);
	    
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
	    calendarTabbedPane.addTab("Dentist", new CalendarPane(Calendar.getInstance(Locale.UK), "Dentist"));
	    calendarTabbedPane.addTab("Hygienist", new CalendarPane(Calendar.getInstance(Locale.UK), "Hygienist"));

	    // add main tabs
	    mainTabbedPane.addTab("Calendar", calendarTabbedPane);
	    
	    if (mode == ModeUI.SECRETARY) {
	    	mainTabbedPane.addTab("Register", new RegisterPane());
		    mainTabbedPane.addTab("Find", new FindPane());
		    mainTabbedPane.addTab("Book", new BookPane());
	    }
	    
	    mainMenu = mainTabbedPane;
	    
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
