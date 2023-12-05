package happer;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/** 
 * @author Kay
 */
public class Box extends GameObject {
    private Image boxImage;
    private int boxNr;
    private String readBoxImage;

    /**
     * Makes a Box object with a Position object and a Gameworld integer.
     * 
     * @param position  The Position of the Box
     * @param gameWorld The integer wich is used to load the right Box image.
     */
    public Box(Position position, int gameWorld) {
        super(position);
        boxNr = (int) Math.ceil(Math.random() * 2);
        this.readBoxImage = "/images/boxes/box" + gameWorld + "_" + boxNr + ".gif";
        loadImage();
    }

    private void loadImage() {
        try {
            boxImage = ImageIO.read(getClass().getResource(readBoxImage));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fout bij laden box: " + ex.toString());
        }
    }

    public void draw(Graphics g, HapperPanel panel) {
        g.drawImage(boxImage, (int) getPosition().getPoint().getX() + 1, (int) getPosition().getPoint().getY() + 1,
                panel);
    }
}