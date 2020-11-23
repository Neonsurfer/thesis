package glvmdl.pac.entity.creatures;

import glvmdl.pac.Handler;
import glvmdl.pac.gfx.Assets;
import java.awt.Graphics;
import glvmdl.pac.tiles.Tile;
import java.util.*;

public class Smith extends Creature{
    static List <Node> path = new ArrayList<>();
    static int destinationTileX;
    static int destinationTileY;
    
    public Smith(Handler handler, float x, float y){
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 25;
        bounds.height = 25;
    }
    
    @Override
    public void tick(){
        path = getInput();
        
        if(path.size() > 0){
            Node next = path.get(0);
            if(next.getRow() < this.y/Tile.TILEHEIGHT){
                yMove = -speed*0.8f;
            } 
            if(next.getRow() > this.y/Tile.TILEHEIGHT){
                yMove = speed*0.8f;
            }
            if(next.getCol() < (int)x/Tile.TILEWIDTH) {
                xMove = -speed*0.8f;
            }  
            if(next.getCol() > (int)x/Tile.TILEWIDTH){
                xMove = speed*0.8f;
            }        
        move();
        }
    }
    
    private List getInput(){
        Node [][] world = handler.getWorld().nodificateMap();
        Astar astar = new Astar(world.length, world.length,  //rows and cols
                world[(int)(y/Tile.TILEWIDTH) ][(int)(x/Tile.TILEHEIGHT) ],//initial
                world[(int)(handler.getWorld().getEntityManager().getPlayer().getY()/Tile.TILEWIDTH)] //final x
                        [(int)(handler.getWorld().getEntityManager().getPlayer().getX()/Tile.TILEHEIGHT)]); //final y
        astar.setNodeValues(handler.getWorld().getWorld());
        
        if(destinationTileX==0|| (destinationTileX == (int)(y/Tile.TILEHEIGHT) && destinationTileY == (int)(x/Tile.TILEHEIGHT))){
            do{
                destinationTileX = (int)((Math.random()*world.length));
                destinationTileY = (int)((Math.random()*world.length));
            }while(astar.getSearchArea()[destinationTileX][destinationTileY].isBlock());
        }
        astar.setFinalNode(new Node(destinationTileX,destinationTileY));
        
        List<Node> nodes = astar.findPath();
        
        if(nodes.size()>1){
            nodes.remove(0);
        }
       
        return nodes;
    }
    
    @Override
    public void render(Graphics g){
        g.drawImage(Assets.smith, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
//        for(int i=0;i<path.size();i++){
//            g.drawRect((int)(path.get(i).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(i).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
//            g.fillRect((int)(path.get(i).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(i).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
//        }
    }
}