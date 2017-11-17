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
		setMinWidth(400);
		setMinHeight(210);
		setScale(0);
	}
	
	private static JPanel createLoadingPane() {
		JPanel loadingPane = new JPanel();
		loadingPane.setBackground(new Color(100, 100, 100));
		
		ImageIcon image = new ImageIcon();
		try {
			image = new ImageIcon(
					new URL("https://image.ibb.co/kKveL6/ajax_loader_1.gif"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    	Image scaleImage = image.getImage().getScaledInstance(
    			150, 150,Image.SCALE_DEFAULT);	 
    	image.setImage(scaleImage);
		
		loadingPane.add(new JLabel(image));
		
		return loadingPane;
	}

}
