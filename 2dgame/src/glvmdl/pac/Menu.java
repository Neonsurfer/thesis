package glvmdl.pac;


import glvmdl.pac.highscore.DisplayHighscore;
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
    
    static int worldId;
    
    private JButton button1; // Új játék
    private JButton button2; // Top lista
    private JButton button3; // Készítő
    private JButton button4; // Kilépés
    
    
    public Menu() {
        worldId = 1;
        
        JFrame frame = new JFrame("Menu");
        
        this.button1 = new JButton("New Game");
        button1.addActionListener((ActionEvent e) -> {
            Game game = new Game(this,"Yogi-bear Game!",800,800);
            game.start();
        });
        button1.setBounds(100,100,100,30);
        
        this.button2 = new JButton("Highscores");
        button2.addActionListener((ActionEvent e) ->{
            DisplayHighscore disp = new DisplayHighscore();
            
        });
        
        this.button3 = new JButton("About");
        
        this.button4 = new JButton("Exit game");
        button4.addActionListener((ActionEvent e) ->{
            
            System.exit(0);
        });
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
    public void increaseWorldId(){
        worldId++;
    }
    
    public void resetWorldId(){
        worldId = 1;
    }
  
    
}
