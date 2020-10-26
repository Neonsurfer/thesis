package glvmdl.pac.states;

import glvmdl.pac.Game;
import glvmdl.pac.Handler;
import glvmdl.pac.entity.creatures.Player;
import glvmdl.pac.world.World;
import java.awt.Graphics;

public class GameState extends State{
    
    private World world;
    
    
    public GameState(Handler handler){
        super(handler);
        world = new World(handler, "res/worlds/world1.txt");
        handler.setWorld(world);
  
    }

    @Override
    public void tick(){
        world.tick();
        
    }
    
    @Override
    public void render(Graphics g){
        world.render(g);
    }
    
}
