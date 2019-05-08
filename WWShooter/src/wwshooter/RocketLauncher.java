/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;

/**
 *
 * @author sergiodiosdado
 */
public class RocketLauncher extends Item {

    private Level level;
    private int speed;
    private Animation currentAnimation;
    private Player player;
    private long lastShot;
    private long shootingAnimation;
    private int originalWidth;
    private boolean shootingOffset;
    private boolean isShooting;

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

    public RocketLauncher(int x, int y, int width, int height, Level level, char direction) {
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        this.originalWidth = width;
        this.state = State.RUN;
        this.currentAnimation = new Animation(Assets.rocketLauncherRun, 100);
        this.player = getLevel().getPlayer();
        this.direction = direction == 'l' ? Direction.LEFT : Direction.RIGHT;
        this.shootingOffset = false;
        this.isShooting = false;
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

    @Override
    public void tick() {
        currentAnimation.tick();
        if (isInBounds()) {
            if (player.getX() - getX() > 0) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }
            if (System.currentTimeMillis() - shootingAnimation >= 500) {
                isShooting = false;
            }
            long timeNow = System.currentTimeMillis();
            if ((System.currentTimeMillis() - lastShot >= 5000)) {
                if (state != State.SHOOT) {
                    currentAnimation.setIndex(0);
                    setWidth(originalWidth + (int) (originalWidth * 0.5));
                }
                shootingAnimation = System.currentTimeMillis();
                isShooting = true;
                state = State.SHOOT;
                lastShot = timeNow;
                if (direction == Direction.RIGHT) {
                    currentAnimation.setFrames(Assets.rocketLauncherShoot);
                    getLevel().getRockets().add(new Bullet(getX() + getWidth()/2, getY() + (int)(getHeight()*0.35), 32, 16, 7, getLevel(), Bullet.Direction.RIGHT, 'r'));
                } else if (direction == Direction.LEFT) {
                    currentAnimation.setFrames(Assets.rocketLauncherShootR);
                    getLevel().getRockets().add(new Bullet(getX() + getWidth()/2, getY() + (int)(getHeight()*0.35), 32, 16, 7, getLevel(), Bullet.Direction.LEFT, 'r'));

                }
            } else {
                if (!isShooting) {
                    state = State.IDLE;
                    setWidth(originalWidth);
                    if (direction == Direction.LEFT) {
                        currentAnimation.setFrames(Assets.rocketLauncherIdleR);
                    } else {
                        currentAnimation.setFrames(Assets.rocketLauncherIdle);
                    }
                }
            }
        } else {
            if (direction == Direction.RIGHT) {
                currentAnimation.setFrames(Assets.rocketLauncherRun);
                setX(getX() + 2);
            } else {
                currentAnimation.setFrames(Assets.rocketLauncherRunR);
                setX(getX() - 2);
            }
            state = State.RUN;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);

    }

}
