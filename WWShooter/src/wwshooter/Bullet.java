/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;

/**
 *
 * @author luis_
 */
public class Bullet extends Item{
    
    private Game game;
    private int speed;
    private boolean visible;
    
    public Bullet(int x, int y,int width, int height, int speed, Game game){
        super(x, y, width, height);
        this.game = game;
        this.speed = speed;
        this.visible = false;
    }

    public boolean isVisible() {
        return visible;
    } 

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void tick() {
        setX(getX()+getSpeed());            
    }

    @Override
    public void render(Graphics g) {
            g.drawImage(Assets.bullet, getX(),getY(), getWidth(), getHeight(), null); 
    }
    
}
