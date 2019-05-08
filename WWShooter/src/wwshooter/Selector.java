/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author inakijaneiro
 */
public class Selector extends Item {

    private Level level;
    private int btnHeight;
    private int spaceBetween;
    private int position; //starts at 0, until n-1
    private ArrayList<Button> buttons;

    public Selector(int x, int y, int width, int height, Level level, ArrayList<Button> buttons, int spaceBetween) {
        super(x, y, width, height);
        this.level = level;
        this.btnHeight = buttons.get(0).getHeight();
        this.spaceBetween = spaceBetween;
        this.buttons = buttons;
        this.position = 0;
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
     * Method to get the position
     * @return 
     */
    public int getPosition(){
        return position;
    }
    /**
     * Method to get the Buttons
     * @return 
     */
    public ArrayList<Button> getButtons() {
        return buttons;
    }
    /**
     * Tick method for the selector
     */
    @Override
    public void tick() {
        if (getButtons().get(0).isVisible()) {
            if (getLevel().getKeyManager().down && getLevel().getKeyManager().isPressable()) {
                getLevel().getKeyManager().setPressable(false);
                Assets.selectorSound.setVolume(level.getGame().getVolume());
                Assets.selectorSound.play();
                if (position + 1 >= 0 && position + 1 < buttons.size()) {
                    position++;
                    setY(buttons.get(position).getY() + getHeight() / 2 - 10);
                } else {
                    position = 0;
                    setY(buttons.get(position).getY() + getHeight() / 2 - 10);
                }
            }
            if (getLevel().getKeyManager().up && getLevel().getKeyManager().isPressable()) {
                getLevel().getKeyManager().setPressable(false);
                Assets.selectorSound.setVolume(level.getGame().getVolume());
                Assets.selectorSound.play();
                if (position - 1 >= 0) {
                    position--;
                    setY(buttons.get(position).getY() + getHeight() / 2 - 10);
                } else {
                    position = buttons.size() - 1;
                    setY(buttons.get(position).getY() + getHeight() / 2 - 10);
                }
            }
        }
        if ((getLevel().getKeyManager().enter || getLevel().getKeyManager().space) && getLevel().getKeyManager().isPressable()) {
            Assets.selectSound.play();    
            getLevel().getKeyManager().setPressable(false);
            if (position == 0) {
                getLevel().getGame().changeLevel();
            }
        }
    }
    /**
     * Main render method
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.selector, getX(), getY(), getWidth(), getHeight(), null);
    }

}
