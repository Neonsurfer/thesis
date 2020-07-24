package glvmdl.pac.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class KeyManager implements KeyListener{
    
    private boolean[] keys;
    public boolean up, down, left, right, escape, ctrl, b, a;
    public ArrayList <String> pressedKeys = new ArrayList<>();
    
    
    
    public KeyManager(){
        keys = new boolean[256];
    }
    
    public void tick(){
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        escape = keys[KeyEvent.VK_ESCAPE];
        ctrl = keys[KeyEvent.VK_CONTROL];
        b = keys[KeyEvent.VK_B];
        a = keys[KeyEvent.VK_A];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    
}
