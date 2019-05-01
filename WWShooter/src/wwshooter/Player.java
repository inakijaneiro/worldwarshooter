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
public class Player extends Item {

    private int speed;
    private Level level;
    private long lastShot;
    private Animation currentAnimation;
    private byte direction;
    private int originalWidth;
    public enum State{
        RUN,
        IDLE,
        SHOOT
    };
    private State state;

    public Player(int x, int y, int width, int height, int speed, Level level) {
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        this.lastShot = 0;
        this.direction = 1;
        this.originalWidth = width;
        currentAnimation = new Animation(Assets.playerIdle, 60);
        this.state = State.IDLE;
    }

    /**
     * Gets the game context
     *
     * @return <code>Game game</code>
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Gets the speed
     *
     * @return <code>int</code> speed
     */
    public int getSpeed() {
        return speed;
    }

    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
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
     * Ticker for the class
     */
    @Override
    public void tick() {
        currentAnimation.tick();
        // Moving player depending on flags
        boolean goingLeft = getLevel().getKeyManager().left;
        boolean goingRight = getLevel().getKeyManager().right;
        boolean isShooting = getLevel().getKeyManager().space;
        
        if(state != State.RUN && (goingLeft || goingRight)){
                currentAnimation.setIndex(0);
        }
        if(goingLeft || goingRight){
            state = State.RUN;
        }
        if (goingLeft) {
            this.direction = -1;
            setX(getX() - getSpeed());
            currentAnimation.setFrames(Assets.playerRunR);
        } else if (goingRight) {
            this.direction = 1;
            setX(getX() + getSpeed());
            currentAnimation.setFrames(Assets.playerRun);
        } else {
            if (direction > 0) {
                state = State.IDLE;
                currentAnimation.setFrames(Assets.playerIdle);
            } else {
                state = State.IDLE;
                currentAnimation.setFrames(Assets.playerIdleR);
            }
        }

        // Collisions with the screen
        if (getX() + getWidth() >= getLevel().getGame().getWidth()) { // right
            setX(0);
            getLevel().setStage(getLevel().getStage() + 1);
            if (getLevel().getStage() <= 3) {
                Assets.setLevelBackground(1, getLevel().getStage());
            }
        }
        if (getX() <= 0) { // left
            setX(0);
        }

        // Shooting with 1 second of delay
        long timeNow = System.currentTimeMillis();
        if (getLevel().getKeyManager().shoot && (System.currentTimeMillis() - lastShot >= 1000)) {
            lastShot = timeNow;
//            getLevel().getBullets().add(new Bullet(getX(), getY(), 7, 7, 5, game));
        }
    }

    /**
     * Draws the player on the canvas
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
