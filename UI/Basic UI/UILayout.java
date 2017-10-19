package Assingment1UI;
 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class UILayout {

  public static void main(String[] args) {
    JFrame.setDefaultLookAndFeelDecorated(true);
    JFrame frame = new JFrame("Basic UI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLayout(new GridLayout(11, 3, 0, 5));
    
    
    frame.add(new JLabel("Title:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("First Name:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("Last Name:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("Date of Birth:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("Phone Number:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("House Number:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("Street Name:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("District Name:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("City:"));
    frame.add(new JTextField("", 15));
    
    frame.add(new JLabel("Post Code:"));
    frame.add(new JTextField());
    
    frame.add(new JLabel());
    frame.add(new JButton("Enter"));
   
    

    frame.pack();
    frame.setVisible(true);
  }
}