package UI;

import java.io.*;
import java.net.*;
import Recommendation.MusicRecomm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.lang.model.element.Element;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import UI.Login;
import UI.SwingUI;

public class MyListener extends JFrame implements ActionListener {
	static String uname = null;
	String pass = null;
	protected JSplitPane split;
	URL url;
	String a = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=33b252badb933011d2a3544ea92d91fc&artist=4hero&track=look%20inside";
	FileWriter fout = null;
	final String[] un = { "user_000001","user_000002", "user_000003", "user_000009", "user_000005","user_000014","user_000006" };
	final String pwd = "sjsu";
	
	
	//HttpWebRequest request = (HttpWebRequest)WebRequest.Create(uri);
	public void actionPerformed(ActionEvent e) {
		Login.failFrame.setDefaultLookAndFeelDecorated(true);
		Login.failFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login.failFrame.setVisible(false);
		if(e.getSource() == Login.login)
		{
			uname = Login.leftTextField.getText();
			pass  = Login.rightTextField.getText();

			
			//Connection con = null;
		  
			
			try
	 	  {
			System.out.println("Uname is : " + uname);
			
			
			/**Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost/login","root","root");
			if(!con.isClosed())
			{
				System.out.println("Successfully connected to mysql database using TCP/IP protocol");
			}
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("Select * from login.user");
			System.out.println("Uname ::::" + uname.toString());
			
			while(r.next())
			{
				String ss = r.getString("uname");
				System.out.println("SS is : " + (ss.compareTo(uname)));
				if(r.getString("uname").equals(uname) && r.getString("pass").equals(pass))**/
			
			
			for(int ii=0; ii<un.length; ii++)
			{
				if(un[ii].toString().equalsIgnoreCase(uname) && pass.equals(pwd))
				{
					
					//System.out.println("User name is : " + un[ii]);
					
					setUname(uname);
					
					System.out.println("Login successful");
					
					Recommendation.MusicRecomm.main(null); //calling the main method
					
					
					fout = new FileWriter("E:/track.xml",true);
					BufferedWriter out = new BufferedWriter(fout);
					url = new URL(a);
					URLConnection conn = url.openConnection();
					
					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					
					String inputLine;
		            while ((inputLine = br.readLine()) != null) {
		                    out.write(inputLine);
		                    out.newLine();
		            }
		            out.close();
		            
		            //File stocks = new File("E:/track.xml");
		            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		            Document doc =  dBuilder.parse(new File("E:/track1.xml"));
		            doc.getDocumentElement().normalize();

		            System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
		            NodeList nodes = doc.getElementsByTagName("person");
		            System.out.println("==========================");

		            for (int i = 0; i < nodes.getLength(); i++) {
		            Node node = nodes.item(i);

		            if (node.getNodeType() == Node.ELEMENT_NODE) {
		            	System.out.println("Hello");;
		            //Element element = (Element) node;
		            System.out.println("Helloooooo");
		            //.out.println("Stock Symbol: " + getValue("url", element));
		            //System.out.println("Stock Price: " + getValue("artist", element));
		            //System.out.println("Stock Quantity: " + getValue("image", element));

		            	}
		            }
		           // Read more: http://javarevisited.blogspot.com/2011/12/parse-xml-file-in-java-example-tutorial.html#ixzz2RkSWGF5X
		            br.close();

		            System.out.println("Done");

					
					SplitPaneExp sp = new SplitPaneExp();
				    sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				    sp.setVisible(true);
					
				    
				    /*JFrame failFrame1 = new JFrame("Welcome " + uname);
					JPanel jsp1 = new JPanel();
			        JPanel jsp2 = new JPanel();
			        failFrame1.setSize(600, 400);
				    jsp1.setLayout(new GridLayout(2,2));
			        failFrame1.setVisible(true);**/
			      
				    break;
			        
			        
			        
					/***JFrame failFrame2 = new JFrame("");
					failFrame1.setSize(600, 400);
				    JPanel mainPanel1 = new JPanel();
				    JPanel mainPanel2 = new JPanel();
				    JSplitPane split = new JSplitPane();
				    JLabel usernamelabel1= new JLabel("Artist Name");
				   // JLabel passwordlabel1= new JLabel("Genre");
				    mainPanel1.setLayout(new GridLayout(2,4));
				    split.setDividerLocation(150);
				    
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
				    c.addActionListener(this);
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
				    reco.addActionListener(this);
				    //cancel.setText("Cancel");
				    //login.setLocation(2, 5);
				    //login.setSize(new Dimension(40,20));
				    mainPanel1.add(buttonbox);
				    
				    mainPanel2.add(buttonbox);
				    failFrame1.add(mainPanel1);
				    //failFrame2.add(mainPanel2);
				   // split.setTopComponent(failFrame1);
				    //split.setTopComponent(failFrame2);
				    
				    failFrame1.setVisible(true);
				    //failFrame2.setVisible(true);
					break;
						//JOptionPane.showMessageDialog(null, "You clicked me!"); **/
			   }
				else
				{
					if((un.length-1) == ii) {
					JOptionPane.showMessageDialog(null, "Invalid Uname/password!");
					//break;
					}
				}
			  }
			
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
			//System.out.println("Error connecting to the database");
		}
		 /** finally
		  {
			  try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  }***/
	  }	
		else
		{
			System.exit(0);
		}
	}
	private String getValue(String tag, Element element) {
		// TODO Auto-generated method stub
		NodeList nodes = ((Document) element).getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();

	}
	
	private void setUname(String uname)
	{
		MyListener.uname = uname;
	}
	public static String getUname()
	{
		return MyListener.uname;
	}
}

	