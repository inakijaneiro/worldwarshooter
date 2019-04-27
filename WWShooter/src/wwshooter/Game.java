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
    private boolean sounded;
    private int score;                      // to store the score
    private int lives;                      // to store lives;
    private Player player;                  // to store the player
    private KeyManager keyManager;          // to manage the keyboard
    private Formatter file;                 // to store the saved game file.
    private Scanner scanner;                // to store the scanner to read a game file
    private boolean moveDown;               // flag to move all instances of aliens down
    private int alienMoveCounter;           // to move the aliens on a certain time
    private int alienTickLimit;             // the limit in which the movement is done
    private int alienBombCounter;           // to spawn an alien bomb on a certain time
    private boolean win;                    // when the player won the game
    private Selector selector;
    
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
        this.alienTickLimit = 60;
        this.lives = 3;
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
     * Gets if a sound was already played
     * 
     * @return <code>boolean</code> sounded
     */
    public boolean isSounded(){
        return sounded;
    }
    
    /**
     * Sets sounded flag
     * 
     * @param sounded 
     */
    public void setSounded(boolean sounded){
        this.sounded = sounded;
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
     * Sets the player instance
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets moveDown value
     *
     * @return <boolean>moveDown</boolean>
     */
    public boolean moveDown() {
        return moveDown;
    }

    /**
     * Sets moveDown value
     *
     * @param moveDown
     */
    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    /**
     * Gets the alienMoveCounter value
     *
     * @return <code>int</code> alienMoveCounter
     */
    public int getAlienMoveCounter() {
        return alienMoveCounter;
    }

    /**
     * Sets alienMoveCounter value
     *
     * @param alienMoveCounter
     */
    public void setAlienMoveCounter(int alienMoveCounter) {
        this.alienMoveCounter = alienMoveCounter;
    }

    /**
     * Gets the alien tick limit
     * @return <code>int</code> alienTickLimit
     */
    public int getAlienTickLimit() {
        return alienTickLimit;
    }

    /**
     * Sets the alien tick limit
     * @param alienTickLimit 
     */
    public void setAlienTickLimit(int alienTickLimit) {
        this.alienTickLimit = alienTickLimit;
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
     * Gets the alien bomb counter
     * 
     * @return <code>int</code> alienBombCounter
     */
    public int getAlienBombCounter() {
        return alienBombCounter;
    }

    /**
     * Sets the alien bomb counter
     * 
     * @param alienBombCounter 
     */
    public void setAlienBombCounter(int alienBombCounter) {
        this.alienBombCounter = alienBombCounter;
    }
    
    // Restarts the game to the original state
    private void restart() {
        setScore(0);
        setLives(3);
        setAlienTickLimit(60);
        // Positions player
        player = new Player(getWidth() / 2 - 24, getHeight() - 64, 48, 48, 5, this);

        setScore(0);
        sounded = false;
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
        selector = new Selector(getWidth()/3, getHeight()/3, 50,50, 0, this);
        setScore(0);

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
        selector.tick();
        // Sets the game paused and unpaused
        if (getKeyManager().p && getKeyManager().isPressable()) {
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
            g.drawImage(Assets.background, 0, 0, width, height, null);
            g.drawImage(Assets.title, width/2 - width/12 - 80, height/2 - 300, width/3, height/6, null);
            g.drawImage(Assets.newGameButton, width/2 - width/12, height/2 - height/6, width/6, height/12, null);
            g.drawImage(Assets.continueButton, width/2 - width/12 , height/2 - height/6 + 100, width/6, height/12, null);
            g.drawImage(Assets.settingsButton, width/2 - width/12 , height/2 - height/6 + 200, width/6, height/12, null);
            selector.render(g);
            
            
            // Draws the player
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
