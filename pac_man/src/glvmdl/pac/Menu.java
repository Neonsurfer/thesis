package glvmdl.pac;

import glvmdl.pac.highscore.DisplayHighscore;
import static glvmdl.pac.highscore.Highscore.getCurrentHighscores;
import glvmdl.pac.world.utils.Utils;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

import javax.swing.*;
public class Menu {

    private static int worldId = 1;
    private final static int WORLD_ID_MAX = 5;
    private static int lives;
    private static int score;
    private static boolean savedGame;
    private BufferedImage background;
    
    private final JButton button1; // Új játék
    private final JButton button2; // Top lista
    private final JButton button3; // Játék folytatása
    private final JButton button4; // Kilépés
    
    public Menu() {
        System.out.println(worldId);
        lives = 3;
        score = 0;
        savedGame = true;
        getCurrentHighscores();
        
        JFrame frame = new JFrame("Menu");
        try{
            background = ImageIO.read(Menu.class.getClassLoader().getResource("textures/background.jpg"));
        }catch(IOException backgroundErr){
            System.out.println("background Error");
        }
        
        this.button1 = new JButton("New Game");
        button1.addActionListener((ActionEvent e) -> {
            resetScore();
            resetLives();
            resetWorldId();
            savedGame = false;
            System.out.println(worldId);
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
            if(lives == 0){
                lives = 3;
                worldId = 1;
                score = 0;
            }
            System.out.println(worldId);
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
        frame.setContentPane(new ImagePanel(background));
        frame.setLayout(new FlowLayout());
        frame.add(button1);
        frame.add(button3);
        frame.add(button2);
        frame.add(button4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(400,325);
        frame.setVisible(true);
        
    }
    
    public int getWorldId(){
        return worldId;
    }
    
    public void increaseWorldId(){
        worldId++;
        if( worldId == WORLD_ID_MAX){
            worldId = 1;
        }
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
            Path path = Paths.get(System.getProperty("user.home"));
            if(Files.exists(Paths.get(path + "/.pacmangame/last.txt"))){
                StringBuilder builder = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.home") + "/.pacmangame/last.txt")));
                
                String line;
                while((line = br.readLine()) != null){
                    builder.append(line + "\n");
                }
                br.close();
                line = builder.toString();
                if(line != null && line.length() > 0){
                    String [] pieces = line.split(";");
                    worldId = Utils.parseInt(pieces[0]);
                    lives = Utils.parseInt(pieces[1]);
                    score = Utils.parseInt(pieces[2]);
                }
            }
            else{
                Files.createDirectory(Paths.get(path + "/.pacmangame"));
                Files.createFile(Paths.get(path + "/.pacmangame/last.txt"));
                Files.createFile(Paths.get(path + "/.pacmangame/highscores.txt"));
                RandomAccessFile in = new RandomAccessFile((System.getProperty("user.home") + "/.pacmangame/last.txt"),"rw");
                in.writeBytes("1;3;0;");
            }

        }catch(IOException e){
            System.out.println("Error with last visited world file");
        }
    }
    
    public void saveDetails(){
        try{
            String path = (System.getProperty("user.home") + "/.pacmangame/last.txt");
            RandomAccessFile input = new RandomAccessFile(path,"rw");
            input.seek(0);
            input.writeBytes("" + worldId + ";" + lives + ";"+score+";");
        }catch(IOException e){
            System.out.println("Error with last visited world file");
        }
    }
    
}

class ImagePanel extends JComponent {
    private final Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
