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

    public Button(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        this.type = type;
    }
    
    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics g) {
        switch(this.type){
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
