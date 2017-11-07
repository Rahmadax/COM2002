package ui.animation;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.Timer;


public class FadeAnimation extends JPanel {

	static {
		// Reference: https://stackoverflow.com/a/41337344
        RepaintManager.setCurrentManager(new RepaintManager() {
            @Override
            public void addDirtyRegion(JComponent c, 
            		int x, int y, int w, int h) {
                Component cc = c;
                
                while (cc != null) {
                    if (cc instanceof FadeAnimation) {
                    	FadeAnimation p = (FadeAnimation) cc;
                        super.addDirtyRegion(p, 
                        		0, 0, p.getWidth(), p.getHeight());
                    }
                    
                    cc = cc.getParent();
                }
                
                super.addDirtyRegion(c, x, y, w, h);
            }
        });
    }
	
	private int duration = 1000;
	private float opacity = 0.0f;
	private long startTime;
	
	private JPanel component;
	private BufferedImage image;

	public FadeAnimation(JPanel component) {
		this.component = component;
		
		JPanel panel = this;
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				component.setPreferredSize(panel.getSize());
				
				component.revalidate();
				component.repaint();
			}
		});
		
		setOpaque(false);
		add(component);
	}
	
	public FadeAnimation(JPanel component, int duration) {
		this(component);
		
		this.duration = duration;
	}
	
	public void start(int delay) {
        Timer timer = new Timer(1000 / 60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long remainingTime = System.currentTimeMillis() - startTime;
                float progress = (float) remainingTime / (float) duration;
                
                if (progress > 1f) {
                    progress = 1f;
                    ((Timer)e.getSource()).stop();
                }
                
                setOpacity(progress);
            }
        });
        
        timer.setRepeats(true);
        timer.setInitialDelay(delay);
        startTime = System.currentTimeMillis() + delay;
        timer.start();
    }

    private void setOpacity(float progress) {
        opacity = progress;
        repaint();
    }

    private void updateImage() {
        int w = Math.min(1, getWidth());
        int h = Math.min(1, getHeight());
        
        if (image == null || image.getWidth() != w || image.getHeight() != h) {
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }
        
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(0,0,0,0));
        g.setComposite(AlphaComposite.SrcOver);
        g.fillRect(0, 0, w, h);
        g.dispose();
    }

    @Override
    protected void paintComponent(Graphics gr) {
        updateImage();
        
        Graphics2D imageGraphics = image.createGraphics();
        super.paintComponent(imageGraphics);
        imageGraphics.dispose();

        Graphics2D g = (Graphics2D) gr;
        g.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, opacity));
        g.drawImage(image, 0, 0, null);
    }
	
}
