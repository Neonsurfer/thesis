package glvmdl.pac.world;

import glvmdl.pac.Handler;
import glvmdl.pac.entity.EntityManager;
import glvmdl.pac.entity.creatures.Player;
import glvmdl.pac.entity.creatures.Angry;
import glvmdl.pac.entity.creatures.Helpful;
import glvmdl.pac.entity.creatures.Problem;
import glvmdl.pac.entity.creatures.Smith;
import glvmdl.pac.entity.creatures.FriendBear;
import glvmdl.pac.tiles.Tile;
import glvmdl.pac.world.utils.Utils;
import java.awt.Graphics;
import glvmdl.pac.entity.creatures.Node;
public class World {

    private final Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int [][] tiles;
    private final EntityManager entityManager;
    public final int [] angrySpawn;
    public final int [] helpfulSpawn;
    public final int [] problemSpawn;
    public final int [] smithSpawn;
    
    public World(Handler handler, String path){
        this.handler = handler;
        loadWorld(path);
        
        angrySpawn = findSpawnPoint();
        helpfulSpawn = findSpawnPoint();
        problemSpawn = findSpawnPoint();
        smithSpawn = findSpawnPoint();
        
        entityManager = new EntityManager(handler, new Player(handler, 45, 45), new Angry(handler, angrySpawn[0]*40, angrySpawn[1]*40 ),
                new Helpful(handler, helpfulSpawn[0]*40, helpfulSpawn[1]*40), new Problem(handler, problemSpawn[0]*40,problemSpawn[1]*40), 
                new Smith(handler, smithSpawn[0]*40, smithSpawn[1]*40), new FriendBear(handler, 45, 45));
        
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
                getTile(x,y).render(g,(int)(x*Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
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
    
    public int[] findSpawnPoint(){
        int [] spawn = new int[2];
        boolean found = false;
        int tries = 0;
        for(int i=0;i<width; i++){
            for(int j=0;j<height; j++){
                if(tiles[i][j] == 0 && !found){
                    if(tries < 4){
                        if((int) (Math.random() * 3 + 1) == 3){
                            spawn[0] = i;
                            spawn[1] = j;
                            found = true;
                            
                        }
                        else{
                            tries++;
                        }
                    }
                    else{
                        spawn[0] = i;
                        spawn[1] = j;
                        found = true;
                    }
                }
            }
        }
        return spawn;
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
    
    public void killEntities(){
        this.entityManager.setEntities(null);
    }
    
    public int getSpawnX(){
        return spawnX;
    }
    
    public int getSpawnY(){
        return spawnY;
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
    
    
}