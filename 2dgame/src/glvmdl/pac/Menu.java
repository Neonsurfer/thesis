package glvmdl.pac;

import glvmdl.pac.highscore.DisplayHighscore;
import glvmdl.pac.world.utils.Utils;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import javax.swing.*;
public class Menu {

    private static int worldId;
    private static int lives;
    private static int score;
    private static boolean savedGame;
    
    private final JButton button1; // Új játék
    private final JButton button2; // Top lista
    private final JButton button3; // Játék folytatása
    private final JButton button4; // Kilépés
    
    public Menu() {
        worldId = 1;
        lives = 3;
        score = 0;
        savedGame = true;
        
        JFrame frame = new JFrame("Menu");
        
        this.button1 = new JButton("New Game");
        button1.addActionListener((ActionEvent e) -> {
            resetScore();
            resetLives();
            resetWorldId();
            savedGame = false;
            Game game = new Game(this,"Yogi-bear Game!",800,800);
            game.start();
        });
        button1.setBounds(100,100,100,30);
        
        this.button2 = new JButton("Highscores");
        button2.addActionListener((ActionEvent e) ->{
            DisplayHighscore disp = new DisplayHighscore();
        });
        button2.setBounds(100,150,100,30);
        
        this.button3 = new JButton("Continue");
        button3.addActionListener((ActionEvent e) ->{
            if(savedGame){
                getSavedDetails();
            }
            Game game = new Game(this,"Yogi-bear Game!",800,800);
            game.start();
        });
        button3.setBounds(100,200,100,30);
        
        
        this.button4 = new JButton("Exit game");
        button4.addActionListener((ActionEvent e) ->{
            saveDetails();
            System.exit(0);
        });
        button4.setBounds(100,250,100,30);
        
        frame.setLayout(new FlowLayout());
        frame.add(button1);
        frame.add(button3);
        frame.add(button2);
        frame.add(button4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(100,160);
        frame.setVisible(true);
        
    }
    
    public int getWorldId(){
        return worldId;
    }
    
    public void increaseWorldId(){
        worldId++;
    }
    
    public void resetWorldId(){
        worldId = 1;
    }
    
    public int getLives(){
        return lives;
    }
    
    public void lowerLives(){
        lives--;
        
    }
    
    public void resetLives(){
        lives = 3;
    }
    
    public int getScore(){
        return score;
    }
    
    public void increaseScore(){
        score++;
    }
    
    public void setScore(int newScore){
        score = newScore;
    }
    
    public void resetScore(){
        score = 0;
    }
    
    public void getSavedDetails(){
        try{
            StringBuilder builder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream("/worlds/last.txt")));
            String line;
            while((line = br.readLine()) != null){
                builder.append(line + "\n");
            }
            br.close();
            line = builder.toString();
            if(line != null){
                String [] pieces = line.split(";");
                worldId = Utils.parseInt(pieces[0]);
                lives = Utils.parseInt(pieces[1]);
                score = Utils.parseInt(pieces[2]);
            }
        }catch(IOException e){
            System.out.println("Error with last visited world file");
        }
    }
    
    public void saveDetails(){
        try{
            RandomAccessFile input = new RandomAccessFile("res/worlds/last.txt","rw");

            input.writeBytes("" + worldId + ";" + lives + ";"+score);
        }catch(IOException e){
            System.out.println("Error with last visited world file");
        }
    }
    
    
  
    
}
