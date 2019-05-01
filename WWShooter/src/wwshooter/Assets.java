/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {

    public static BufferedImage background;     // to store background image
    public static BufferedImage continueButton; // to store the continue button
    public static BufferedImage newGameButton;  // to store the new game button
    public static BufferedImage settingsButton; // to store the settings button
    public static BufferedImage backButton;     // to store the back button
    public static BufferedImage selector;       // to store the settings button
    public static BufferedImage title;          // to store the title
    public static BufferedImage playerIdle[];
    public static BufferedImage playerIdleR[];
    public static BufferedImage playerRun[];
    public static BufferedImage playerRunR[];
    public static BufferedImage playerShoot[];
    public static BufferedImage playerShootR[];
    public static BufferedImage firstEnemyRun[];
    public static BufferedImage firstEnemyRunR[];
    public static BufferedImage bullet;
    public static BufferedImage level1Background;
    public static BufferedImage musicController;
    public static BufferedImage volumePill;
    public static BufferedImage lives;

    public static void setLevelBackground(int level, int stage) {
        level1Background = ImageLoader.loadImage("/LevelBackgrounds/Level" + level + "_" + stage + ".png");
    }

    /**
     * Initializes the images of the game
     */
    public static void init() {
        // Images
        background = ImageLoader.loadImage("/images/Background.png");
        continueButton = ImageLoader.loadImage("/images/Continue.png");
        newGameButton = ImageLoader.loadImage("/images/NewGame.png");
        settingsButton = ImageLoader.loadImage("/images/Settings.png");
        backButton = ImageLoader.loadImage("/images/Back.png");
        selector = ImageLoader.loadImage("/images/Selector.png");
        title = ImageLoader.loadImage("/images/Title.png");
        bullet = ImageLoader.loadImage("/images/bullet2.png");
        musicController = ImageLoader.loadImage("/images/MusicController.png");
        volumePill = ImageLoader.loadImage("/images/VolumePill.png");
        lives = ImageLoader.loadImage("/images/helmet.png");
        playerIdle = new BufferedImage[8];
        playerIdleR = new BufferedImage[8];
        playerRun = new BufferedImage[6];
        playerRunR = new BufferedImage[6];
        playerShoot = new BufferedImage[6];
        playerShootR = new BufferedImage[6];
        firstEnemyRun = new BufferedImage[6];
        firstEnemyRunR = new BufferedImage[6];

        for (int i = 0; i < playerIdle.length; i++) {
            String path = "/player_sprite/Idle" + i + ".png";
            playerIdle[i] = ImageLoader.loadImage(path);
            path = "/player_sprite/IdleR" + i + ".png";
            playerIdleR[i] = ImageLoader.loadImage(path);
        }

        for (int i = 0; i < 6; i++) {
            String path = "/player_sprite/Run" + i + ".png";
            playerRun[i] = ImageLoader.loadImage(path);
            path = "/player_sprite/RunR" + i + ".png";
            playerRunR[i] = ImageLoader.loadImage(path);
            path = "/player_sprite/Shoot" + i + ".png";
            playerShoot[i] = ImageLoader.loadImage(path);
            path = "/player_sprite/ShootR" + i + ".png";
            playerShootR[i] = ImageLoader.loadImage(path);
            
            // Enemy
            path = "/enemy_sprite/Terrorist1Run" + i + ".png";
            firstEnemyRun[i] = ImageLoader.loadImage(path);
            path = "/enemy_sprite/Terrorist1RunR" + i + ".png";
            firstEnemyRun[i] = ImageLoader.loadImage(path);
        }

    }
}
