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
 * @author sergiodiosdado
 */
public class Enemy extends Item {

    private Level level;
    private int speed;
    private Animation currentAnimation;
    private Player player;
    private long lastShot;
    private int originalWidth;
    private boolean shootingOffset;

    public enum State {
        RUN,
        SHOOT,
        IDLE
    };

    public enum Direction {
        LEFT,
        RIGHT
    };
    private State state;
    private Direction direction;
    /**
     * Main constructor of enemy
     * @param x
     * @param y
     * @param width
     * @param height
     * @param level
     * @param direction 
     */
    public Enemy(int x, int y, int width, int height, Level level, char direction) {
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        this.originalWidth = width;
        this.state = State.RUN;
        this.currentAnimation = new Animation(Assets.firstEnemyRunR, 100);
        this.player = getLevel().getPlayer();
        this.direction = direction == 'l' ? Direction.LEFT : Direction.RIGHT;
        this.shootingOffset = false;
    }
    /**
     * Get's the level the enemy is in
     * @return 
     */
    public Level getLevel() {
        return this.level;
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
     * Checks if the enemy is inside the stage
     * @return if enemy inbounds
     */
    public boolean isInBounds(){
        
        if (getX() + getWidth() <= getLevel().getGame().getWidth()) {
            if (getX() >= 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * Main tick method of the enemy
     */
    @Override
    public void tick() {
        boolean isShooting = false;
        boolean isIdle = false;
        currentAnimation.tick();
        if (Math.abs(player.getX() - getX()) < 700 && isInBounds()) {
            if (player.getState() != Player.State.CROUCH){
                if (state != State.SHOOT) {
                    if (shootingOffset){
                        setX(getX() - (int) (originalWidth * 0.4));
                    }
                    currentAnimation.setIndex(0);
                }
                setWidth(originalWidth + (int) (originalWidth * 0.4));
                state = State.SHOOT;
                isShooting = true;
            } 
            else {
                if (state == State.SHOOT) {
                    setWidth(originalWidth);
                }
                isShooting = false;
                isIdle = true;
            }
        } 
        else {
            setX(getX() - 2);
            isShooting = false;
            isIdle = false;
            state = State.RUN;
        }

        if (isShooting) {
            state = State.SHOOT;
            if (direction == Direction.LEFT) {
                currentAnimation.setFrames(Assets.firstEnemyShootR);
                if (!shootingOffset) {
                    setWidth(originalWidth + (int) (originalWidth * 0.4));
                    setX(getX() - (int) (originalWidth * 0.4));
                }
                shootingOffset = true;
            } else {
                setWidth(originalWidth);
            }
            // Shooting with 1 second of delay
            long timeNow = System.currentTimeMillis();
            if ((System.currentTimeMillis() - lastShot >= 1000)) {
                lastShot = timeNow;
                if (direction == Direction.RIGHT) {
                    getLevel().getEnemyBullets().add(new Bullet(getX() + getWidth() - 50, getY() + getHeight() / 2, 7, 7, 5, getLevel(), Bullet.Direction.RIGHT));
                } else if (direction == Direction.LEFT) {
                    getLevel().getEnemyBullets().add(new Bullet(getX() + 50, getY() + getHeight() / 2, 7, 7, 5, getLevel(), Bullet.Direction.LEFT));
                }

            }
            isShooting = false;
        } 
        else if (isIdle) {
            
            if (state == State.SHOOT) {
                currentAnimation.setIndex(0);
                setX(getX() + (int) (originalWidth * 0.4));
            } 
            state = State.IDLE;
            currentAnimation.setFrames(Assets.firstEnemyIdleR);
        } else {
            if (shootingOffset) {
                setX(getX() + (int) (originalWidth * 0.4));
                shootingOffset = false;
            }
            setWidth(originalWidth);
            currentAnimation.setFrames(Assets.firstEnemyRunR);
        }

    }
    /**
     * Render method of the enemy
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
