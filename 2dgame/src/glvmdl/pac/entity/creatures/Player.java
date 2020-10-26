package glvmdl.pac.entity.creatures;


import glvmdl.pac.Handler;
import glvmdl.pac.gfx.Assets;
import glvmdl.pac.highscore.EndgamePanel;
import static glvmdl.pac.highscore.Highscore.getCurrentHighscores;
import java.awt.Graphics;
import java.util.*;

public class Player extends Creature{

    public boolean isCheating = false;
    
    
    public Player(Handler handler, float x, float y){
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 30;
        bounds.height = 30;
        this.health = 3;
        //TWEAK THIS
    }
    
    @Override
    public void tick(){
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this); //CAMERA CENTER
        checkHP();
        checkKeyManager(); // for cheat mode activation
    }
    
    private void getInput(){
        xMove = 0;
        yMove = 0;
        
        if(handler.getKeyManager().up){
            yMove = -speed;
            this.handler.getGame().getKeyManager().pressedKeys.add("u");
        }
        
        if(handler.getKeyManager().down){
            yMove = speed;
            this.handler.getGame().getKeyManager().pressedKeys.add("d");
        }
        
        if(handler.getKeyManager().left){
            xMove = -speed;
            this.handler.getGame().getKeyManager().pressedKeys.add("l");
        }
        if(handler.getKeyManager().right){
            xMove = speed;
            this.handler.getGame().getKeyManager().pressedKeys.add("r");
        }
        if(handler.getKeyManager().escape){
            handler.getGame().closeGame();
        }
        if(handler.getKeyManager().ctrl){
            if(this.handler.getGame().getScore()>=200 && this.isCheating){
                this.handler.getGame().setScore(this.handler.getGame().getScore()-100);
                this.handler.getWorld().getEntityManager().spawnFriendlyBear();
            }
        }
        if(handler.getKeyManager().b){
            this.handler.getGame().getKeyManager().pressedKeys.add("b");
        }
        if(handler.getKeyManager().a){
            this.handler.getGame().getKeyManager().pressedKeys.add("a");
        }
        
    }
    
    @Override
    public void render(Graphics g){
        g.drawImage(Assets.player, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
        g.drawRect((int)(x - handler.getGameCamera().getxOffset()),(int)(y - handler.getGameCamera().getyOffset()),bounds.width,bounds.height);
    }
    
    private void checkHP(){
        if(this.health==0){
            System.out.println("GAME OVER");
            getCurrentHighscores();
            EndgamePanel tmp = new EndgamePanel(handler.getGame().getScore());
            System.out.println("asd");
            tmp = null;
            handler.getGame().closeGame();
        }
        
    }
    
    public  void lowerHP(){
        this.health--;
        if(this.health > 0){
            System.out.println(health + " lives remaining");
        }
    }
    
    public void checkKeyManager(){
        ArrayList <String> temp = this.handler.getGame().getKeyManager().pressedKeys;
        String lastPressed = "";
        for(int i=0;i<temp.size();i++){
            lastPressed +=temp.get(i);
        }
        if(lastPressed.contains("uuddlrlrba")){
            this.isCheating = true;
            
        }
        
        if(lastPressed.length() > 20){
            for(int i=0;i<10;i++){
                this.handler.getGame().getKeyManager().pressedKeys.remove(i);
            }
        }
    }
    
    
    
}
