/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glvmdl.pac.entity.creatures;

import glvmdl.pac.Game;
import glvmdl.pac.Handler;
import glvmdl.pac.display.Display;
import glvmdl.pac.gfx.Assets;
import java.awt.Color;
import java.awt.Graphics;
import glvmdl.pac.entity.creatures.Astar;
import glvmdl.pac.entity.creatures.Node;
import glvmdl.tiles.Tile;
import java.util.*;

public class Problem extends Creature{
    
    static List <Node> path = new ArrayList<>();
    
    public Problem(Handler handler, float x, float y){
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        
        bounds.x = 5;
        bounds.y = 5;
        bounds.width = 20;
        bounds.height = 20;
        //TWEAK THIS
    }
    @Override
     public void tick(){
        
//        if(this.waitforit == 60){
        this.path = getInput();
//        }
        
        
        if(this.path.size()>0){
            Node next = this.path.get(0);
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
        move();
        }
    }
    
    private List getInput(){
        Node [][] world = handler.getWorld().nodificateMap();
        int destX = (int)(handler.getWorld().getEntityManager().getPlayer().getX()/Tile.TILEWIDTH)+4;
        Astar astar = new Astar(world.length, world.length, world[(int)(y/Tile.TILEWIDTH) ][(int)(x/Tile.TILEHEIGHT) ]/**/,world[(int)(handler.getWorld().getEntityManager().getPlayer().getY()/Tile.TILEWIDTH) ][(int)(handler.getWorld().getEntityManager().getPlayer().getX()/Tile.TILEHEIGHT) ]/**/);
        astar.setNodeValues(handler.getWorld().getWorld());
        
        
        if(destX<handler.getWorld().getHeight() && !astar.getSearchArea()[(int)(handler.getWorld().getEntityManager().getPlayer().getY()/Tile.TILEHEIGHT)][destX].isBlock()){
            astar.setFinalNode(new Node((int)(handler.getWorld().getEntityManager().getPlayer().getY()/Tile.TILEHEIGHT),destX));
        }
        else{
            astar.setFinalNode(new Node((int)(handler.getWorld().getEntityManager().getPlayer().getY()/Tile.TILEWIDTH),(int)(handler.getWorld().getEntityManager().getPlayer().getX()/Tile.TILEHEIGHT)));
        }
        List<Node> nodes = astar.findPath();
        if(nodes.size()>1){
            nodes.remove(0);
        }
        
       
        return nodes;
    }
    
    @Override
    public void render(Graphics g){
        g.drawImage(Assets.problem, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
       // for(int i=0;i<path.size();i++){
//            g.drawRect((int)(path.get(i).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(i).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
            //g.fillRect((int)(path.get(i).getCol()*40- handler.getGameCamera().getxOffset()),(int)(path.get(i).getRow()*40-handler.getGameCamera().getyOffset()),40,40);
        //}
    }
}
