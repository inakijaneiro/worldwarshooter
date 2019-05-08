/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author inakijaneiro
 */
public class Game implements Runnable {

    // Constants
    public static final int PADDING_TOP = 10;
    public static final int PADDING_LEFT = 110;

    private BufferStrategy bs;              // to have several buffers when displaying
    private Graphics g;                     // to paint objects
    private Display display;                // to display in the game 
    private String title;                   // title of the window
    private int width;                      // width of the window
    private int height;                     // height of the window
    private Thread thread;                  // thread to create the game
    private boolean running;                // to set the game
    private boolean paused;                 // to store the pause flag
    private boolean gameEnded;              // to store if the game is over
    private int score;                      // to store the score
    private int lives;                      // to store lives;
    private int health;                     // to store health;
    private KeyManager keyManager;          // to manage the keyboard
    private Formatter file;                 // to store the saved game file.
    private Scanner scanner;                // to store the scanner to read a game file
    private boolean win;                    // when the player won the game
    private Level level;
    private float volume;
    private int levelNumber;

    
    /**
     * To create title,	width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.running = false;
        this.keyManager = new KeyManager();
        this.lives = 3;
        this.volume = -10;
        this.health = 3;
        this.levelNumber = 0;
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the KeyManager instance
     *
     * @return <code>KeyManager</code> keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }


    /**
     * Gets the score
     *
     * @return <code>int</code> score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets lives
     * 
     * @return <code>int</code> lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets lives
     * 
     * @param lives 
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
    
    /**
     * Gets the game pause status
     *
     * @return <code>boolean</code> paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the game pause status
     *
     * @param paused
     */
    public void setPause(boolean paused) {
        this.paused = paused;
    }
    /**
     * Gets the volume of the game
     * @return 
     */
    public float getVolume() {
        return volume;
    }
    /**
     * Sets the volume of the game
     * @param volume 
     */
    public void setVolume(float volume) {
        this.volume = volume;
        Assets.setVolume(volume);
    }
    /**
     * Method to change level
     * @param level 
     */
    public void changeLevel() {
        this.level = null;
        levelNumber += 1;
        switch (levelNumber) {
            case 1:
                this.level = new Level(Level.LevelName.Chapter1, this);
                break;
            case 2:
                this.level = new Level(Level.LevelName.Level1, this);
                Assets.setLevelBackground(1, 1);
                break;
            case 3:
                this.level = new Level(Level.LevelName.Chapter2, this);
                break;
            case 4:
                this.level = new Level(Level.LevelName.Level2, this);
                break;
        }
    }
    
    /**
     * Method restart to reset the game
     */
    private void restart() {
        setScore(0);
        setLives(3);
    }
    
    /**
     * Calls every save method from the classes and writes the score and lives
     * on a file
     */
    private void save(){
        
        // Generates a new game.txt
        try{
            file = new Formatter("game.txt");
        } catch (Exception e) {
            System.out.println("Hubo un problema con el guardado");
        }
        /*
        Se guardan todas las instancias llamando el metodo instancia.save();
        */
        
        file.close();
    }
    
    /**
     * Reads the saved-game file and uses the load methods of the classes to
     * restore the saved game
     */
    private void load() {
        try {
            int x, y, xspeed, yspeed, width, height;
            String type;
            scanner = new Scanner(new File("game.txt"));
            
            // Se cargan las instancias respetando el orden en que fuerno guardadas, verlo en save();
            
        } catch (Exception e) {
            System.out.println("Hubo un problema con  carga.");
        }

    }

    /**
     * Initialising the display window of the game
     */
    private void init() {
        display = new Display(title, width, height);
        display.getJframe().addKeyListener(keyManager);
        Assets.init();
        this.level = new Level(Level.LevelName.MainMenu, this);
        setScore(0);
        Assets.setVolume(-10);
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 60;
        // time for each tick in nano segs;
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanoseconds
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // accumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            //updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }


    /**
     * Ticks all the necessary instances
     */
    private void tick() {
        keyManager.tick();
        // Sets the game paused and unpaused
        if (getKeyManager().p && getKeyManager().isPressable() && level.level != Level.LevelName.MainMenu) {
            setPause(!isPaused());
            getKeyManager().setPressable(false);
        }
        
        // Saves the game
        if (getKeyManager().g) {
            save();
        }
        
        // Loads a game
        if (getKeyManager().c) {
            load();
        }
        
        // Restarts the game
        if (getKeyManager().r) {
            restart();
        }
        
        
        // As the game is not paused or ended everything is getting ticked
        if (!isPaused() && !gameEnded) {
            level.tick();
            // Game ends when the player runs out of lives
            if(lives == 0){
                gameEnded = true;
            }
        }
        
       if(isPaused()){
           if (getKeyManager().right && getKeyManager().isPressable()) {
                    if (getVolume() >= 0) {
                        setVolume(0);
                    } else {
                        setVolume(getVolume() + 2);
                    }
                    getKeyManager().setPressable(false);
                }

                if (getKeyManager().left && getKeyManager().isPressable()) {
                    if (getVolume() <= -20) {
                        setVolume(-20);
                    } else {
                        setVolume(getVolume() - 2);
                    }
                    getKeyManager().setPressable(false);
                }
       }
        
            
        // When the game ends, sets the flags to true or false, and waits for
        // the player to press space to restart
        if (gameEnded) {
            if (getKeyManager().space) {
                restart();
                getKeyManager().setPressable(false);
                gameEnded = false;
                win = false;
            }
        }
    }
    /**
     * Main render method of the game
     */
    private void render() {
        //get the buffer from the display
        bs = display.getCanvas().getBufferStrategy();
        /* 
         * If it is null, we define one with 3 buffers to display images of the game,
         * if not null, then we display every image of the game but after clearing the
         * Rectanlge, getting the graphic object from the buffer strategy element.	
         * Show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            // Draws background, score and limit
            level.render(g);
            
            if (isPaused()) {
                g.drawImage(Assets.pause, 0, 0, 1280, 720, null);
                g.drawImage(Assets.musicController, width / 2 - 200, 380, 400, 100, null);
                for (int i = 0; i < 10 - getVolume() / -2; i++) {
                    g.drawImage(Assets.volumePill, width / 2 - 159 + i * 32, 440, 30, 30, null);
                }
            }

            /* Mover a Level
            for (int i = 0; i < bullets.size(); i++) {
                bullets.get(i).render(g);
            }*/
            //If Level3 is added, needs to be added to the if
            if(level.level == Level.LevelName.Level1 || level.level == Level.LevelName.Level2){
               if(health==3){
                    g.drawImage(Assets.healthBar1, 20, 20, 160, 50, null);
               }
               else if(health==2){
                   g.drawImage(Assets.healthBar2, 20, 20, 160, 50, null);
               }
               else{
                   g.drawImage(Assets.healthBar3, 20, 20, 160, 50, null);
               }
            }
            bs.show();
            g.dispose();
        }
    }

/**
 * Setting the thread for the game
 */
public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
        }
        try {
            thread.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
