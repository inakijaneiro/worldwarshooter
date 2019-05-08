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
    Level level;
    int lastShot;
    
    public Tank(int x, int y, int width, int height, Level level) {
        super(x, y, width, height);
        this.health = 10;
        this.animation = new Animation(Assets.tank, 60);
        this.level = level;
        this.lastShot = 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Level getLevel() {
        return level;
    }
        
    @Override
    public void tick() {
        animation.tick();
        if(lastShot > 60){
            getLevel().getRockets().push(new Bullet(getX() + getWidth()/2, getY() + (int)(getHeight()*0.45), 32, 16, 7, getLevel(), Bullet.Direction.LEFT, 'r'));
            lastShot = 0;
        }else{
            lastShot++;
        }
        for(int i = 0; i < getLevel().getBullets().size(); i++){
            if(getLevel().getBullets().get(i).getX() == getX() + 100){
                setHealth(getHealth() - 1);
                getLevel().getBullets().remove(i);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
    
    
}
