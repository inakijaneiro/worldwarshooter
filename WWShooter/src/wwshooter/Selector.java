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

    private Game game;
    private int btnHeight;
    private int spaceBetween;
    private int position;
    private ArrayList<Button> buttons;

    public Selector(int x, int y, int width, int height, Game game, ArrayList<Button> buttons, int spaceBetween) {
        super(x, y, width, height);
        this.game = game;
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
    public Game getGame() {
        return game;
    }
    
    public int getPosition(){
        return position;
    }

    @Override
    public void tick() {
        if (getGame().getKeyManager().down && getGame().getKeyManager().isPressable()) {
            getGame().getKeyManager().setPressable(false);
            if (position + 1 >= 0 && position + 1 < buttons.size()) {
                position++;
                setY(buttons.get(position).getY() + getHeight() / 2 - 10);
            }else {
                position = 0;
                setY(buttons.get(position).getY() + getHeight() / 2 - 10);
            }
        }
        if (getGame().getKeyManager().up && getGame().getKeyManager().isPressable()) {
            getGame().getKeyManager().setPressable(false);
            if (position - 1 >= 0) {
                position--;
                setY(buttons.get(position).getY() + getHeight() / 2 - 10);
            }else {
                position = buttons.size() - 1;
                setY(buttons.get(position).getY() + getHeight() / 2 - 10);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.selector, getX(), getY(), getWidth(), getHeight(), null);
    }

}
