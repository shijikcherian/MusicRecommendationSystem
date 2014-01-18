package UI;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import UI.MyListener;


public class SwingUI extends JFrame { // if extends Applet then it is an Applet else a Swing UI
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	static JFrame frame;
	static String msg;
	static JButton butt;
	static Graphics g;
	private JTextField statusField = new JTextField(20);
	private static JTextField userNameField = new JTextField(10);
	private static JTextField passwordField = new JPasswordField(10);
	private JButton loginButton = new JButton("Log in");
	private JButton cancelButton = new JButton("Cancel");
	
	public SwingUI()
	{
		super("Login");
	    //statusField.setEditable(false);
	    //add(statusField, BorderLayout.NORTH);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUI u = new SwingUI();
		g=u.getGraphics();
		frame = new JFrame("Song Recommendation System");
	    frame.setSize(500, 500);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //JTextField jtext = new JTextField(15);
	    JPanel p = new JPanel();
	    p.setLayout(new GridLayout(10,10));
	    butt = new JButton("Click me!");
	    frame.add(butt);
	    ActionListener listener = new MyListener();
	    butt.addActionListener(listener); // Register button with a class that deals  event handler (ActionListener)
	    //frame.getContentPane().add(BorderLayout.CENTER, u);
	    //paint1(g);
	    frame.setVisible(true);
	}
	
	
	public static void paint1(Graphics g)
	{
		g.drawString("Hello", 100, 100);
	}
}
