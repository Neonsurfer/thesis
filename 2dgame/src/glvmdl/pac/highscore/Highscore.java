
package glvmdl.pac.highscore;

import glvmdl.pac.input.KeyManager;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Highscore {

    private String name;
    private int score;
    private LocalDate date;
    private KeyManager keyManager;
    public static List <Highscore> highscores = new ArrayList<>();
    
    public Highscore(String name, int score, LocalDate date){
        this.name = name;
        this.score = score;
        this.date = date;
        keyManager = new KeyManager();
        
    }
    
    public static void getCurrentHighscores(){
        try{
            RandomAccessFile input = new RandomAccessFile("highscores.txt", "rw");
            for(String line = input.readLine(); line != null; line = input.readLine()){
                addNewHighScore(line);
            }
            input.close();
        }catch(IOException e){
            System.out.println("Highscore file not found!");
        }
    }
    
    private static void addNewHighScore(String line){
        String [] pieces = line.split(";");
        Highscore current = new Highscore(pieces[0], Integer.parseInt(pieces[1]),  LocalDate.parse(pieces[2]));
        highscores.add(current);
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
}
