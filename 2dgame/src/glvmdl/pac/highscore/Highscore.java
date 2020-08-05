
package glvmdl.pac.highscore;

import glvmdl.pac.input.KeyManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Highscore {

    private String name;
    private int score;
    private Date date;
    private KeyManager keyManager;
    public ArrayList <Highscore> highscores;
    
    public Highscore(String name, int score, Date date){
        this.name = name;
        this.score = score;
        this.date = date;
        keyManager = new KeyManager();
    }
    
    private void getCurrentHighscores(){
        try{
            
        }catch(IOException e){
            System.out.println("Highscore file not found!");
        }
    }
}
