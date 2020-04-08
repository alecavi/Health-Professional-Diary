package tester;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import datastore.DataStore;
import datastore.DuplicateNameException;
import datastore.HealthProfessional;

/**
 * 
 * @author Ashik Mahmud
 *
 */
public class TesterGUI implements ActionListener {

	private static DataStore dataStore = new DataStore();
        
        
    private JFrame frame;
    private final JLabel label;
    private final JPanel panel;
    int count = 0;
        
    public TesterGUI() {
        frame = new JFrame();
        panel = new JPanel();
        JButton myButton = new JButton("Appionment Checker");
        myButton.addActionListener(this);
        
        label = new JLabel("Have a Nice Day");
        //
         
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30 ));
        panel.setLayout(new GridLayout(0,1));
        panel.add(myButton);
        panel.add(label);
       
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Welcome To Operation Scheduler");
        //frame.setBackground(Color.GREEN);
        frame.add(label);
        frame.add(myButton);
        frame.pack();
        frame.setVisible(true);
        
    }
      
	public static void main(String[] args)
	{
		HealthProfessional testEntry = new HealthProfessional("John Doe", "Doctor", "Dundee");
		
		//System.out.print("Test adding a name: ");
                 JOptionPane.showMessageDialog(null, "Test adding a name");
                 
        dataStore.addEntry(testEntry);
        if(dataStore.getEntry("DoctorName") != testEntry)  JOptionPane.showMessageDialog(null, "Failure");
        else  JOptionPane.showMessageDialog(null, "Success");
        
        //System.out.print("Test adding the same name again: ");
         JOptionPane.showMessageDialog(null, "Test adding same name again");
        try
        {
        	dataStore.addEntry(testEntry);
        	//System.out.println("Failure!");
                 JOptionPane.showMessageDialog(null, "Failure");
        }
        catch(DuplicateNameException e)
        {
        	//System.out.println("Success!");
                 JOptionPane.showMessageDialog(null, "Success");
        }
        
        System.out.print("Test adding null entry: ");
        try
        {
        	dataStore.addEntry(null);
        	//System.out.println("Failure!");
                 JOptionPane.showMessageDialog(null, "Failure");
        }
        catch(NullPointerException e)
        {
        	//System.out.println("Success!");
                JOptionPane.showMessageDialog(null, "Success");
        }
        
      
    }

        @Override
    public void actionPerformed(ActionEvent ae) {
         count++;
        label.setText("Number of Appoinments : " + count);
        
    }
}

