package happer;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Special extends GameObject {
    private Image specialImage;
    private String readSpecialImage;

    public Special(Position position, int gameWorld) {
        super(position);
        this.readSpecialImage = "/images/specials/special" + gameWorld + ".gif";
        loadImage();
    }

    private void loadImage() {
        try {
            this.specialImage = ImageIO.read(getClass().getResource(this.readSpecialImage));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fout bij laden special: " + ex.toString());
        }
    }

    public void draw(Graphics g, HapperPanel panel) {
        g.drawImage(this.specialImage, (int) getPosition().getPoint().getX(), (int) getPosition().getPoint().getY(),
                panel);
    }
}
