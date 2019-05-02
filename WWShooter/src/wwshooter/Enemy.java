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
public class Enemy extends Item {

    private Level level;
    private int speed;
    private Animation currentAnimation;
    private Player player;
    private int originalWidth;
    private boolean shootingOffset;

    public enum State {
        RUN,
        SHOOT
    };

    public enum Direction {
        LEFT,
        RIGHT
    };
    private State state;
    private Direction direction;

    public Enemy(int x, int y, int width, int height, Level level, char direction) {
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        this.originalWidth = width;
        this.currentAnimation = new Animation(Assets.firstEnemyRunR, 80);
        this.player = getLevel().getPlayer();
        this.direction = direction == 'l' ? Direction.LEFT : Direction.RIGHT;
        this.shootingOffset = false;
    }

    public Level getLevel() {
        return this.level;
    }

    @Override
    public void tick() {
        boolean isShooting = false;
        currentAnimation.tick();
        if (Math.abs(player.getX() - getX()) < 700) {
            isShooting = true;
        } else {
            setX(getX() - 2);
            isShooting = false;
        }

        if (isShooting) {
            if (direction == Direction.LEFT) {
                currentAnimation.setFrames(Assets.firstEnemyShootR);
                if(!shootingOffset){
                    setWidth(originalWidth + (int)(originalWidth*0.4));
                    setX(getX() - (int)(originalWidth*0.4));
                }
                shootingOffset = true;
            } else {
                setWidth(originalWidth);
            }
            isShooting = false;
        } else {
            if (shootingOffset) {
                setX(getX() + (int)(originalWidth*0.4));
                shootingOffset = false;
            }
            setWidth(originalWidth);
            currentAnimation.setFrames(Assets.firstEnemyRunR);
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
