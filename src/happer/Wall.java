package happer;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Wall extends GameObject {
    private Image wallImage;
    private String readWallImage;

    public Wall(Position position, int gameWorld) {
        super(position);
        this.readWallImage = "/images/walls/wall" + gameWorld + ".gif";
        loadImage();
    }

    private void loadImage() {
        try {
            this.wallImage = ImageIO.read(getClass().getResource(this.readWallImage));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fout bij laden wall: " + ex.toString());
        }
    }

    public void draw(Graphics g, HapperPanel panel) {
        g.drawImage(this.wallImage, (int) getPosition().getPoint().getX() + 1,
                (int) getPosition().getPoint().getY() + 1, panel);
    }
}
