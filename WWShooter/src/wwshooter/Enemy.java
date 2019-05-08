/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Formatter;

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
     *
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
     *
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
     *
     * @return if enemy inbounds
     */
    public boolean isInBounds() {

        if (getX() + getWidth() <= getLevel().getGame().getWidth()) {
            if (getX() >= 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Writes it's data in the saving file
     *
     * @param file
     */
    public void save(Formatter file) {
        file.format("%s%s", getX() + " ", getY() + " ");
    }

    /**
     * Loads it's necessary data from a file
     *
     * @param x
     * @param y
     */
    public void load(int x, int y) {
        setX(x);
        setY(y);
    }
    

    /**
     * Main tick method of the enemy
     */
    @Override
    public void tick() {
        currentAnimation.tick();
        if (Math.abs(player.getX() - getX()) < 500 && isInBounds()) {
            if (player.getX() - getX() > 0) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }

            if (player.getState() != Player.State.CROUCH) {
                if (state != State.SHOOT) {
                    if (direction == Direction.LEFT) {
                        setX(getX() - (int) (originalWidth * 0.4));
                    }
                    setWidth(originalWidth + (int) (originalWidth * 0.4));
                    currentAnimation.setIndex(0);
                    shootingOffset = true;
                    state = State.SHOOT;
                }
            } else {
                if (shootingOffset) {
                    setWidth(originalWidth);
                    if (direction == Direction.LEFT) {
                        setX(getX() + (int) (originalWidth * 0.4));
                    }
                    shootingOffset = false;
                }
                if (direction == Direction.RIGHT) {
                    currentAnimation.setFrames(Assets.firstEnemyIdle);
                } else {
                    currentAnimation.setFrames(Assets.firstEnemyIdleR);
                }
                state = State.IDLE;
            }

            if (state == State.SHOOT) {
                if (direction == Direction.RIGHT) {
                    currentAnimation.setFrames(Assets.firstEnemyShoot);
                } else {
                    currentAnimation.setFrames(Assets.firstEnemyShootR);
                }
            }
            long timeNow = System.currentTimeMillis();
            if ((System.currentTimeMillis() - lastShot >= 1000) && state == State.SHOOT) {
                lastShot = timeNow;
                if (direction == Direction.RIGHT) {
                    getLevel().getEnemyBullets().add(new Bullet(getX() + getWidth() - 50, getY() + getHeight() / 2, 7, 7, 5, getLevel(), Bullet.Direction.RIGHT));
                } else if (direction == Direction.LEFT) {
                    getLevel().getEnemyBullets().add(new Bullet(getX() + 50, getY() + getHeight() / 2, 7, 7, 5, getLevel(), Bullet.Direction.LEFT));
                }

            }

        } else {
            if (shootingOffset) {
                setWidth(originalWidth);
                if (direction == Direction.LEFT) {
                    setX(getX() + (int) (originalWidth * 0.4));
                }
                shootingOffset = false;
            }
            if (direction == Direction.RIGHT) {
                currentAnimation.setFrames(Assets.firstEnemyRun);
                setX(getX() + 2);
            } else {
                currentAnimation.setFrames(Assets.firstEnemyRunR);
                setX(getX() - 2);
            }
            state = State.RUN;
        }
    }

    /**
     * Render method of the enemy
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
