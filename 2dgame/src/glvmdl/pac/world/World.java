package glvmdl.pac.world;

import glvmdl.pac.Game;
import glvmdl.pac.Handler;
import glvmdl.pac.entity.EntityManager;
import glvmdl.pac.entity.creatures.Player;
import glvmdl.pac.entity.creatures.Angry;
import glvmdl.pac.entity.creatures.Helpful;
import glvmdl.pac.entity.creatures.Problem;
import glvmdl.pac.entity.creatures.Smith;
import glvmdl.pac.entity.creatures.FriendBear;
import glvmdl.tiles.Tile;
import glvmdl.pac.world.utils.Utils;
import java.awt.Graphics;
import glvmdl.pac.entity.creatures.Node;
public class World {

    private Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int [][] tiles;
    private EntityManager entityManager;
    private static int appleCounter = 100;
    
    
    public World(Handler handler, String path){
        this.handler = handler; // tweak spawn finder for everyone
        entityManager = new EntityManager(handler, new Player(handler, 50, 50), new Angry(handler, 450, 600 ),
                new Helpful(handler, 450, 550), new Problem(handler, 650,550), new Smith(handler, 650, 600),
                new FriendBear(handler, 50, 50));
        loadWorld(path);
        
        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
        entityManager.getPlayer().setHealth(handler.getGame().getMenu().getLives());
    }
    
    public void tick(){
        entityManager.tick();
    }
    
    public void render(Graphics g){
        int xStart = (int)Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int)Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(width, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
        
        
        for(int y=yStart;y< yEnd;y++){
            for(int x=xStart;x<xEnd;x++){
                getTile(x,y).render(g,(int)(x*Tile.TILEWIDTH - handler.getGameCamera().getxOffset()) ,
                        (int)(y*Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
            }
        }
        
        entityManager.render(g);
    }
    
    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x>=width || y>= height) return Tile.dirtTile;
        Tile t = Tile.tiles[tiles[x][y]];
        if(t == null){
            return Tile.dirtTile;
        }
        return t;
    }
    
    public void setTile(int x, int y, int id){
        tiles[x][y] = id;
    }
    
    private void loadWorld(String path){
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX =  Utils.parseInt(tokens[2]);
        spawnY =  Utils.parseInt(tokens[3]);
        
        tiles = new int[width][height];
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                tiles[x][y] = Utils.parseInt(tokens[(x+y * width) + 4]);
            }
        }
        
    }
    
    public Node[][] nodificateMap(){
        Node[][] returnable = new Node[tiles.length][tiles.length];
        for(int i=0;i<tiles.length;i++){
            for(int j=0;j<tiles.length;j++){
                returnable[i][j] = new Node(i,j);
            }
        }
        return returnable;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    public int[][] getWorld(){
        return tiles;
    }
    
    public int countApples(){
        int appleCount = 0;
        for(int i=0;i<tiles.length;i++){
            for(int j=0;j<tiles.length;j++){
                if(tiles[i][j] == 3 || tiles[i][j] == 4){
                    appleCount++;
                }
            }
        }
        return appleCount;
    }
    
    public int getAppleCount(){
        return this.appleCounter;
    }
    
    public void killEntities(){
        this.entityManager.setEntities(null);
    }
    
    
}
