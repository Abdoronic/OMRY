package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Bar extends JPanel {

	private static final long serialVersionUID = 1L;
	private int X;
	private int Y;
	private int WIDTH;
	private int HEIGHT;
	private Color color;
	
	public Bar(int X, int Y, int WIDTH, int HIGHT, Color color) {
		super();
		this.X = X;
		this.Y = Y;
		this.WIDTH = WIDTH;
		this.HEIGHT = HIGHT;
		this.color = color;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(X, Y, WIDTH, HEIGHT);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH + 2 * X, HEIGHT + 2 * Y);
	}
}
