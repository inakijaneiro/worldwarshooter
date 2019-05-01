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
public class Enemy extends Item{
    
    private Level level;
    private int speed;
    private Animation currentAnimation;

    public Enemy(int x, int y, int width, int height, int speed, Level level) {
        super(x, y, width, height);
        this.level = level;
        this.speed = speed;
        currentAnimation = new Animation(Assets.firstEnemyRun, 60);
    }

    @Override
    public void tick() {
        currentAnimation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
