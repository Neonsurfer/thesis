package glvmdl.pac.entity.creatures;

/**
 *
 * @author mvass
 */

import glvmdl.pac.Handler;
import glvmdl.pac.gfx.Assets;
import java.awt.Graphics;
import glvmdl.tiles.Tile;

import java.util.*;

public class FriendBear extends Creature{
    static List <Node> path = new ArrayList<>();
    int destinationTileX;
    int destinationTileY;

    private static boolean isControlled = true;
    
    public FriendBear(Handler handler, float x, float y){
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        
        bounds.x = 5;
        bounds.y = 5;
        bounds.width = 30;
        bounds.height = 30;
    }

    @Override
    public void tick(){
        //if player is in control
        getPlayerInput(); 
        
        //if ai is in control
        if(isControlled == false){
            path = getInput();
            Node next = path.get(0);
            if(next.getRow() < this.y/Tile.TILEHEIGHT){
                yMove = -speed*0.8f;
                    //System.out.println("FEL");
            } 
            if(next.getRow() > this.y/Tile.TILEHEIGHT){
                yMove = speed*0.8f;
                //System.out.println("LE");
            }
            if(next.getCol() < (int)x/Tile.TILEWIDTH) {
                xMove = -speed*0.8f;
                //System.out.println("BAL");
            }  
            if(next.getCol() > (int)x/Tile.TILEWIDTH){
                xMove = speed*0.8f;
                //System.out.println("JOBB");
            }        
            
        }
        move();
    }
    private void getPlayerInput(){
        xMove = 0;
        yMove = 0;
        
        if(handler.getKeyManager().w){
            yMove = -speed;
            this.handler.getGame().getKeyManager().pressedKeys.add("u");
            isControlled = true;
        }
        
        if(handler.getKeyManager().s){
            yMove = speed;
            this.handler.getGame().getKeyManager().pressedKeys.add("d");
            isControlled = true;
        }
        
        if(handler.getKeyManager().a){
            xMove = -speed;
            this.handler.getGame().getKeyManager().pressedKeys.add("l");
            isControlled = true;
        }
        if(handler.getKeyManager().d){
            xMove = speed;
            this.handler.getGame().getKeyManager().pressedKeys.add("r");
            isControlled = true;
        }

        if(xMove == 0 && yMove == 0){
            isControlled = false;
        }
        
    }
    private List getInput(){ //CHANGE WHERE TO GO
        Node [][] world = handler.getWorld().nodificateMap();
        Astar astar = new Astar(world.length, world.length, world[(int)(y/Tile.TILEWIDTH) ][(int)(x/Tile.TILEHEIGHT) ]/**/,world[(int)(handler.getWorld().getEntityManager().getPlayer().getY()/Tile.TILEWIDTH) ][(int)(handler.getWorld().getEntityManager().getPlayer().getX()/Tile.TILEHEIGHT) ]/**/);
        astar.setNodeValues(handler.getWorld().getWorld());
        
        
        if(this.destinationTileX==0|| (this.destinationTileX == (int)(y/Tile.TILEHEIGHT) && this.destinationTileY == (int)(x/Tile.TILEHEIGHT))){
            do{
                this.destinationTileX = (int)((Math.random()*world.length));
                this.destinationTileY = (int)((Math.random()*world.length));
            }while(astar.getSearchArea()[this.destinationTileX][this.destinationTileY].isBlock() || handler.getWorld().getTile(this.destinationTileX, this.destinationTileY).getId() != 3);
        }
        astar.setFinalNode(new Node(this.destinationTileX, this.destinationTileY));
        
        
        List<Node> nodes = astar.findPath();
        
        if(nodes.size()>1){
            nodes.remove(0);
        }
        
       
        return nodes;
    }
    
    @Override
    public void render(Graphics g){
        g.drawImage(Assets.friend, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
        g.drawRect((int)(path.get(0).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(0).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
        for(int i=0;i<path.size();i++){
            g.drawRect((int)(path.get(i).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(i).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
            g.fillRect((int)(path.get(i).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(i).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
        }
    }
    
    
}
