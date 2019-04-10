package GUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import core.Kernel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GUI {
	static JButton btnNewButton;
	private JFrame frame;
	static boolean x;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public GUI() throws IOException, InterruptedException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private void initialize() throws IOException, InterruptedException {
		
		frame = new JFrame();
		frame.setBounds(250, 100, 800, 600);
		frame.setResizable(false);  //3shan 3ala 7agm el sora
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Kernel kernel =new Kernel();
		
		btnNewButton=new JButton();
		btnNewButton.setIcon(new ImageIcon("siri_final.jpg"));
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				if(x==false) {
					btnNewButton.setIcon(new ImageIcon("1.gif"));
					x=true;
					System.out.println("HERE");
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				try {
					if(!kernel.runShell())
						System.exit(0);
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(x) {
					x=false;
					btnNewButton.setIcon(new ImageIcon("siri_final.jpg"));
					
				}
			}
		});
		
		frame.getContentPane().add(btnNewButton, BorderLayout.CENTER);

	}
	

}
