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
    private int originalHeight;
    private boolean animationOffset;

    public enum State {
        RUN,
        IDLE,
        SHOOT,
        CROUCH
    };

    public enum Direction {
        LEFT,
        RIGHT
    };
    private State state;
    private Direction direction;

    /**
     * Main constructor for the player
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param speed
     * @param level
     */
    public Player(int x, int y, int width, int height, int speed, Level level) {
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        this.lastShot = 0;
        this.direction = Direction.RIGHT;
        this.originalWidth = width;
        this.originalHeight = height;
        currentAnimation = new Animation(Assets.playerIdle, 60);
        this.state = State.IDLE;
        this.animationOffset = false;
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

    /**
     * Method to get the Hitbox
     *
     * @return
     */
    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Returns the state of the player
     *
     * @return state
     */
    public State getState() {
        return state;
    }

    /**
     * Writes it's data in the saving file
     *
     * @param file
     */
    public void save(Formatter file) {
        file.format("%s%s%s%s", getX() + " ", getY() + " ", getWidth() + " ", getHeight() + " ");
        if (getState() == State.IDLE) {
            file.format("%s", 1 + " ");
        } else if (getState() == State.RUN) {
            file.format("%s", 2 + " ");
        } else if (getState() == State.SHOOT) {
            file.format("%s", 3 + " ");
        } else if (getState() == State.CROUCH) {
            file.format("%s", 4 + " ");
        }
    }

    /**
     * Loads it's necessary data from a file
     *
     * @param x
     * @param y
     */
    public void load(int x, int y, int w, int h, int s) {
        setX(x);
        setY(y);
        setWidth(w);
        setHeight(h);
        switch (s) {
            case 1:
                state = State.IDLE;
                break;
            case 2:
                state = State.RUN;
                break;
            case 3:
                state = State.SHOOT;
                break;
            case 4:
                if (direction == Direction.LEFT) {
                    setX(getX() + (int) (originalWidth / 2));
                }
                setY(getY() - (int) (originalHeight / 2));
                state = State.CROUCH;
                break;
            default:
                state = State.IDLE;
                break;
        }
    }

    /**
     * Ticker for the class
     */
    @Override
    public void tick() {
        currentAnimation.tick();
        // Declaration of flags to manage the rest of the behaviour
        boolean isShooting = getLevel().getKeyManager().space;
        boolean isCrouching = getLevel().getKeyManager().down;
        boolean goingLeft = getLevel().getKeyManager().left && !isCrouching;
        boolean goingRight = getLevel().getKeyManager().right && !isCrouching;
        // Checks the player's state and sets the animation index accordingly,
        // to avoid out-of-bounds error 
        if (state != State.RUN && (goingLeft || goingRight)) {
            currentAnimation.setIndex(0);

        }
        if (goingLeft || goingRight) {
            if (state == State.CROUCH) {
                setHeight(originalHeight);
                setY(getLevel().getGame().getHeight() - (int) (originalHeight));
            }
            state = State.RUN;
            // Since the animations have different widths, it adjusts.
            setWidth(originalWidth + (int) (originalWidth * 0.1));
        }

        if (goingLeft) {
            this.direction = Direction.LEFT;
            setX(getX() - getSpeed());
            currentAnimation.setFrames(Assets.playerRunR);
        } else if (goingRight) {
            this.direction = Direction.RIGHT;
            setX(getX() + getSpeed());
            currentAnimation.setFrames(Assets.playerRun);
        } else if (isCrouching) {
            if (state != State.CROUCH) {
                currentAnimation.setIndex(0);
                setY(getY() + (int) (originalHeight / 2));
                if (direction == Direction.LEFT) {
                    setX(getX() - (int) (originalWidth / 2));
                }
                animationOffset = true;
            }
            setWidth((int) (originalWidth * 2));
            setHeight((int) (originalHeight / 1.75));
            if (direction == Direction.LEFT) {
                currentAnimation.setFrames(Assets.playerCrouchR);
            } else {
                currentAnimation.setFrames(Assets.playerCrouch);
            }
            state = State.CROUCH;
        } else if (isShooting) {
            setHeight(originalHeight);
            setY(getLevel().getGame().getHeight() - (int) (originalHeight));
            if (state != State.SHOOT) {
                if (state == State.CROUCH && direction == Direction.RIGHT) {
                    animationOffset = false;
                } else if (direction == Direction.LEFT) {
                    if (state != State.CROUCH) {
                        setX(getX() - (int) (originalWidth * 0.8));
                        animationOffset = true;
                    }
                }
                currentAnimation.setIndex(0);
                state = State.SHOOT;
                // Since the animations have different widths, it adjusts.
                setWidth(originalWidth * 2 - (int) (originalWidth * 0.2));
            }
            if (direction == Direction.LEFT) {
                currentAnimation.setFrames(Assets.playerShootR);
            } else {
                currentAnimation.setFrames(Assets.playerShoot);
            }

        } else {
            // When no key is pressed goes back to idle state and adjusts width
            if (animationOffset) {
                animationOffset = false;
                if (state == State.SHOOT) {
                    setX(getX() + (int) (originalWidth * 0.8));
                } else if (state == State.CROUCH) {
                    if (direction == Direction.LEFT) {
                        setX(getX() + (int) (originalWidth / 2));
                    }
                    setY(getY() - (int) (originalHeight / 2));
                }
            }
            state = State.IDLE;
            setWidth(originalWidth);
            setHeight(originalHeight);
            if (direction == Direction.RIGHT) {
                currentAnimation.setFrames(Assets.playerIdle);
            } else {
                currentAnimation.setFrames(Assets.playerIdleR);
            }
        }

        // Collisions with the screen
        if (getX() + getWidth() >= getLevel().getGame().getWidth()) { // right
            if(getLevel().level == Level.LevelName.Level3){
                    if(getLevel().getStage() == 3 && getLevel().getBoss().getHealth() > 0){
                        setX(getLevel().getGame().getWidth() - getWidth());
                    }
            }
            if (!getLevel().getEnemies().isEmpty()){
                setX(getLevel().getGame().getWidth() - getWidth());
            } else {
                setX(150);
                getLevel().setStage(getLevel().getStage() + 1);
                if (getLevel().getStage() <= 3) {
                    if (getLevel().level == Level.LevelName.Level1) {
                        Assets.setLevelBackground(1, getLevel().getStage());
                        for (int i = 1; i <= 5; i++) {
                            getLevel().getEnemies().add(new Enemy(getLevel().getGame().getWidth() + 300 * i, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'l'));
                        }
                        for (int i = 1; i <= 5; i++) {
                            getLevel().getEnemies().add(new Enemy(- 300 * i, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'r'));
                        }
                    } else if (getLevel().level == Level.LevelName.Level2) {
                        Assets.setLevelBackground(2, getLevel().getStage());
                        for (int i = 1; i <= 5; i++) {
                            getLevel().getEnemies().add(new Enemy(getLevel().getGame().getWidth() + 300 * i, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'l'));
                        }
                        for (int i = 1; i <= 5; i++) {
                            getLevel().getEnemies().add(new Enemy(- 300 * i, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'r'));
                        }
                        getLevel().getRocketLaunchers().add(new RocketLauncher(getLevel().getGame().getWidth() + 300, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'l'));
                        getLevel().getRocketLaunchers().add(new RocketLauncher(-300, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'r'));

                    } else if (getLevel().level == Level.LevelName.Level3) {
                        Assets.setLevelBackground(3, getLevel().getStage());
                        for (int i = 1; i <= 6; i++) {
                            getLevel().getEnemies().add(new Enemy(getLevel().getGame().getWidth() + 300 * i, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'l'));
                        }
                        for (int i = 1; i <= 6; i++) {
                            getLevel().getEnemies().add(new Enemy(- 300 * i, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'r'));
                        }
                        getLevel().getRocketLaunchers().add(new RocketLauncher(getLevel().getGame().getWidth() + 300, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'l'));
                        getLevel().getRocketLaunchers().add(new RocketLauncher(-300, getLevel().getGame().getHeight() - 350, 150, 350, getLevel(), 'r'));
                    }
                } else {
                    getLevel().getGame().changeLevel();
                }
            }
        }
        if (getX() <= 0) { // left
            setX(0);
        }

        // Shooting with 1 second of delay
        long timeNow = System.currentTimeMillis();
        if (state == State.SHOOT && (System.currentTimeMillis() - lastShot >= 500)) {
            lastShot = timeNow;
            Assets.shotSound.play();
            if (direction == Direction.RIGHT) {
                getLevel().getBullets().add(new Bullet(getX() + getWidth() - 50, getY() + getHeight() / 2, 7, 7, 5, getLevel(), Bullet.Direction.RIGHT));
            } else if (direction == Direction.LEFT) {
                getLevel().getBullets().add(new Bullet(getX() + 50, getY() + getHeight() / 2, 7, 7, 5, getLevel(), Bullet.Direction.LEFT));
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
        for (int i = 0; i < getLevel().getGame().getLives(); i++) {
            g.drawImage(Assets.lives, 1000 + 90 * i, 10, 80, 80, null);
        }
    }

}
