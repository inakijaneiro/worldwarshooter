/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;
import java.awt.Rectangle;

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
    
    /**
     * Creates a Circle object and simulates the "hit box" of the ball
     *
     * @return new Circle
     */
    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    /**
     * Checks if there was a collision with another instance and returns a
     * boolean
     *
     * @param obj
     * @return <code>boolean</code>
     */
    public boolean intersecta(Object obj) {
        return (obj instanceof Enemy && getHitbox().intersects(((Enemy) (obj)).getHitbox())
                || obj instanceof Player && getHitbox().intersects(((Player) (obj)).getHitbox()));
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
