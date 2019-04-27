/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;

/**
 *
 * @author inakijaneiro
 */
public class Selector extends Item {
    
    private Game game;
    private int btnHeight;
    private int spaceBetween;
    
    public Selector(int x, int y, int width, int height, Game game, int btnHeight, int spaceBetween){
        super(x, y, width, height);
        this.game = game;
        this.btnHeight = btnHeight;
        this.spaceBetween = spaceBetween;
    }
    
    /**
     * Gets the game context
     * 
     * @return <code>Game game</code>
     */
    public Game getGame() {
        return game;
    }

    @Override
    public void tick() {
        if (getGame().getKeyManager().down && getGame().getKeyManager().isPressable()) {
            getGame().getKeyManager().setPressable(false);
            setY(getY() + btnHeight + spaceBetween);
        }
        if (getGame().getKeyManager().up && getGame().getKeyManager().isPressable()) {
            getGame().getKeyManager().setPressable(false);
               setY(getY() - btnHeight - spaceBetween);
        }
        
        if (getY() < 372){
            setY(570);
        }
        if (getY() > 635){
            setY(372);
        }
        
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(Assets.selector, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
