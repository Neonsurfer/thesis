package glvmdl.pac.entity.creatures;

import glvmdl.pac.Handler;
import glvmdl.pac.gfx.Assets;
import java.awt.Graphics;
import glvmdl.pac.tiles.Tile;

import java.util.*;

public class FriendBear extends Creature{
    static List <Node> path = new ArrayList<>();
    int destinationTileX;
    int destinationTileY;

    private static boolean isControlled = true;
    
    public FriendBear(Handler handler, float x, float y){
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 25;
        bounds.height = 25;
    }

    @Override
    public void tick(){
        //if player is in control
        getPlayerInput(); 
        
        //if ai is in control
        if(isControlled == false){
            path = getInput();
            if(path.size()>0){
                Node next = path.get(0);
                if(next.getRow()*Tile.TILEHEIGHT+5 < y){
                    yMove = -speed*0.8f;
                } 
                if(next.getRow()*Tile.TILEHEIGHT+5 > y){
                    yMove = speed*0.8f;
                }
                if(next.getCol()*Tile.TILEWIDTH+5 < x) {
                    xMove = -speed*0.8f;
                }  
                if(next.getCol()*Tile.TILEWIDTH+5 > x){
                    xMove = speed*0.8f;
                }
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
    private List getInput(){
        Node [][] world = handler.getWorld().nodificateMap();
        Astar astar = new Astar(world.length, world.length,  //rows and cols
            world[(int)(y/Tile.TILEWIDTH) ][(int)(x/Tile.TILEHEIGHT) ],//initial
            world[(int)(handler.getWorld().getEntityManager().getPlayer().getY()/Tile.TILEWIDTH)] //final x
                    [(int)(handler.getWorld().getEntityManager().getPlayer().getX()/Tile.TILEHEIGHT)]); //final y
        astar.setNodeValues(handler.getWorld().getWorld());
        
        if(this.destinationTileX==0 || (this.destinationTileX == (int)(y/Tile.TILEHEIGHT) && this.destinationTileY == (int)(x/Tile.TILEHEIGHT))){
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
//        g.drawRect((int)(path.get(0).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(0).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
//        for(int i=0;i<path.size();i++){
//            g.drawRect((int)(path.get(i).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(i).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
//            g.fillRect((int)(path.get(i).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(i).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
//        }
    }
}