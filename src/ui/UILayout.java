package ui;

import javax.swing.*;
 
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import ui.custom.CustomTextField;

public class UILayout {

  public static void main(String[] args) {
    JFrame frame = new JFrame("Basic UI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(true);
    frame.setLayout(new GridLayout(11, 3, 0, 5));
    
    frame.add(new JLabel("Title:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel("First Name:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel("Last Name:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel("Date of Birth:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel("Phone Number:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel("House Number:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel("Street Name:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel("District Name:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel("City:"));
    frame.add(new CustomTextField("test", 15));
    
    frame.add(new JLabel("Post Code:"));
    frame.add(new CustomTextField("", 15));
    
    frame.add(new JLabel());
    frame.add(new JButton("Enter"));

    frame.pack();
    frame.setVisible(true);
  }
}