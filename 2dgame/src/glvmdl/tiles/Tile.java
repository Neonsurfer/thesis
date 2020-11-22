package glvmdl.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
    
    public static Tile[] tiles = new Tile[256];
    public static Tile dirtTile = new DirtTile(0);
    public static Tile treeTile = new TreeTile(1);
    public static Tile rockTile = new RockTile(2);
    public static Tile dirtWithApple = new AppleTile(3);
    public static Tile basket = new BasketTile(4);
    
    public static final int TILEWIDTH = 40, TILEHEIGHT = 40;
    
    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id){
        this.texture = texture;
        this.id = id;
        tiles[id] = this;
    }
        
    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }
    
    public boolean isSolid(){
        return false;
    }
    
    public int getId(){
        return id;
    }
}