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
    private int originalWidth;
    private boolean shootingOffset;
    public enum State {
        RUN,
        IDLE,
        SHOOT
    };
    public enum Direction{
        LEFT,
        RIGHT
    };
    private State state;
    private Direction direction;

    public Player(int x, int y, int width, int height, int speed, Level level) {
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        this.lastShot = 0;
        this.direction = Direction.RIGHT;
        this.originalWidth = width;
        currentAnimation = new Animation(Assets.playerIdle, 60);
        this.state = State.IDLE;
        this.shootingOffset = false;
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
        // Declaration of flags to manage the rest of the behaviour
        boolean goingLeft = getLevel().getKeyManager().left;
        boolean goingRight = getLevel().getKeyManager().right;
        boolean isShooting = getLevel().getKeyManager().space;
        
        // Checks the player's state and sets the animation index accordingly,
        // to avoid out-of-bounds error 
        if(state != State.RUN && (goingLeft || goingRight)){
                currentAnimation.setIndex(0);

        }
        if (goingLeft || goingRight) {
            state = State.RUN;
            // Since the animations have different widths, it adjusts.
            setWidth(originalWidth + (int) (originalWidth*0.1));
        }
        
        if (goingLeft) {
            this.direction = Direction.LEFT;
            setX(getX() - getSpeed());
            currentAnimation.setFrames(Assets.playerRunR);
        } else if (goingRight) {
            this.direction = Direction.RIGHT;
            setX(getX() + getSpeed());
            currentAnimation.setFrames(Assets.playerRun);
        } else if (isShooting) {
            if (state != State.SHOOT) {
                if(direction == Direction.LEFT){
                    setX(getX() - (int)(originalWidth*0.8));
                    shootingOffset = true;
                }
                currentAnimation.setIndex(0);
                state = State.SHOOT;
                // Since the animations have different widths, it adjusts.
                setWidth(originalWidth * 2 - (int) (originalWidth*0.2));
            }
            if(direction == Direction.LEFT){
                currentAnimation.setFrames(Assets.playerShootR);
            }else{
                currentAnimation.setFrames(Assets.playerShoot);
            }
            
        } else {
            // When no key is pressed goes back to idle state and adjusts width
            if(shootingOffset){
                setX(getX() + (int)(originalWidth*0.8));
                shootingOffset = false;
            }
            state = State.IDLE;
            setWidth(originalWidth);
            if (direction == Direction.RIGHT) {
                currentAnimation.setFrames(Assets.playerIdle);
            } else {
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
        if (getLevel().getKeyManager().shoot && (System.currentTimeMillis() - lastShot >= 500)) {
            lastShot = timeNow;
            if (direction == Direction.RIGHT) {
                getLevel().getBullets().add(new Bullet(getX() + getWidth() - 50, getY() + getHeight()/2, 7, 7, 5, getLevel(), Bullet.Direction.RIGHT));
            }
            else if (direction == Direction.LEFT) {
                getLevel().getBullets().add(new Bullet(getX() + 50, getY() + getHeight()/2, 7, 7, 5, getLevel(), Bullet.Direction.LEFT));
            }
            
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
        for(int i = 0; i < getLevel().getGame().getLives(); i++){
            g.drawImage(Assets.lives, 1000 + 90 * i, 10, 80, 80, null);
        }
    }

}