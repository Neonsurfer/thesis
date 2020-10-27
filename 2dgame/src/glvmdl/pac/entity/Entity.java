package glvmdl.pac.entity;

import glvmdl.pac.Game;
import java.awt.*;
import glvmdl.pac.display.Display;
import glvmdl.pac.Handler;
import glvmdl.pac.gfx.Assets;
import java.awt.Graphics;
import java.awt.Rectangle;
import glvmdl.pac.entity.creatures.Player;
import glvmdl.pac.entity.creatures.FriendBear;

public abstract class Entity {
    
    protected Handler handler;
    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    
    
    public Entity(Handler handler, float x, float y, int width, int height){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        bounds = new Rectangle(0, 0, width, height);
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
    
    public boolean checkEntityCollisions(float xOffset, float yOffset){
       
        for(Entity e : handler.getWorld().getEntityManager().getEntities()){
            //APPLE
            if(e instanceof Player && e.getCollisionBounds( 0f, 0f).intersects(getCollisionBounds(0f, 0f)) && handler.getWorld().getTile(((int)e.x/40), ((int)e.y/40)).getId() == 3){
                this.handler.getWorld().setTile((int)(e.x/40), (int)(e.y/40), 0);
                this.handler.getGame().increaseScore();
                if(this.alreadyWon()){
                    handler.getGame().getMenu().increaseWorldId();
                    handler.getGame().closeGame();
                }
                System.out.println(this.handler.getGame().getScore());
            }
            //BASKET
            if(e instanceof Player && e.getCollisionBounds( 0f, 0f).intersects(getCollisionBounds(0f, 0f)) && handler.getWorld().getTile(((int)e.x/40), ((int)e.y/40)).getId() == 4){
                this.handler.getWorld().setTile((int)(e.x/40), (int)(e.y/40), 0);
                this.handler.getGame().increaseScore();
                if(this.alreadyWon()){
                    handler.getGame().getMenu().increaseWorldId();
                    handler.getGame().closeGame();
                }
                System.out.println(this.handler.getGame().getScore());
                resetBots();
            }
            //FRIENDLYBEAR
            if(e instanceof FriendBear && e.getCollisionBounds( 0f, 0f).intersects(getCollisionBounds(0f, 0f)) && handler.getWorld().getTile(((int)e.x/40), ((int)e.y/40)).getId() == 3){
                this.handler.getWorld().setTile((int)(e.x/40), (int)(e.y/40), 0);
                this.handler.getGame().increaseScore();
                if(this.alreadyWon())handler.getGame().closeGame();
                System.out.println(this.handler.getGame().getScore());
            }
            
            
            //PLAYER VS ENEMIES
            if(e.equals(this.handler.getWorld().getEntityManager().getPlayer()) && this.handler.getWorld().getEntityManager().getPlayer().isCheating == false){
                for(Entity f : handler.getWorld().getEntityManager().getEntities()){
                    if(f.equals(this.handler.getWorld().getEntityManager().getAngry()) && e.getCollisionBounds( xOffset, yOffset).intersects(f.getCollisionBounds(xOffset, yOffset))){
                        this.handler.getWorld().getEntityManager().getPlayer().lowerHP();
                        resetBots();
                        resetPlayer();
                    }
                    if(f.equals(this.handler.getWorld().getEntityManager().getHelpful()) && e.getCollisionBounds(xOffset, yOffset).intersects(f.getCollisionBounds(xOffset, yOffset))){
                        this.handler.getWorld().getEntityManager().getPlayer().lowerHP();
                        resetBots();
                        resetPlayer();
                    }
                    if(f.equals(this.handler.getWorld().getEntityManager().getProblem()) && e.getCollisionBounds(xOffset, yOffset).intersects(f.getCollisionBounds(xOffset, yOffset))){
                        this.handler.getWorld().getEntityManager().getPlayer().lowerHP();
                        resetBots();
                        resetPlayer();
                    }
                    if(f.equals(this.handler.getWorld().getEntityManager().getSmith()) && e.getCollisionBounds(xOffset, yOffset).intersects(f.getCollisionBounds(xOffset, yOffset))){
                        this.handler.getWorld().getEntityManager().getPlayer().lowerHP();
                        resetBots();
                        resetPlayer();
                    }
                }
                
            }
            if(e.equals(this))
                continue;
            
        }
        
        
        return false;
    }
    
    private void resetBots(){
        this.handler.getWorld().getEntityManager().getAngry().x=450;
        this.handler.getWorld().getEntityManager().getAngry().y=600;
        this.handler.getWorld().getEntityManager().getHelpful().x=450;
        this.handler.getWorld().getEntityManager().getHelpful().y=550;
        this.handler.getWorld().getEntityManager().getProblem().x=650;
        this.handler.getWorld().getEntityManager().getProblem().y=550;
        this.handler.getWorld().getEntityManager().getSmith().x=650;
        this.handler.getWorld().getEntityManager().getSmith().y=600;
    }
    
    private void resetPlayer(){
        this.handler.getWorld().getEntityManager().getPlayer().x=51;
        this.handler.getWorld().getEntityManager().getPlayer().y=51;
    }
    
    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        return new Rectangle((int)(x + bounds.x + xOffset), (int)(y + bounds.y + yOffset),  bounds.width, bounds.height);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public boolean alreadyWon(){
        if(handler.getWorld().countApples() == 0){
            handler.getGame().getMenu().increaseWorldId();
            handler.getGame().closeGame();
            return true;
        }
        return false;
    }
    
}
