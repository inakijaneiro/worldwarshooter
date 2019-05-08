/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Formatter;
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
    private LinkedList<Bullet> rockets;
    private ArrayList<Enemy> enemies;
    private ArrayList<RocketLauncher> rocketLaunchers;
    private Animation arrowAnimation;
    private Animation saving;
    private Tank boss;

    enum LevelName {
        MainMenu, Level1, Level2, Level3, Chapter1, Chapter2, Chapter3
    }
    LevelName level;

    /**
     * Main constructor of the level
     *
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
        this.rocketLaunchers = new ArrayList<RocketLauncher>();
        this.rockets = new LinkedList<Bullet>();
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
            case Level2:
            case Level3:
                Assets.menuMusic.stop();
                Assets.backgroundMusic.setLooping(true);
                Assets.backgroundMusic.play();
                this.player = new Player(0, height - 350, 150, 350, 5, this);
                for (int i = 1; i <= 5; i++) {
                    enemies.add(new Enemy(width + 300 * i, height - 350, 150, 350, this, 'l'));
                }
                this.rocketLaunchers.add(new RocketLauncher(width + 300, height - 350, 150, 350, this, 'l'));
                this.rocketLaunchers.add(new RocketLauncher(-300, height - 350, 150, 350, this, 'r'));
                this.stage = 1;
                if(level == Level.LevelName.Level3){
                    this.boss = new Tank(800, 150, 500, 800, this);
                }
                break;

        }
        this.settingsMenu = false;
        this.arrowAnimation = new Animation(Assets.nextArrow, 90);
        this.saving = new Animation(Assets.saving, 240);
        saving.setTimeToAnimate(120);
        saving.setCurrTimer(120);
    }
    
    Level(int levelNumber, int stage, LevelName levelName, Game game) {
        this.game = game;
        this.level = levelName;
        this.width = game.getWidth();
        this.height = game.getHeight();
        this.buttons = new ArrayList<Button>();
        this.bullets = new LinkedList<Bullet>();
        this.enemyBullets = new LinkedList<Bullet>();
        this.enemies = new ArrayList<Enemy>();
        this.rocketLaunchers = new ArrayList<RocketLauncher>();
        this.rockets = new LinkedList<Bullet>();
        
        setStage(stage);
        
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
            case Level2:
            case Level3:
                Assets.menuMusic.stop();
                Assets.backgroundMusic.setLooping(true);
                Assets.backgroundMusic.play();
                this.player = new Player(0, height - 350, 150, 350, 5, this);
                break;
        }
        this.settingsMenu = false;
        this.arrowAnimation = new Animation(Assets.nextArrow, 90);
    }

    public Tank getBoss() {
        return boss;
    }
    
    /**
     * Get game method
     *
     * @return <code> Game </code> game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Method to get the bullets of the level
     *
     * @return <code>LikedList</code> bullets
     */
    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Method to get the rockets of the level
     *
     * @return <code>LikedList</code> bullets
     */
    public LinkedList<Bullet> getRockets() {
        return rockets;
    }

    /**
     * Method to get the enemy bullets
     *
     * @return <code>LinkedList</code> enemyBullets
     */
    public LinkedList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    /**
     * Boolean to check if the player is on the settings menu
     *
     * @return
     */
    public boolean isSettingsMenu() {
        return settingsMenu;
    }

    /**
     * Access to the Linked List of the enemies
     *
     * @return
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Set the settingsMenu boolean
     *
     * @param settingsMenu
     */
    public void setSettingsMenu(boolean settingsMenu) {
        this.settingsMenu = settingsMenu;
    }

    /**
     * Get the buttons in the main menu
     *
     * @return
     */
    public ArrayList<Button> getButtons() {
        return buttons;
    }

    /**
     * Method to get the current stage
     *
     * @return
     */
    public int getStage() {
        return stage;
    }

    /**
     * Method to get the Key Manager
     *
     * @return
     */
    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    /**
     * Method to set the buttons Linked List
     *
     * @param buttons
     */
    public void setButtons(ArrayList<Button> buttons) {
        this.buttons = buttons;
    }

    /**
     * Method to get the selector
     *
     * @return
     */
    public Selector getSelector() {
        return selector;
    }

    public ArrayList<RocketLauncher> getRocketLaunchers() {
        return rocketLaunchers;
    }

    public void setRocketLaunchers(ArrayList<RocketLauncher> rocketLaunchers) {
        this.rocketLaunchers = rocketLaunchers;
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
     *
     * @param stage
     */
    public void setStage(int stage) {
        this.stage = stage;
    }
    
    /**
     * Writes it's data in the saving file
     *
     * @param file
     */
    public void save(Formatter file) {
        file.format("%s%s", getGame().levelNumber + " ", getStage() + " ");
    }

    /**
     * Loads it's necessary data from a file
     *
     * @param x
     * @param y
     */
    public void load(int x, int y) {
      
    }
    

    /**
     * Main tick method of the level
     */
    public void tick() {
        switch (level) {
            case MainMenu:
                selector.tick();
                if (isSettingsMenu() && getKeyManager().right && getKeyManager().isPressable()) {
                    if (getGame().getVolume() >= 0) {
                        getGame().setVolume(0);
                    } else {
                        getGame().setVolume(getGame().getVolume() + 2);
                    }
                    getKeyManager().setPressable(false);
                }

                if (isSettingsMenu() && getKeyManager().left && getKeyManager().isPressable()) {
                    if (getGame().getVolume() <= -20) {
                        getGame().setVolume(-20);
                    } else {
                        getGame().setVolume(getGame().getVolume() - 2);
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
            case Level2:
            case Level3:
                player.tick();
                if (getGame().getHealth() <= 0) {
                    getGame().setLives(getGame().getLives() - 1);
                    getGame().setHealth(3);
                }
                saving.tick();
                for (int i = 0; i < bullets.size(); i++) {
                    Bullet bullet = bullets.get(i);
                    //Move bullets
                    bullet.tick();
                    if (bullet.getX() + bullet.getWidth() >= getGame().getWidth() || bullet.getX() <= 0) {
                        bullets.remove(i);
                        break;
                    }
                    for (int j = 0; j < enemies.size(); j++) {
                        if (bullet.intersecta(enemies.get(j))) {
                            Assets.enemyHurt.play();
                            enemies.remove(j);
                            bullets.remove(i);
                            break;
                        }
                    }
                    for (int j = 0; j < rocketLaunchers.size(); j++) {
                        if (bullet.intersecta(rocketLaunchers.get(j))) {
                            Assets.enemyHurt.play();
                            rocketLaunchers.remove(j);
                            bullets.remove(i);
                        }
                    }
                }
                for (int i = 0; i < rockets.size(); i++) {
                    Bullet rocket = rockets.get(i);
                    rocket.tick();
                    if (rocket.getX() + rocket.getWidth() >= getGame().getWidth() || rocket.getX() <= 0) {
                        rockets.remove(i);
                        break;
                    }
                }

                //Collision of enemy bullets with player
                int target = player.getX() + player.getWidth() / 2;
                for (int i = 0; i < enemyBullets.size(); i++) {
                    Bullet bulletEnemy = enemyBullets.get(i);
                    if (bulletEnemy.intersecta(player) && !getKeyManager().down) {
                        if (bulletEnemy.getX() <= target && bulletEnemy.getX() >= target - 10) {
                            getGame().setHealth(getGame().getHealth() - 1);
                            enemyBullets.remove(i);
                        }
                    }
                }
                // Collision of rockets with player
                for (int i = 0; i < rockets.size(); i++) {
                    Bullet rocket = rockets.get(i);
                    if (rocket.intersecta(player) && !getKeyManager().down) {
                        if (rocket.getX() <= target && rocket.getX() >= target - 10) {
                            getGame().setHealth(getGame().getHealth() - 2);
                            rockets.remove(i);
                        }
                    }
                }
                for (Bullet bullet : enemyBullets) {
                    bullet.tick();
                }
                for (Enemy enemy : enemies) {
                    enemy.tick();
                }
                for (RocketLauncher rocketLauncher : rocketLaunchers) {
                    rocketLauncher.tick();
                }
                if(level == Level.LevelName.Level3 && stage == 3 && boss.getHealth() > 0){
                    boss.tick();
                }
                break;
//            case Level3:
//                player.tick();
//                
//                break;
            case Chapter1:
            case Chapter2:
            case Chapter3:
                if ((getKeyManager().space || getKeyManager().enter) && getKeyManager().isPressable()) {
                    getKeyManager().setPressable(false);
                    getGame().changeLevel();
                }
                break;
        }
        if (level != Level.LevelName.MainMenu) {
            arrowAnimation.tick();
        }
    }

    /**
     * Render method for the level
     *
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
                    for (int i = 0; i < 10 - getGame().getVolume() / -2; i++) {
                        g.drawImage(Assets.volumePill, width / 2 - 159 + i * 32, 440, 30, 30, null);
                    }
                }
                break;
            case Level1:
            case Level2:
            case Level3:
                g.drawImage(Assets.level1Background, 0, 0, width, height, null);
                if (enemies.isEmpty() && rocketLaunchers.isEmpty()) {
                    if(level == Level.LevelName.Level3 && stage == 3){
                        if(boss.getHealth() <= 0){
                            g.drawImage(arrowAnimation.getCurrentFrame(), 1000, 300, 200, 200, null);
                        }
                    }else{
                        g.drawImage(arrowAnimation.getCurrentFrame(), 1000, 300, 200, 200, null);
                    }
                }
                player.render(g);
                for (int i = 0; i < bullets.size(); i++) {
                    //Render bullets
                    bullets.get(i).render(g);
                }
                for (Enemy enemy : enemies) {
                    enemy.render(g);
                }
                for (Bullet bullet : enemyBullets) {
                    bullet.render(g);
                }
                for (RocketLauncher rocketLauncher : rocketLaunchers) {
                    rocketLauncher.render(g);
                }
                for (Bullet rocket : rockets) {
                    rocket.render(g);
                }
                if(getKeyManager().g){
                    saving.setCurrTimer(0);
                }
                if(saving.getCurrTimer() < saving.getTimeToAnimate()){
                    g.drawImage(saving.getCurrentFrame(), 1100, 600, 150, 75, null);
                    saving.setCurrTimer(saving.getCurrTimer() + 1);
                }
                if(level == Level.LevelName.Level3 && stage == 3){
                    if(boss.getHealth() > 0){
                        boss.render(g);
                    }
                }
                break;
//            case Level3:
//                g.drawImage(Assets.level1Background, 0, 0, width, height, null);
//                player.render(g);
//                if (enemies.isEmpty()) {
//                    g.drawImage(arrowAnimation.getCurrentFrame(), 1000, 300, 200, 200, null);
//                }
//                break;
            case Chapter1:
                g.drawImage(Assets.chapter1, 0, 0, width, height, null);
                break;
            case Chapter2:
                g.drawImage(Assets.chapter2, 0, 0, width, height, null);
                break;
            case Chapter3:
                g.drawImage(Assets.chapter3, 0, 0, width, height, null);
                break;

        }
    }
}
