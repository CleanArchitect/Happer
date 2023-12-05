package happer;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Background extends JPanel {
    private Image backgroundImage;
    private String readBackgroundImage;
    private Position position;

    public Background(Position position, int gameWorld) {
        this.position = position;
        this.readBackgroundImage = "/images/backgrounds/background" + gameWorld + ".gif";
        loadImage();
    }

    private void loadImage() {
        try {
            this.backgroundImage = ImageIO.read(getClass().getResource(this.readBackgroundImage));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fout bij laden background: " + ex.toString());
        }
    }

    public void draw(Graphics g) {
        g.drawImage(this.backgroundImage, (int) this.position.getPoint().getX() + 1,
                (int) this.position.getPoint().getY() + 1, this);
    }
}