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
    /**
     * Constructor for the class Bullet
     * @param x
     * @param y
     * @param width
     * @param height
     * @param speed
     * @param level
     * @param direction 
     */
    public Bullet(int x, int y,int width, int height, int speed, Level level, Direction direction){
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        this.visible = false;
        this.direction = direction;
    }
    /**
     * Method to check if the bullet is visible
     * @return <code>boolean</code>
     */
    public boolean isVisible() {
        return visible;
    } 
    /**
     * Sets visible
     * @param visible 
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    /**
     * Get speed
     * @return <code> int speed </code>
     */
    public int getSpeed() {
        return speed;
    }
    /**
     * Get level
     * @return <code> Level level </code>
     */
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
    /**
     * Main tick of the bullet
     */
    @Override
    public void tick() {
        if (direction == Direction.RIGHT) {
            setX(getX()+getSpeed()); 
        }
        else if (direction == Direction.LEFT) {
            setX(getX() - getSpeed());
        }
    }
    /**
     * Render method for the bullet
     * @param g 
     */
    @Override
    public void render(Graphics g) {
            g.drawImage(Assets.bullet, getX(),getY(), getWidth(), getHeight(), null); 
    }
    
}
