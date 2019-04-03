/*
 * Mens.java
 *
 * Created on 22 mei 2007, 18:23
 */

package happer;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class is used to create Box object, load and draw images for the specific Gameworlds.
 * It will first load an image with the Gameworld integer, wich is given. Then it will
 * draw itself with the method draw.
 * @author Nemesis
 */
public class Box extends GameObject{
    private Image boxImage;
    private int boxNr;
    private String readBoxImage;
    
    /**
     * Makes a Box object with a Position object and a Gameworld integer.
     * @param position The Position of the Box
     * @param gameWorld The integer wich is used to load the right Box image.
     */
    public Box(Position position, int gameWorld) {
        super(position);
        boxNr = (int)Math.ceil(Math.random()*2);
        this.readBoxImage = "../images/boxes/box" + gameWorld + "_" + boxNr + ".gif";       
        loadImage();
    }
        
    private void loadImage() {
        try {
            File box = new File(readBoxImage);
            boxImage = ImageIO.read(box);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fout bij laden box: " + ex.toString());
        }
    }    
    
    /**
     * Draws the Boximage on the HapperPanel.
     * @param g The Graphics Object.
     * @param panel The HapperPanel to draw on.
     */
    public void draw(Graphics g, HapperPanel panel) {
        g.drawImage(boxImage, (int)getPosition().getPoint().getX()+1, (int)getPosition().getPoint().getY()+1, panel);   
    }
}
