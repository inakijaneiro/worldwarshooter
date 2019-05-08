/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;

/**
 *
 * @author admin
 */
public class Tank extends Item{
    
    int health;
    Animation animation;
    
    public Tank(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.health = 10;
        this.animation = new Animation(Assets.tank, 60);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
    
    
}
