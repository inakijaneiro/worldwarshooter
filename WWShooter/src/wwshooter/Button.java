/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.Graphics;

/**
 *
 * @author sergiodiosdado
 */
public class Button extends Item {
    
    private String type;
    private boolean visible;
    /**
     * Main constructor for the class Button
     * @param x
     * @param y
     * @param width
     * @param height
     * @param type 
     */
    public Button(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        this.type = type;
        this.visible = true;
    }
    /**
     * Set's the type of button
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Get's the type of button
     * @return 
     */
    public String getType() {
        return type;
    }
    /**
     * Checks if the button is visible
     * @return 
     */
    public boolean isVisible() {
        return visible;
    }
    /**
     * Show's or hides the button
     * @param visible 
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    /**
     * Main tick for the button
     */
    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Render method for Button
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        if (isVisible()) {
            switch (this.type) {
                case "newgame":
                    g.drawImage(Assets.newGameButton, getX(), getY(), getWidth(), getHeight(), null);
                    break;
                case "continue":
                    g.drawImage(Assets.continueButton, getX(), getY(), getWidth(), getHeight(), null);
                    break;
                case "settings":
                    g.drawImage(Assets.settingsButton, getX(), getY(), getWidth(), getHeight(), null);
                    break;
                case "back":
                    g.drawImage(Assets.backButton, getX(), getY(), getWidth(), getHeight(), null);
                    break;
            }
        }
    }
    
}
