package ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

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
	    	ImageIcon image = new ImageIcon(
	    			new URL("https://i.imgur.com/SZVTSAQ.jpg"));
	    	Image scaleImage = image.getImage().getScaledInstance(
	    			960, 540,Image.SCALE_DEFAULT);	 
	    	image.setImage(scaleImage);

			add(new JLabel(image));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    
	    setVisible(true);
	}
	
}
