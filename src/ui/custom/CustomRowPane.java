package ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CustomRowPane extends JPanel {

	private ArrayList<Cell> cellList;
	private CustomRowPane container;
	private ComponentAdapter adjustListener;
	
	public CustomRowPane() {
		super(null);
		setOpaque(false);
		
		this.cellList = new ArrayList<Cell>();
		this.container = this;
		this.adjustListener = new ComponentAdapter() {			
			@Override
			public void componentResized(ComponentEvent e) {
				int totalWidth = 0;
				
				for (Cell cell: container.cellList) {
					totalWidth += cell.getBounds().width;
				}
				
				int dif = Math.abs(container.getBounds().width - totalWidth);
				
				for (int i = 1; i < dif; i++) {
					Cell prev = container.cellList.get(i - 1);
					Cell next = container.cellList.get(i);
					Rectangle pb = prev.getBounds();
					Rectangle nb = next.getBounds();
					
					prev.setBounds(pb.x, pb.y, pb.width + 1, pb.height);
					next.setBounds(nb.x + i, nb.y, nb.width, nb.height);
					prev.revalidate();
					next.revalidate();
					prev.repaint();
					next.repaint();
				}
				
				for (int i = dif; i < container.cellList.size(); i++) {
					Cell cell = container.cellList.get(i);
					Rectangle b = cell.getBounds();
					
					cell.setBounds(b.x + dif - 1, b.y, b.width, b.height);
					cell.revalidate();
					cell.repaint();
				}
			}
		};
		
		addComponentListener(adjustListener);
	}
	
	public void addCell(JComponent component, double percentage) {
		Cell cell = new Cell(component, percentage);
		this.cellList.add(cell);

		removeComponentListener(adjustListener);
		addComponentListener(setResizeListener(cell));
		addComponentListener(adjustListener);
		add(cell);
	}
	
	private ComponentAdapter setResizeListener(Cell cell) {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				cell.setBounds(cell.getCellBounds());
								
				cell.revalidate();
				cell.repaint();
			}
		};
	}
	
	private class Cell extends JPanel {
		
		public JComponent component;
		public double percentage;
		
		public Cell(JComponent component,  double percentage) {
			super(new BorderLayout());
			setOpaque(false);
			
			this.component = component;
			this.percentage = percentage;

			add(component);
		}
		
		public Rectangle getCellBounds() {
			Rectangle bounds = container.getBounds();
			int x = 0, y = 0;
			int width = (int) (bounds.width * percentage / 100.0);
			int height = bounds.height;
			
			for (Cell cell: cellList) {
				if (cell == this) {
					break;
				} else {
					x = (int) (x + bounds.width * cell.percentage / 100.0);
				}
			}
			
			return new Rectangle(x, y, width, height);
		}
		
	}
	
}
