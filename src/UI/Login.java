package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import UI.MyListener;

public class Login extends JPanel{ 
	static JButton login,cancel;
	static JPanel mainPanel;
	static JFrame failFrame;
	static JTextField leftTextField = new JTextField();
	static JTextField rightTextField = new JPasswordField();
	static String str = null;
/**
 * @param args
 */
	//private static JButton loginButton = new JButton("Log in");
	//private static JButton cancelButton = new JButton("Cancel");
	
public static void main(String[] args) {
    failFrame = new JFrame("Login to your favorite Song Database");
    mainPanel = new JPanel();
    JLabel usernamelabel= new JLabel("Username");
    JLabel passwordlabel= new JLabel("Password");
    mainPanel.setLayout(new GridLayout(4, 0));

   // mainPanel.add(usernamelabel);
    JPanel leftTextFieldPanel = new JPanel();
    leftTextFieldPanel.setLayout(new FlowLayout());        
    JPanel rightTextFieldPanel = new JPanel();
    rightTextFieldPanel.setLayout(new FlowLayout());
    //JTextField leftTextField = new JTextField();
    leftTextField.setPreferredSize(new Dimension(150,20));              
    //JTextField rightTextField = new JTextField();
    rightTextField.setPreferredSize(new Dimension(160,20));

    leftTextFieldPanel.add(usernamelabel);
    leftTextFieldPanel.add(leftTextField);
    mainPanel.add(leftTextFieldPanel);

    rightTextFieldPanel.add(passwordlabel);
    rightTextFieldPanel.add(rightTextField);
    mainPanel.add(rightTextFieldPanel);
    
    
Box buttonBar = Box.createHorizontalBox();
    
    buttonBar.add(Box.createHorizontalGlue());
    //buttonBar.add(cancelButton);
    //buttonBar.add(loginButton);
    //mainPanel.add(buttonBar, BorderLayout.WEST);
    mainPanel.add(rightTextFieldPanel);
    mainPanel.add(new JPanel());
    JPanel buttonbox = new JPanel();
    buttonbox.setLayout(new FlowLayout());
    
    login = new JButton();
    cancel = new JButton();
    buttonBar.add(login);
    buttonBar.add(cancel);
    buttonbox.add(login);
    buttonbox.add(cancel);
    //login.setBounds(2, 4, 2, 20);
    login.setText("Login");
    cancel.setText("Cancel");
    //login.setLocation(2, 5);
    //login.setSize(new Dimension(40,20));
    mainPanel.add(buttonbox);
    
    ActionListener listener = new MyListener();
    login.addActionListener(listener);
    cancel.addActionListener(listener);
        //frame.add(mainPanel);
    failFrame.add(mainPanel);
    //failFrame1.add(mainPanel1);
    failFrame.setSize(600, 400);
    failFrame.setLocationRelativeTo(null);
    failFrame.setVisible(true);
}
}