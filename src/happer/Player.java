package happer;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Player        extends GameObject {
    private String readPlayerImage;
    private Image playerImage;

    public Player(Position position, int gameWorld, int playerNr) {
        super(position);
        this.readPlayerImage = "/images/players/player" + gameWorld + "_" + playerNr + ".gif";
        loadImage();
    }

    private void loadImage() {
        try {
            this.playerImage = ImageIO.read(getClass().getResource(this.readPlayerImage));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fout bij laden player: " + ex.toString());
        }
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public void draw(Graphics g, HapperPanel panel) {
        g.drawImage(this.playerImage, (int) getPosition().getPoint().getX(), (int) getPosition().getPoint().getY(),
                panel);
    }
}
