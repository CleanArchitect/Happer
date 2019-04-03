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
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class is used to load and draw Background images for the specific Gameworlds.
 * It will first load an image with the Gameworld integer, wich is given. Then it will
 * draw itself with the method draw.
 * @author Nemesis
 */
public class Background extends JPanel{
    private Image backgroundImage;
    private String readBackgroundImage;
    private Position position;
    
    /**
     * Makes an Background object with a Position object and a Gameworld integer.
     * @param position The position where the Background will be drawn
     * @param gameWorld The integer wich is used to load the Background image
     */
    public Background(Position position, int gameWorld) {
        this.position = position;
        this.readBackgroundImage = "../images/backgrounds/background" + gameWorld + ".gif";
        loadImage();
    }
    
    private void loadImage() {
        try {
            File background = new File(readBackgroundImage);
            backgroundImage = ImageIO.read(background);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fout bij laden background: " + ex.toString());
        }
        
    }
    
    /**
     * Draws the loaded Backgroundimage
     * @param g The Graphics object
     */
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, (int)position.getPoint().getX()+1, (int)position.getPoint().getY()+1, this);
    }
}
