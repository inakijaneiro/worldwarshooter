/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;

/**
 *
 * @author inakijaneiro
 */
public class Level {
    
    public Player player;
    public Game game;
    private Selector selector;       
    enum LevelName {
        MainMenu, Level1, Level2
    }
    LevelName level;
    
    
    Level(LevelName levelName, Game game) {
        this.game = game;
        this.player = new Player(game.getWidth() / 2 - 24, game.getHeight() - 64, 48, 48, 5, game);
        this.level = levelName;
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
        
    }
    
    public void render(Graphics g){
        int width = game.getWidth();
        int height = game.getHeight();
        switch (level) {
            case MainMenu:
            g.drawImage(Assets.background, 0, 0, width, height, null);
            g.drawImage(Assets.title, width/2 - 275, 100, 550, 209, null);
            for (int i = 0; i < game.getButtons().size(); i++) {
                game.getButtons().get(i).render(g);
            }
            break;
        }
    }

}
