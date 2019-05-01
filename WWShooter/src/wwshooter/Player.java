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
    private Game game;
    private long lastShot;
    private Animation currentAnimation;
    private int originalWidth;
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

    public Player(int x, int y, int width, int height, int speed, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.speed = speed;
        this.lastShot = 0;
        this.direction = Direction.RIGHT;
        this.originalWidth = width;
        currentAnimation = new Animation(Assets.playerIdle, 60);
        this.state = State.IDLE;
    }

    /**
     * Gets the game context
     *
     * @return <code>Game game</code>
     */
    public Game getGame() {
        return game;
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
        boolean goingLeft = getGame().getKeyManager().left;
        boolean goingRight = getGame().getKeyManager().right;
        boolean isShooting = getGame().getKeyManager().space;

        // Checks the player's state and sets the animation index accordingly,
        // to avoid out-of-bounds error
        if (state != State.RUN && (goingLeft || goingRight)) {
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
                currentAnimation.setIndex(0);
                state = State.SHOOT;
                // Since the animations have different widths, it adjusts.
                setWidth(originalWidth * 2 - (int) (originalWidth*0.2));
            }
            currentAnimation.setFrames(Assets.playerShoot);
        } else {
            // When no key is pressed goes back to idle state and adjusts width
            state = State.IDLE;
            setWidth(originalWidth);
            if (direction == Direction.RIGHT) {
                currentAnimation.setFrames(Assets.playerIdle);
            } else {
                currentAnimation.setFrames(Assets.playerIdleR);
            }
        }

        // Collisions with the screen
        if (getX() + getWidth() >= getGame().getWidth()) {
            setX(getGame().getWidth() - getWidth());
        }
        if (getX() <= 0) {
            setX(0);
        }

        // Shooting with 1 second of delay
        long timeNow = System.currentTimeMillis();
        if (getGame().getKeyManager().shoot && (System.currentTimeMillis() - lastShot >= 1000)) {
            lastShot = timeNow;
            getGame().getBullets().add(new Bullet(getX(), getY(), 7, 7, 5, game));
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
