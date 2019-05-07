/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wwshooter;

import java.awt.image.BufferedImage;

/**
 *
 * @author inakijaneiro
 */
public class Animation {

    private int speed;      // for the speed of every frame
    private int index;      // for the index of the next frame to paint
    private long lastTime;  // to save the previous time of the animation
    private long timer;     // to acumulate the time of the animation
    private BufferedImage[] frames; // to store every image - frame
    private boolean start;
    public boolean ended;

    /**
     * Creating the animation with all the frames and the speed for each
     *
     * @param frames an <code> array </code> of images
     * @param speed an <code>int</code> value for the speed of every frame
     */
    public Animation(BufferedImage[] frames, int speed) {
        this.frames = frames;   // storing frames
        this.speed = speed;     // storing speed
        index = 0;              // initializing index
        timer = 0;              // initializing timer
        lastTime = System.currentTimeMillis(); // getting the initial time
    }

    /**
     * Gets the current frame at the time when is called
     *
     * @return <code>frames[index]</code>
     */
    public BufferedImage getCurrentFrame() {
        return this.frames[index];
    }

    /**
     * Sets the frames from Assets package
     * @param BufferedImage[]
     */
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
    }
    /**
     * Get's the index of the animation
     * @return <code>index</code>
     */
    public int getIndex(){
        return this.index;
    }
    
    /**
     * Sets the current index
     * @param int
     */
    public void setIndex(int index){
        this.index = index;
    }
    
    /**
     * Sets the speed of the animation
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
    /**
     * Ticker for the class
     */
    public void tick() {
        // acumulating time from the privious tick to this one
        timer += System.currentTimeMillis() - lastTime;
        // updating the lastTime for the next tick
        lastTime = System.currentTimeMillis();
        ended = false;
        // check the timer to increase the index
        if (timer > speed) {
            index++;
            timer = 0;
            // check index not to get out of bounds
            if (index >= frames.length) {
                index = 0;
                ended = true;
            }
        }

    }

}
