package glvmdl.pac.entity.creatures;

import glvmdl.pac.Handler;
import glvmdl.pac.gfx.Assets;
import glvmdl.pac.highscore.EndgamePanel;
import java.awt.Graphics;

public class Player extends Creature{

    public Player(Handler handler, float x, float y){
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 25;
        bounds.height = 25;
        this.health = handler.getGame().getMenu().getLives();
    }
    
    @Override
    public void tick(){
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);

        checkHP();
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
    }
    
    @Override
    public void render(Graphics g){
        g.drawImage(Assets.player, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
        //g.drawRect((int)(x - handler.getGameCamera().getxOffset()),(int)(y - handler.getGameCamera().getyOffset()),bounds.width,bounds.height);
    }
    
    private void checkHP(){
        if(this.health == 0){
            handler.getGame().getMenu().resetWorldId();
            EndgamePanel tmp = new EndgamePanel(handler.getGame().getScore(), handler.getGame().getMenu().getWorldId());
            tmp = null;
            handler.getGame().closeGame();
        }
    }
    
    public  void lowerHP(){
        this.health--;
        handler.getGame().getMenu().lowerLives();
        String [] textPieces = handler.getGame().getDisplay().getLabelText().split(" ");
        textPieces[1] = " Lives:" + this.health + " ";
        handler.getGame().getDisplay().setLabelText(textPieces[0] + textPieces[1] + textPieces[2]);
    }
}