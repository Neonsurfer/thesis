package glvmdl.pac;

import glvmdl.pac.display.Display;
import glvmdl.pac.gfx.Assets;
import glvmdl.pac.gfx.GameCamera;
import glvmdl.pac.input.KeyManager;
import glvmdl.pac.states.GameState;
import glvmdl.pac.states.LeaderboardState;
import glvmdl.pac.states.State;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    /*
    legyen 10 pálya
    gépi játékos velünk/ellenünk -- done
    coop mód -- coop - done
    több játékosban akkor 2 játékos is játszhat akár -- done
    Bubu mint gépi játékos -- done
    követi maci Lacit -- done
    szétrombolhatja a segítő eszközöket vagy ügyes és jobb mint mi
    sövény amit át lehet törni(vagy adott almaszám felett)
    lehet így következő pályára menni
    többpálya esetén mentés, betöltés
    egyéb eszközök esetleg, amik több pontot adnak
    */
    private Display display;
    public String title;
    private int width, height;
    
    private boolean running = false;
    private Thread thread;
    
    private BufferStrategy bs;
    private Graphics g;
    
    
    //States
    
    public State gameState;
    public State menuState;
    public State leaderboardState;
    public Menu menu;
    
    //Input
    private KeyManager keyManager;
    
    //Camera
    private GameCamera gameCamera;
    
    //Handler
    private Handler handler;
    

    

    public Game(Menu menu, String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        this.menu = menu;
    }
    
    private void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();
        
        handler = new Handler(this);
        gameCamera = new GameCamera(handler,0,0);
        
        
        gameState = new GameState(handler, menu.getWorldId());
        leaderboardState = new LeaderboardState(handler); //TODO
        State.setState(gameState);
        
    } 
    
    private void tick(){
        keyManager.tick();
        
        if(State.getState() != null){
            State.getState().tick();
        }
    }
    
    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        // Clear Screen
        g.clearRect(0, 0, width, height);
        //Draw Here!
        
        if(State.getState() != null){
            State.getState().render(g);
        }
        
        //End Drawing!
        bs.show();
        g.dispose();
    }
    
    public void run(){
        init();
        
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        
        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            
            if(delta >=1){
                tick();
                render();
                delta--;
            }
            
            if(timer >=1000000000){
                timer = 0;
            }
        }
        stop();
    }
    
    public KeyManager getKeyManager(){
        return keyManager;
    }
    
    public GameCamera getGameCamera(){
        return gameCamera;
    }
    
    public int getWidth(){
        return width;
    }

    public Graphics getG() {
        return g;
    }
    
    
    public int getHeight(){
        return height;
    }
    
    public void increaseScore(){
        menu.increaseScore();
    }
    
    public int getScore(){
        return menu.getScore();
    }
    
    public void setScore(int score){
        menu.setScore(score);
    }    
    
    public Display getDisplay(){
        return display;
    }
    
    public Menu getMenu(){
        return this.menu;
    }
    
    
    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        System.out.println(menu.getScore());
        System.exit(0);
    }
    
    public void closeGame(){
        handler.getGame().getDisplay().getFrame().setVisible(false);
        handler.getGame().getDisplay().getFrame().dispose();
        handler.getWorld().getEntityManager().killAll();
        handler.setWorld(null);
        handler.setGame(null);
        State.setState(null);
    }
    
    
    
    
}
