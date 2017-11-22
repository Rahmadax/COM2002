package ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SplashScreen extends JFrame {

	public SplashScreen() {
		super();
		
		setUndecorated(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	    setBounds((screenSize.width - 960) / 2, (screenSize.height - 540) / 2, 
	    		960, 540);
	    	    
	    try {
	    	BufferedImage image = ImageIO.read(
	    			new File(System.getProperty("user.dir") + "\\lib\\images\\splash.jpg"));
	    	Image scaleImage = image.getScaledInstance(
	    			960, 540,Image.SCALE_DEFAULT);	 

			add(new JLabel(new ImageIcon(scaleImage)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    setVisible(true);
	}
	
}
