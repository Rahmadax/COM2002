package ui.popup;

import java.awt.Color;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

public class LoadingPane extends OverlayPane {

	public LoadingPane(JRootPane rootPane) {
		this(rootPane, createLoadingPane());
	}
	
	private LoadingPane(JRootPane rootPane, JPanel contentPane) {
		super(rootPane, contentPane);

		disableTitlePane();
		disableOutOfBoundsClose();
		setConstraints(400, 210, 0, 0);
	}
	
	private static JPanel createLoadingPane() {
		JPanel loadingPane = new JPanel();
		loadingPane.setBackground(new Color(100, 100, 100));
		
		String loadingImagePath = 
				System.getProperty("user.dir") + "\\lib\\images\\loading.gif";
		
		ImageIcon image = new ImageIcon(loadingImagePath);
    	Image scaleImage = image.getImage().getScaledInstance(
    			150, 150,Image.SCALE_DEFAULT);	 
    	image.setImage(scaleImage);

		loadingPane.add(new JLabel(image));
		
		return loadingPane;
	}

}
