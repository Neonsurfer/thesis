package glvmdl.pac;


import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

import javax.swing.*;
/**
 *
 * @author mvass
 */
public class Menu {
    private JFrame frame;
    private Canvas canvas;
    
    private JButton button1; // Új játék
    private JButton button2; // Top lista
    private JButton button3; // Készítő
    private JButton button4; // Kilépés
    
    
    public Menu() {
        JFrame frame = new JFrame("Menu");
        
        this.button1 = new JButton("New Game");
        button1.addActionListener((ActionEvent e) -> {
            Game game = new Game("Tile Game!",800,800);
            game.start(); 
        });
        button1.setBounds(100,100,100,30);
        button1.setVisible(true);
        this.button2 = new JButton("Highscores");
        button2.addActionListener((ActionEvent e) ->{
            
        });
        this.button3 = new JButton("About");
        this.button4 = new JButton("Exit game");
        frame.setLayout(new FlowLayout());
        frame.add(button1);    
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(100,200);
        frame.setVisible(true);
        
    }
  
    
}
