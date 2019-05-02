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
    
    private Level level;
    private int speed;
    private boolean visible;
    public enum Direction {
        LEFT, RIGHT
    }
    private Direction direction;
    
    public Bullet(int x, int y,int width, int height, int speed, Level level, Direction direction){
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        this.visible = false;
        this.direction = direction;
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
    
    public Level getLevel() {
        return level;
    }

    @Override
    public void tick() {
        if (direction == Direction.RIGHT) {
            setX(getX()+getSpeed()); 
        }
        else if (direction == Direction.LEFT) {
            setX(getX() - getSpeed());
        }
    }

    @Override
    public void render(Graphics g) {
            g.drawImage(Assets.bullet, getX(),getY(), getWidth(), getHeight(), null); 
    }
    
}
