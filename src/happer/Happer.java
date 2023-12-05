package happer;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Happer extends GameObject {
    private AStar happerAI;
    private Image happerImage;
    private String readHapperImage;

    public Happer(Position position, int gameWorld, int happerNr) {
        super(position);
        this.readHapperImage = "/images/happers/happer" + gameWorld + "_" + happerNr + ".gif";
        loadImage();
        this.happerAI = new AStar();
    }

    private void loadImage() {
        try {
            this.happerImage = ImageIO.read(getClass().getResource(this.readHapperImage));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fout bij laden happer: " + ex.toString());
        }
    }

    public boolean lost() {
        if (this.happerAI.happerLost()) {
            return true;
        }
        return false;
    }

    public boolean won() {
        if (this.happerAI.playerDead()) {
            return true;
        }
        return false;
    }

    public void move(ArrayList players, ArrayList happers) {
        this.happerAI.move(getPosition(), players, happers);
    }

    public void draw(Graphics g, HapperPanel panel) {
        g.drawImage(this.happerImage, (int) getPosition().getPoint().getX(), (int) getPosition().getPoint().getY(),
                panel);
    }
}
