/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

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
    private int stage;
    private boolean settingsMenu;
    private LinkedList<Bullet> bullets;
    private LinkedList<Bullet> enemyBullets;
    private ArrayList<Enemy> enemies;

    enum LevelName {
        MainMenu, Level1, Level2
    }
    LevelName level;
    /**
     * Main constructor of the level
     * @param levelName
     * @param game 
     */
    Level(LevelName levelName, Game game) {
        this.game = game;
        this.level = levelName;
        this.width = game.getWidth();
        this.height = game.getHeight();
        this.buttons = new ArrayList<Button>();
        this.bullets = new LinkedList<Bullet>();
        this.enemyBullets = new LinkedList<Bullet>();
        this.enemies = new ArrayList<Enemy>();
        switch (levelName) {
            case MainMenu:
                Assets.menuMusic.setLooping(true);
                Assets.menuMusic.play();
                buttons.add(new Button(game.getWidth() / 2 - 232, 350, 464, 90, "newgame"));
                buttons.add(new Button(game.getWidth() / 2 - 232, 460, 464, 90, "continue"));
                buttons.add(new Button(game.getWidth() / 2 - 232, 570, 464, 90, "settings"));
                selector = new Selector(width / 2 - 282, 370, 35, 55, this, buttons, 20);
                break;
            case Level1:
                Assets.menuMusic.stop();
                Assets.backgroundMusic.setLooping(true);
                Assets.backgroundMusic.play();
                this.player = new Player(0, height - 350, 150, 350, 5, this);
                for (int i = 1; i <= 5; i++) {
                    enemies.add(new Enemy(width + 300 * i, height - 350, 150, 350, this, 'l'));
                }
                this.stage = 1;
                break;
        }
        this.settingsMenu = false;
    }
    /**
     * Get game method
     * @return <code> Game </code> game
     */
    public Game getGame() {
        return game;
    }
    /**
     * Method to get the bullets of the level
     * @return <code>LikedList</code> bullets
     */
    public LinkedList<Bullet> getBullets() {
        return bullets;
    }
    /**
     * Method to get the enemy bullets
     * @return <code>LinkedList</code> enemyBullets
     */
    public LinkedList<Bullet> getEnemyBullets(){
        return enemyBullets;
    }
    /**
     * Boolean to check if the player is on the settings menu
     * @return 
     */
    public boolean isSettingsMenu() {
        return settingsMenu;
    }
    /**
     * Access to the Linked List of the enemies
     * @return 
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    /**
     * Set the settingsMenu boolean
     * @param settingsMenu 
     */
    public void setSettingsMenu(boolean settingsMenu) {
        this.settingsMenu = settingsMenu;
    }
    /**
     * Get the buttons in the main menu
     * @return 
     */
    public ArrayList<Button> getButtons() {
        return buttons;
    }
    /**
     * Method to get the current stage
     * @return 
     */
    public int getStage() {
        return stage;
    }
    /**
     * Method to get the Key Manager
     * @return 
     */
    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }
    /**
     * Method to set the buttons Linked List
     * @param buttons 
     */
    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }
    /**
     * Method to get the selector
     * @return 
     */
    public Selector getSelector() {
        return selector;
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
    /**
     * Method to get the stage
     * @param stage 
     */
    public void setStage(int stage) {
        this.stage = stage;
    }
    /**
     * Main tick method of the level
     */
    public void tick() {
        switch (level) {
            case MainMenu:
                selector.tick();
                if (isSettingsMenu() && getKeyManager().right && getKeyManager().isPressable()) {
                    if (getGame().getVolume() >= 10) {
                        getGame().setVolume(10);
                    } else {
                        getGame().setVolume(getGame().getVolume() + 1);
                    }
                    getKeyManager().setPressable(false);
                }

                if (isSettingsMenu() && getKeyManager().left && getKeyManager().isPressable()) {
                    if (getGame().getVolume() <= 1) {
                        getGame().setVolume(1);
                    } else {
                        getGame().setVolume(getGame().getVolume() - 1);
                    }
                    getKeyManager().setPressable(false);
                }

                if (!isSettingsMenu() && getSelector().getPosition() == 2 && (getKeyManager().space || getKeyManager().enter) && getKeyManager().isPressable()) {
                    getButtons().get(0).setVisible(false);
                    getButtons().get(1).setVisible(false);
                    getButtons().get(2).setType("back");

                    getSelector().getButtons().get(0).setVisible(false);
                    getSelector().getButtons().get(1).setVisible(false);
                    getSelector().getButtons().get(2).setType("back");

                    setSettingsMenu(true);
                    getKeyManager().setPressable(false);
                } else if (isSettingsMenu() && getSelector().getPosition() == 2 && (getKeyManager().space || getKeyManager().enter) && getKeyManager().isPressable()) {
                    getButtons().get(0).setVisible(true);
                    getButtons().get(1).setVisible(true);
                    getButtons().get(2).setType("settings");

                    getSelector().getButtons().get(0).setVisible(true);
                    getSelector().getButtons().get(1).setVisible(true);
                    getSelector().getButtons().get(2).setType("settings");

                    setSettingsMenu(false);
                    getKeyManager().setPressable(false);
                }
                break;
            case Level1:
                player.tick();
                for (int i = 0; i < bullets.size(); i++) {
                    Bullet bullet = bullets.get(i);
                    //Move bullets
                    bullet.tick();
                    if (bullet.getX() + bullet.getWidth() >= getGame().getWidth() || bullet.getX() <= 0) {
                        bullets.remove(i);
                    }
                    for (int j = 0; j < enemies.size(); j++) {
                        if (bullet.intersecta(enemies.get(j))) {
                            Assets.enemyHurt.play();
                            enemies.remove(j);
                            bullets.remove(i);
                        }
                    }
                }
                for(Bullet bullet: enemyBullets){
                    bullet.tick();
                }
                for (Enemy enemy : enemies) {
                    enemy.tick();
                }
                break;
        }

    }
    /**
     * Render method for the level
     * @param g 
     */
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
                selector.render(g);
                if (isSettingsMenu()) {
                    g.drawImage(Assets.musicController, width / 2 - 200, 380, 400, 100, null);
                    for (int i = 0; i < getGame().getVolume(); i++) {
                        g.drawImage(Assets.volumePill, width / 2 - 159 + i * 32, 440, 30, 30, null);
                    }
                }
                break;
            case Level1:
                g.drawImage(Assets.level1Background, 0, 0, width, height, null);
                player.render(g);
                for (int i = 0; i < bullets.size(); i++) {
                    //Render bullets
                    bullets.get(i).render(g);
                }
                for (Enemy enemy : enemies) {
                    enemy.render(g);
                }
                for(Bullet bullet : enemyBullets){
                    bullet.render(g);
                }
                break;
        }
    }
}
