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
public class Level {

    public Player player;
    public Game game;
    private Selector selector;
    private ArrayList<Button> buttons;
    private int width;
    private int height;

    enum LevelName {
        MainMenu, Level1, Level2
    }
    LevelName level;

    Level(LevelName levelName, Game game) {
        this.game = game;
        this.player = new Player(50, 20, 150, 350, 5, game);
        this.level = levelName;
        this.width = game.getWidth();
        this.height = game.getHeight();
        this.buttons = new ArrayList<Button>();
        switch (levelName) {
            case MainMenu:
                buttons.add(new Button(game.getWidth() / 2 - 232, 350, 464, 90, "newgame"));
                buttons.add(new Button(game.getWidth() / 2 - 232, 460, 464, 90, "continue"));
                buttons.add(new Button(game.getWidth() / 2 - 232, 570, 464, 90, "settings"));
                selector = new Selector(width / 2 - 282, 370, 35, 55, this, buttons, 20);
                break;
        }
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    /**
     * Gets the Player instance
     *
     * @return <code>Player</code> player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player instance
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void tick() {
        switch (level) {
            case MainMenu:
                selector.tick();
                player.tick();
                break;
        }

    }

    public void render(Graphics g) {
        int width = game.getWidth();
        int height = game.getHeight();
        switch (level) {
            case MainMenu:
                g.drawImage(Assets.background, 0, 0, width, height, null);
                g.drawImage(Assets.title, width / 2 - 275, 100, 550, 209, null);
                for (int i = 0; i < getButtons().size(); i++) {
                    getButtons().get(i).render(g);
                }
                player.render(g);

                selector.render(g);
                break;
        }
    }

}
