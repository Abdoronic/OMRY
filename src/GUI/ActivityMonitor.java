package GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ActivityMonitor extends JFrame{
	private static final long serialVersionUID = 1L;
	
	int cpuUsage, memoryUsage, diskUsage, ioUsage;
	
	public ActivityMonitor() {
		setTitle("Activity Monitor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawBars(0, 0, 0, 0);
		setSize(800, 600);
		setVisible(true);
	}
	
	public void drawBars(int h1, int h2, int h3, int h4) {
		JPanel panel = new JPanel();
		panel.setSize(740, 600);
		panel.setLayout(new GridLayout(2, 4));
		
		panel.add(new Bar(0, 0, 150, h1 * 3, Color.RED.darker()));
		panel.add(new Bar(0, 0, 150, h2 * 3, Color.GREEN.darker()));
		panel.add(new Bar(0, 0, 150, h3 * 3, Color.BLUE.darker()));
		panel.add(new Bar(0, 0, 150, h4 * 3, Color.YELLOW.darker()));
		
		JLabel cpuLabel = new JLabel("CPU Usage" + "(" + h1 + "%)");
		cpuLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		panel.add(cpuLabel);
		
		JLabel memoryLabel = new JLabel("Memory Usage" + "(" + h2 + "%)");
		memoryLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		panel.add(memoryLabel);
		
		JLabel diskLabel = new JLabel("Disk Usage" + "(" + h3 + "%)" );
		diskLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		panel.add(diskLabel);
		
		JLabel ioLabel = new JLabel("IO Usage" + "(" + h4 + "%)");
		ioLabel.setFont(new Font("Helvetica Neue " , Font.BOLD, 14));
		panel.add(ioLabel);
		
		Border padding = BorderFactory.createEmptyBorder(50, 50, 50, 50);
		panel.setBorder(padding);
		setContentPane(panel);
	}
	
	public void taskSwappedIn() {
		cpuUsage = (int)(Math.random() * 101);
		memoryUsage = (int)(Math.random() * 101);
		diskUsage = (int)(Math.random() * 101);
		ioUsage = (int)(Math.random() * 101);
		drawBars(cpuUsage, memoryUsage, diskUsage, ioUsage);
	}
	
	public void taskSwappedOut() {
		cpuUsage = 0;
		memoryUsage = 0;
		diskUsage = 0;
		ioUsage = 0;
		drawBars(cpuUsage, memoryUsage, diskUsage, ioUsage);
	}
	
	public static void main(String[] args) throws InterruptedException {
		ActivityMonitor am = new ActivityMonitor();
		while(true) {
			am.taskSwappedIn();
			
			//Task is running
			Thread.sleep(500);
			//Task finished
			
			am.taskSwappedOut();
		}
	}
}
