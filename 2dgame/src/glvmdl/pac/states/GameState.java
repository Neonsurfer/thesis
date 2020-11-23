package glvmdl.pac.states;

import glvmdl.pac.Handler;
import glvmdl.pac.world.World;
import java.awt.Graphics;

public class GameState extends State{
    
    private final World world;
    
    public GameState(Handler handler, int worldId){
        super(handler);
        world = new World(handler, "/worlds/world" + worldId + ".txt");
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