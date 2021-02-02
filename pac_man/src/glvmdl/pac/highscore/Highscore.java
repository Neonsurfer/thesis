package glvmdl.pac.highscore;

import glvmdl.pac.world.utils.Utils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Highscore {

    private String name;
    private int score;
    private LocalDate date;
    private int worldNum;
    public static List <Highscore> highscores = new ArrayList<>();
    
    public Highscore(String name, int score, int worldId, LocalDate date){
        this.name = name;
        this.score = score;
        this.date = date;
        this.worldNum = worldId;
    }
    
    public static void getCurrentHighscores(){
        if(highscores.isEmpty()){
            try{
                Path path = Paths.get(System.getProperty("user.home"));
                if(Files.exists(Paths.get(path + "/.pacmangame/highscores.txt"))){
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.home") + "/.pacmangame/highscores.txt")));

                    String line = br.readLine();
                    while(line != null){
                        addNewHighScore(line);
                        line = br.readLine();
                    }
                    br.close();
                    
                }
                else{
                    Files.createDirectory(Paths.get(path + "/.pacmangame"));
                    Files.createFile(Paths.get(path + "/.pacmangame/last.txt"));
                    Files.createFile(Paths.get(path + "/.pacmangame/highscores.txt"));
                }
                
            }catch(IOException e){
                System.out.println("Highscore file not found!");
            }
        }
        sortHighScores();
    }
    
    public static void addNewHighScore(String line){
        String [] pieces = line.split(";");
        highscores.add(new Highscore(pieces[0], Utils.parseInt(pieces[1]), Utils.parseInt(pieces[2]), LocalDate.parse(pieces[3])));
    }
    
    public static void checkCurrentScore(int score, String name, int worldNum){
        boolean added = false;
        if(highscores.size() < 11){
            highscores.add(new Highscore(name, score, worldNum, LocalDate.now()));
            added = true;
        }else{
            while(highscores.size() > 10){
                highscores.remove(highscores.size()-1);
            }
        }
        sortHighScores();
        
        if(score > highscores.get(highscores.size()-1).getScore() && !added){
            highscores.remove(highscores.size()-1);
            highscores.add(new Highscore(name, score, worldNum, LocalDate.now()));
            sortHighScores();
        }
    }
    
    private static void sortHighScores(){
        for(int i = 0; i < highscores.size() - 1; i++){
            for(int j = 0; j < highscores.size()-i-1; j++){
                if(highscores.get(j).score < highscores.get(j+1).score){
                    Highscore tmp = new Highscore(highscores.get(j).name,highscores.get(j).score, highscores.get(j).worldNum, highscores.get(j).date);
                    highscores.remove(j);
                    highscores.add(j+1, tmp);
                }
            }
        }
    }
    
    public static void saveHighScores(){
        try{
            String path = (System.getProperty("user.home") + "/.pacmangame/highscores.txt");
            RandomAccessFile input = new RandomAccessFile(path,"rw");
            input.seek(0);
            input.setLength(0);
            for(Highscore h : highscores){
                input.writeBytes(h.toString() + "\r\n");
            }
        }catch(IOException e){
            System.out.println("Highscore file not found!");
        }
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public int getWorldNum(){
        return worldNum;
    }

    @Override
    public String toString() {
        return name + ";" + score + ";" + worldNum + ";" +date + ";";
    }
}