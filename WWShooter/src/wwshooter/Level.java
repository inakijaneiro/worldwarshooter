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
            g.drawImage(Assets.title, width/2 - width/12 - 80, height/2 - 300, width/3, height/6, null);
            g.drawImage(Assets.newGameButton, width/2 - width/12, height/2 - height/6, width/6, height/12, null);
            g.drawImage(Assets.continueButton, width/2 - width/12 , height/2 - height/6 + 100, width/6, height/12, null);
            g.drawImage(Assets.settingsButton, width/2 - width/12 , height/2 - height/6 + 200, width/6, height/12, null);
        }
    }

}
