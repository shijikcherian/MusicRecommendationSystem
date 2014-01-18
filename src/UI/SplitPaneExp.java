package UI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class SplitPaneExp extends JFrame {
    
    public SplitPaneExp() {
        
        setTitle("Welcome " + MyListener.uname);
        setSize(600, 600);
        String [] genre = {"electronic", "Hip Hop", "Polski Hip Hop", "funk", "house", "dance"};
        
        //JPanel jsp1 = new JPanel();
        JPanel mainPanel1 = new JPanel();
        JLabel usernamelabel1= new JLabel("Artist Name");
        mainPanel1.setLayout(new GridLayout(0,4));
        JPanel leftTextFieldPanel1 = new JPanel();
	    leftTextFieldPanel1.setLayout(new FlowLayout()); 
	    JTextField leftTextField1 = new JTextField();
	    leftTextField1.setEditable(true);
	    leftTextField1.setPreferredSize(new Dimension(150,20));  
	    leftTextFieldPanel1.add(usernamelabel1);
	    leftTextFieldPanel1.add(leftTextField1);
	    mainPanel1.add(leftTextFieldPanel1);
	    
	    JLabel artistLabel= new JLabel("Select Genre");
	    JPanel combo = new JPanel();
	    final String[] description = { "BollyWood Music","Jazz", "Latin American", "Spiritual" };
	    int count = 0;
	    JComboBox c = new JComboBox();
	    
	    for(int i=0; i< description.length; i++)
	    {
	    	c.addItem(description[count++]);
	    }
	    combo.add(artistLabel);
	    combo.add(c);
	    ActionListener listener = new MyListener();
	    c.addActionListener(listener);
	    mainPanel1.add(combo);
	    Box buttonBar = Box.createHorizontalBox();
	    
	    buttonBar.add(Box.createHorizontalGlue());
	    
	    JLabel butt= new JLabel("");
	    JPanel buttonbox = new JPanel();
	    buttonbox.setLayout(new FlowLayout());
	    
	    JButton reco= new JButton();
	    //JButton cancel = new JButton();
	    buttonbox.add(butt);
	    buttonBar.add(reco);
	    //buttonBar.add(cancel);
	    buttonbox.add(reco);
	    //buttonbox.add(cancel);
	    //login.setBounds(2, 4, 2, 20);
	    reco.setText("Play");
	    
	    reco.addActionListener(listener);
	    //cancel.setText("Cancel");
	    //login.setLocation(2, 5);
	    //login.setSize(new Dimension(40,20));
	    mainPanel1.add(buttonbox);
	    
        JPanel jsp2 = new JPanel();
        JLabel j1 = new JLabel("Area 1");
        JLabel j2 = new JLabel("Recommendations:::: ");
        j2.getFont();
		//Font newLabelFont=new Font(Font.SANS_SERIF,Font.BOLD,j2.getFont().getSize()); 
        j2.setFont(new Font("serif", Font.BOLD, 28));
        jsp2.setLayout(new GridLayout(10,3));
        
        //jsp1.add(j1);
        jsp2.add(j2);
        
        for(int i=0; i < genre.length; i++)
        {
        	
        	jsp2.add(new JLabel( genre[i].toString() ));
        }
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                true, mainPanel1, jsp2);
        
        splitPane.setOneTouchExpandable(true);
        getContentPane().add(splitPane);
        
    }
    //public static void main(String[] args) {
    //    
    //    SplitPaneExp sp = new SplitPaneExp();
    //    sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    sp.setVisible(true);
        
    //}
}