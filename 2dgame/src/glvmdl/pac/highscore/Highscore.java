package glvmdl.pac.highscore;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Highscore {

    private String name;
    private int score;
    private LocalDate date;
    public static List <Highscore> highscores = new ArrayList<>();
    
    public Highscore(String name, int score, LocalDate date){
        this.name = name;
        this.score = score;
        this.date = date;
    }
    
    public static void getCurrentHighscores(){
        if(highscores.isEmpty()){
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
        sortHighScores();
    }
    
    public static void addNewHighScore(String line){
        String [] pieces = line.split(";");
        highscores.add(new Highscore(pieces[0], Integer.parseInt(pieces[1]),  LocalDate.parse(pieces[2])));
    }
    
    public static void checkCurrentScore(int score, String name){
        if(highscores.size() < 11){
            highscores.add(new Highscore(name, score, LocalDate.now()));
        }else{
            while(highscores.size() > 10){
                highscores.remove(highscores.size()-1);
            }
        }
        sortHighScores();
        
        if(score > highscores.get(highscores.size()-1).getScore()){
            highscores.remove(highscores.size()-1);
            highscores.add(new Highscore(name, score, LocalDate.now()));
            sortHighScores();
        }
    }
    
    private static void sortHighScores(){
        for(int i = 0; i < highscores.size() - 1; i++){
            for(int j = 0; j < highscores.size()-i-1; j++){
                if(highscores.get(j).score < highscores.get(j+1).score){
                    Highscore tmp = new Highscore(highscores.get(j).name,highscores.get(j).score,highscores.get(j).date);
                    highscores.remove(j);
                    highscores.add(j+1, tmp);
                }
            }
        }
    }
    
    public static void saveHighScores(){
        try{
            RandomAccessFile input = new RandomAccessFile("highscores.txt", "rw");
            input.seek(0);
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

    @Override
    public String toString() {
        return name + ";" + score + ";" + date;
    }
}