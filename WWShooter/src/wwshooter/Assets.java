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
        
    }
}
