package EduGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Exception.WarningBox;
import Operation.ReadFile;
import Operation.WriteFile;
public class RosterGUI extends JDialog implements ActionListener {

	
	 JList list;
	
	 
	
	  
	  
	
  public RosterGUI(JFrame parent, String title, String [] message) {
    super(parent, title, true);
    if (parent != null) {
      Dimension parentSize = parent.getSize(); 
      Point p = parent.getLocation(); 
    }
    
    
    list = new JList(message);

    //Add list
    list.addListSelectionListener(new SelectionHandler());
    JScrollPane jsp = new JScrollPane(list);
    jsp.setSize(100, 100);
    
    

   
    getContentPane().add(jsp,BorderLayout.CENTER);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    pack(); 
    setBounds(200, 200, 400, 400);
    setVisible(true);

  }
  public void actionPerformed(ActionEvent e1) {
	  

		 }


  
  
  private class SelectionHandler implements ListSelectionListener {

      @Override
      public void valueChanged(ListSelectionEvent e) {
          if (!e.getValueIsAdjusting()) {
              System.out.println(Arrays.toString(list.getSelectedValues()));
          }
      }
  }
  

  
}