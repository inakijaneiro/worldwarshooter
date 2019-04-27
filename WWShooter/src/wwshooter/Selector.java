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
    
    
    public Selector(int x, int y, int width, int height, int speed, Game game){
        super(x, y, width, height);
        this.game = game;
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
            setY(getY() + 100);
        }
        if (getGame().getKeyManager().up && getGame().getKeyManager().isPressable()) {
            getGame().getKeyManager().setPressable(false);
               setY(getY() - 100);
        }
        
        if (getY() >= game.getHeight()/2 - game.getHeight()/6 + 200 + game.getHeight()/12){
            setY(game.getHeight()/3);
        }
        if (getY() <= game.getHeight() - 500){
            setY(game.getHeight()/3 + 200);
        }
        
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(Assets.selector, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
