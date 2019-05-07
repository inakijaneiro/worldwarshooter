/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author inakijaneiro
 */
public class KeyManager implements KeyListener {

    public boolean left;        // flag to move left the player
    public boolean down;
    public boolean up;
    public boolean enter;       // flag to see if the player has pressed enter
    public boolean right;       // flag to move right the player
    public boolean shoot;       // flag to shoot
    public boolean p;           // flag to pause the game
    public boolean g;           // flag to save the game
    public boolean c;           // flag to load the game
    public boolean r;           // flag to restart the game
    public boolean space;       // flag to restart the game when finished
    public boolean movement;    // flag to denote the player has pressed/released a key
    private boolean pressable;  // flag to do a one time press that has lasting effect
    
    private boolean keys[]; // to store all the flags for every key

    public KeyManager() {
        keys = new boolean[256];
        
        this.pressable = true;
    }

    public boolean isPressable() {
        return pressable;
    }

    public void setPressable(boolean pressable) {
        this.pressable = pressable;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed if a key isnt being pressed
        if(!movement){
            keys[e.getKeyCode()] = true;
            movement = true;
        } 
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to a key only if was pressed 
        if(movement && keys[e.getKeyCode()]){
            keys[e.getKeyCode()] = false;
            movement = false;
            setPressable(true);
        }  
        
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        left = keys[KeyEvent.VK_LEFT];
        up = keys[KeyEvent.VK_UP];
            down = keys[KeyEvent.VK_DOWN];
        right = keys[KeyEvent.VK_RIGHT];
        shoot = keys[KeyEvent.VK_SPACE];
        p = keys[KeyEvent.VK_P];
        g = keys[KeyEvent.VK_G];
        c = keys[KeyEvent.VK_C];
        r = keys[KeyEvent.VK_R];
        space = keys[KeyEvent.VK_SPACE];
        enter = keys[KeyEvent.VK_ENTER];
    }
    
}
