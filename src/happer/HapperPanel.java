package happer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HapperPanel extends JPanel implements KeyListener, ActionListener {
    private boolean isRunning;

    private boolean won;

    private int gameWidth;

    private int gameHeight;

    private int gameWorld;

    private int nrOfBoxes;

    private int nrOfWalls;

    private int nrOfBackground;

    private int totalPositions;

    private int totalPlayers;

    private int totalHappers;

    private Position[][] positions;
    private Background background;

    public HapperPanel(int gameSize, int gameWorld, int gameType, int nrOfPlayers, int nrOfHappers) {
        this.gameWidth = (gameSize + 3) * 5;
        this.gameHeight = (gameSize + 3) * 3;
        this.totalPositions = this.gameWidth * this.gameHeight;
        this.gameWorld = gameWorld + 1;
        this.totalPlayers = nrOfPlayers + 1;
        this.totalHappers = nrOfHappers + 1;
        this.nrOfBackground = this.totalPositions / 100 * 10;

        this.players = new ArrayList();
        this.happers = new ArrayList();
        this.backgrounds = new ArrayList();
        this.positions = new Position[this.gameWidth][this.gameHeight];
        this.happerTimer = new Timer(500, this);
        this.specialTimer = new Timer(1000, this);
        this.isRunning = false;
        this.won = false;

        setGameWorld(gameWorld);
        setGameType(gameType);

        makePositions();
        setNeighbors();
        makeGameObjects();

        this.action = new Action();
        this.action.makeActions(this.players);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(this.gameWidth * 25, this.gameHeight * 25));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        addKeyListener(this);
    }

    private ArrayList players;
    private ArrayList happers;
    private ArrayList backgrounds;
    private GameObject player;
    private GameObject box;
    private GameObject wall;
    private GameObject special;
    private Happer happer;
    private HashMap actions;
    private Action action;
    private Timer happerTimer;
    private Timer specialTimer;
    private int happerDelay;
    private int counter;

    public boolean isRunning() {
        return this.isRunning;
    }

    public boolean won() {
        return this.won;
    }

    public void start() {
        setFocusable(true);
        this.happerTimer.start();
        this.isRunning = true;
    }

    public void stop() {
        setFocusable(false);
        this.happerTimer.stop();
        this.isRunning = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawGameObjects(g);
        if (this.gameWorld == 6) {
            drawGrid(g);
        }
    }

    public void setGameWorld(int gameWorld) {
        switch (gameWorld) {
            case 0:
                setBackground(new Color(152, 200, 96));
                break;
            case 1:
                setBackground(new Color(138, 179, 55));
                break;
            case 2:
                setBackground(new Color(248, 208, 112));
                break;
            case 3:
                setBackground(new Color(200, 200, 248));
                break;
            case 4:
                setBackground(new Color(32, 156, 0));
                break;
            case 5:
                setBackground(Color.WHITE);
                break;
        }

    }

    public void setGameType(int gameType) {
        switch (gameType) {
            case 0:
                this.nrOfBoxes = this.totalPositions / 100 * 20;
                this.nrOfWalls = this.totalPositions / 100 * 4;
                this.happerTimer.setDelay(650);
                break;
            case 1:
                this.nrOfBoxes = this.totalPositions / 100 * 15;
                this.nrOfWalls = this.totalPositions / 100 * 5;
                this.happerTimer.setDelay(550);
                break;
            case 2:
                this.nrOfBoxes = this.totalPositions / 100 * 10;
                this.nrOfWalls = this.totalPositions / 100 * 6;
                this.happerTimer.setDelay(450);
                break;
            case 3:
                this.nrOfBoxes = this.totalPositions / 100 * 7;
                this.nrOfWalls = this.totalPositions / 100 * 7;
                this.happerTimer.setDelay(400);
                break;
        }

    }

    public void keyPressed(KeyEvent e) {
        String key = KeyEvent.getKeyText(e.getKeyCode());
        this.action.execute(key);
        checkSpecial();
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.happerTimer) &&
                this.happerTimer.isRunning()) {
            moveHapper();
            repaint();
            makeSpecial();
        }

        if (e.getSource().equals(this.specialTimer)) {
            this.counter++;
            if (this.counter == 6) {
                this.happerTimer.setDelay(this.happerDelay);
                this.specialTimer.stop();
            }
        }
    }

    public void moveHapper() {
        for (Iterator<Happer> it = this.happers.iterator(); it.hasNext();) {
            this.happer = it.next();
            if (this.happer.isActive()) {
                this.happer.move(this.players, this.happers);
                if (this.happer.lost()) {
                    this.happerTimer.stop();
                    this.won = true;
                    this.isRunning = false;
                    break;
                }
                if (this.happer.won()) {
                    this.happerTimer.stop();
                    this.isRunning = false;
                    break;
                }
            }
        }
    }

    public void checkSpecial() {
        for (Iterator<Player> it = this.players.iterator(); it.hasNext();) {
            this.player = it.next();
            if (this.player.isSpecial()) {
                this.player.setSpecial(false);
                if (!this.specialTimer.isRunning()) {
                    this.happerDelay = this.happerTimer.getDelay();
                    this.happerTimer.setDelay(1000);
                    this.specialTimer.start();
                    this.counter = 0;
                }
            }
        }
    }

    public void makeSpecial() {
        int randomNr = (int) Math.ceil(Math.random() * 35.0D);
        if (randomNr == 15)
            for (int i = 0; i < 1;) {
                int randomRow = (int) Math.ceil(Math.random() * (this.positions.length - 2));
                int randomColumn = (int) Math.ceil(Math.random() * ((this.positions[0]).length - 2));

                if (this.positions[randomRow][randomColumn].getGameObject() == null) {
                    this.special = new Special(this.positions[randomRow][randomColumn], this.gameWorld);
                    i++;
                }
            }
    }

    public void makeGameObjects() {
        int i;
        for (i = 0; i < this.totalPlayers; i++) {
            switch (i) {
                case 0:
                    this.player = new Player(this.positions[i + 1][i + 1], this.gameWorld, i + 1);
                    this.players.add(this.player);
                    break;
                case 1:
                    this.player = new Player(this.positions[this.gameWidth - 2][i], this.gameWorld, i + 1);
                    this.players.add(this.player);
                    break;
                case 2:
                    this.player = new Player(this.positions[i + 2][i + 1], this.gameWorld, i + 1);
                    this.players.add(this.player);
                    break;
                case 3:
                    this.player = new Player(this.positions[this.gameWidth - 5][i], this.gameWorld, i + 1);
                    this.players.add(this.player);
                    break;
            }

        }
        for (i = 0; i < this.totalHappers; i++) {
            switch (i) {
                case 0:
                    this.happer = new Happer(this.positions[this.gameWidth - 2][this.gameHeight - 2], this.gameWorld,
                            i + 1);
                    this.happers.add(this.happer);
                    break;
                case 1:
                    this.happer = new Happer(this.positions[i][this.gameHeight - 2], this.gameWorld, i + 1);
                    this.happers.add(this.happer);
                    break;
                case 2:
                    this.happer = new Happer(this.positions[i + 4][this.gameHeight - 4], this.gameWorld, i + 1);
                    this.happers.add(this.happer);
                    break;
                case 3:
                    this.happer = new Happer(this.positions[this.gameWidth - 7][this.gameHeight - 4], this.gameWorld,
                            i + 1);
                    this.happers.add(this.happer);
                    break;
            }

        }
        for (i = 0; i < this.nrOfBoxes;) {
            int randomRow = (int) Math.ceil(Math.random() * (this.positions.length - 2));
            int randomColumn = (int) Math.ceil(Math.random() * ((this.positions[0]).length - 2));

            if (this.positions[randomRow][randomColumn].getGameObject() == null) {
                this.box = new Box(this.positions[randomRow][randomColumn], this.gameWorld);
                i++;
            }
        }

        for (i = 0; i < this.nrOfWalls;) {
            int randomRow = (int) Math.ceil(Math.random() * (this.positions.length - 2));
            int randomColumn = (int) Math.ceil(Math.random() * ((this.positions[0]).length - 2));

            if (this.positions[randomRow][randomColumn].getGameObject() == null) {
                this.wall = new Wall(this.positions[randomRow][randomColumn], this.gameWorld);
                i++;
            }
        }

        for (i = 0; i < this.nrOfBackground;) {
            int randomRow = (int) Math.ceil(Math.random() * (this.positions.length - 2));
            int randomColumn = (int) Math.ceil(Math.random() * ((this.positions[0]).length - 2));
            this.background = new Background(this.positions[randomRow][randomColumn], this.gameWorld);
            this.backgrounds.add(this.background);
            i++;
        }
    }

    public void makePositions() {
        for (int row = 0; row < this.gameWidth; row++) {
            for (int column = 0; column < this.gameHeight; column++) {
                this.positions[row][column] = new Position(row * 25, column * 25);
            }
        }
    }

    public void setNeighbors() {
        for (int row = 0; row < this.positions.length; row++) {
            for (int column = 0; column < (this.positions[row]).length; column++) {
                Position position = this.positions[row][column];
                if (row != 0) {
                    position.setNeighbor("Left", this.positions[row - 1][column]);
                }
                if (row != this.positions.length - 1) {
                    position.setNeighbor("Right", this.positions[row + 1][column]);
                }
                if (column != 0) {
                    position.setNeighbor("Up", this.positions[row][column - 1]);
                }
                if (column != (this.positions[row]).length - 1) {
                    position.setNeighbor("Down", this.positions[row][column + 1]);
                }
            }
        }
    }

    public void drawGrid(Graphics g) {
        for (int row = 0; row <= getHeight(); row++) {
            g.drawLine(row * 25, 0, row * 25, getHeight());
        }
        for (int column = 0; column <= getWidth(); column++) {
            g.drawLine(0, column * 25, getWidth(), column * 25);
        }
    }

    public void drawGameObjects(Graphics g) {
        for (int row = 0; row < this.positions.length; row++) {
            for (int column = 0; column < (this.positions[row]).length; column++) {
                GameObject gameObject = this.positions[row][column].getGameObject();
                if (gameObject != null) {
                    gameObject.draw(g, this);
                }
            }
        }
    }

    public void drawBackground(Graphics g) {
        for (Iterator<Background> it = this.backgrounds.iterator(); it.hasNext();) {
            this.background = it.next();
            this.background.draw(g);
        }
    }
}
