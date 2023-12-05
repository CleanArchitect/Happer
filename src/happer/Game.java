package happer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.Timer;
import javax.swing.UIManager;

public class Game extends JFrame implements ActionListener {
    private int gameSize;
    private int gameWorld;
    private int gameType;
    private int nrOfPlayers;
    private int nrOfHappers;
    private Timer timer;
    private ClockDisplay minutes;
    private ClockDisplay seconds;
    private ClockDisplay miliSeconds;
    private String totalTime;
    private String highScore;
    private int highScoreMinutes;
    private int highScoreSeconds;
    private int highScoreMiliSeconds;
    private HapperPanel newGame;
    private JButton jBReset;

    public Game() {
        initLooks();
        initComponents();
        initButtonActions();
        initClock();
    }

    private JButton jBStart;
    private JButton jBStop;
    private JComboBox jCGameType;
    private JComboBox jCNrOfHappers;
    private JComboBox jCNrOfPlayers;
    private JComboBox jCWorldSize;
    private JComboBox jCWorldType;
    private JLabel jLGameType;
    private JLabel jLWorldSize;
    private JLabel jLWorldType;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPGamePanel;
    private JPanel jPGameWorld;
    private JPanel jPSettings;
    private JTextField jTClock;
    private JTextField jTHighScore;

    public void initLooks() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.toString();
        }
    }

    private void initComponents() {
        this.jPGamePanel = new JPanel();
        this.jPSettings = new JPanel();
        this.jLWorldSize = new JLabel();
        this.jLWorldType = new JLabel();
        this.jLGameType = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jBStart = new JButton();
        this.jBStop = new JButton();
        this.jBReset = new JButton();
        this.jCGameType = new JComboBox();
        this.jCWorldSize = new JComboBox();
        this.jCWorldType = new JComboBox();
        this.jCNrOfPlayers = new JComboBox();
        this.jCNrOfHappers = new JComboBox();
        this.jTClock = new JTextField();
        this.jTHighScore = new JTextField();
        this.jPGameWorld = new JPanel();

        setDefaultCloseOperation(3);
        setTitle("Happer");
        this.jPGamePanel.setBorder(BorderFactory.createEtchedBorder());
        this.jPSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Settings", 0, 0,
                new Font("Tahoma", 0, 11), new Color(0, 0, 0)));
        this.jLWorldSize.setText("World size:");

        this.jLWorldType.setText("World type:");

        this.jLGameType.setText("Game type:");

        this.jLabel1.setText("Number of players:");

        this.jLabel2.setText("Number of happers:");

        this.jBStart.setText("Start");
        this.jBStart.setPreferredSize(new Dimension(70, 23));

        this.jBStop.setText("Stop");
        this.jBStop.setPreferredSize(new Dimension(70, 23));

        this.jBReset.setText("Reset");
        this.jBReset.setPreferredSize(new Dimension(70, 23));

        this.jCGameType
                .setModel(new DefaultComboBoxModel<String>(new String[] { "Easy", "Normal", "Hard", "Hardcore" }));
        this.jCGameType.setSelectedIndex(1);

        this.jCWorldSize
                .setModel(new DefaultComboBoxModel<String>(new String[] { "Small", "Medium", "Large", "Extra large" }));
        this.jCWorldSize.setSelectedIndex(1);

        this.jCWorldType.setModel(new DefaultComboBoxModel<String>(
                new String[] { "Pokémon", "Farm", "Marioworld", "Iceland", "Bomberman", "Retro" }));

        this.jCNrOfPlayers.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4" }));

        this.jCNrOfHappers.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4" }));

        this.jTClock.setEditable(false);
        this.jTClock.setHorizontalAlignment(0);

        this.jTHighScore.setEditable(false);
        this.jTHighScore.setHorizontalAlignment(0);

        GroupLayout jPSettingsLayout = new GroupLayout(this.jPSettings);
        this.jPSettings.setLayout(jPSettingsLayout);
        jPSettingsLayout.setHorizontalGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPSettingsLayout.createSequentialGroup().addContainerGap()
                        .addGroup(jPSettingsLayout
                                .createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLWorldSize)
                                .addComponent(this.jLWorldType).addComponent(this.jLGameType))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(this.jCWorldSize, -2, -1, -2).addComponent(this.jCWorldType, -2, -1, -2)
                                .addComponent(this.jCGameType, -2, -1, -2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPSettingsLayout.createSequentialGroup()
                                        .addGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(this.jLabel2).addComponent(this.jLabel1))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(this.jCNrOfHappers, -2, -1, -2)
                                                .addComponent(this.jCNrOfPlayers, -2, -1, -2)))
                                .addGroup(
                                        jPSettingsLayout.createSequentialGroup().addComponent(this.jTClock, -2, 56, -2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
                                                .addComponent(this.jTHighScore, -2, 56, -2)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 31, 32767)
                        .addGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(this.jBStop, -2, -1, -2).addComponent(this.jBReset, -2, -1, -2)
                                .addComponent(this.jBStart, -2, -1, -2))
                        .addContainerGap()));

        jPSettingsLayout.linkSize(0, new Component[] { this.jCGameType, this.jCWorldSize, this.jCWorldType });

        jPSettingsLayout.setVerticalGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPSettingsLayout.createSequentialGroup().addGroup(jPSettingsLayout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPSettingsLayout.createSequentialGroup()
                                .addGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(this.jLWorldSize).addComponent(this.jCWorldSize, -2, -1, -2)
                                        .addComponent(this.jLabel1).addComponent(this.jCNrOfPlayers, -2, -1, -2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(this.jLWorldType).addComponent(this.jCWorldType, -2, -1, -2)
                                        .addComponent(this.jLabel2).addComponent(this.jCNrOfHappers, -2, -1, -2))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPSettingsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(this.jLGameType).addComponent(this.jCGameType, -2, -1, -2)
                                        .addComponent(this.jTClock, -2, -1, -2)
                                        .addComponent(this.jTHighScore, -2, -1, -2)))
                        .addGroup(jPSettingsLayout.createSequentialGroup().addComponent(this.jBStart, -2, -1, -2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(this.jBStop, -2, -1, -2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(this.jBReset, -2, -1, -2)))
                        .addContainerGap(14, 32767)));

        this.jPGameWorld.setLayout(new BorderLayout());

        this.jPGameWorld.setBackground(new Color(255, 255, 255));

        GroupLayout jPGamePanelLayout = new GroupLayout(this.jPGamePanel);
        this.jPGamePanel.setLayout(jPGamePanelLayout);
        jPGamePanelLayout.setHorizontalGroup(jPGamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPGamePanelLayout.createSequentialGroup().addContainerGap()
                        .addGroup(jPGamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(this.jPSettings, -1, -1, 32767)
                                .addComponent(this.jPGameWorld, -2, -1, -2))
                        .addContainerGap()));

        jPGamePanelLayout.setVerticalGroup(jPGamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPGamePanelLayout.createSequentialGroup().addContainerGap()
                        .addComponent(this.jPSettings, -2, -1, -2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(this.jPGameWorld, -2, -1, -2).addContainerGap(-1, 32767)));

        getContentPane().add(this.jPGamePanel, "Center");

        pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (this.newGame.isRunning()) {
            this.miliSeconds.increment();
            if (this.miliSeconds.getValue() == 0) {
                this.seconds.increment();
                if (this.seconds.getValue() == 0) {
                    this.minutes.increment();
                }
            }
            updateDisplay();
        } else {
            this.timer.stop();
            stopGame();
            if (this.newGame.won()) {
                setHighScore();
            }
        }
    }

    public void stopGame() {
        String end = "U have ";

        if (this.newGame.won()) {
            end = end + "WON ";
        } else {
            end = end + "LOST ";
        }

        JOptionPane.showMessageDialog(null, end + "the game!");
        this.newGame.setFocusable(false);
    }

    public void initButtonActions() {
        this.jBStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Game.this.newGame != null) {
                    Game.this.newGame.start();
                    Game.this.newGame.requestFocus();
                    Game.this.timer.start();
                } else {
                    Game.this.startGame();
                    Game.this.newGame.requestFocus();
                }
            }
        });
        this.jBStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.this.newGame.stop();
                Game.this.timer.stop();
            }
        });
        this.jBReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.this.startGame();
                Game.this.setTime(0, 0, 0);
            }
        });
    }

    public void startGame() {
        if (this.newGame != null) {
            this.newGame.stop();
        }
        this.jPGameWorld.removeAll();

        this.gameSize = this.jCWorldSize.getSelectedIndex();
        this.gameWorld = this.jCWorldType.getSelectedIndex();
        this.gameType = this.jCGameType.getSelectedIndex();

        this.nrOfPlayers = this.jCNrOfPlayers.getSelectedIndex();
        this.nrOfHappers = this.jCNrOfHappers.getSelectedIndex();

        this.newGame = new HapperPanel(this.gameSize, this.gameWorld, this.gameType, this.nrOfPlayers,
                this.nrOfHappers);

        this.jPGameWorld.add(this.newGame, "Center");
        this.jPGameWorld.repaint();

        pack();
    }

    public void initClock() {
        this.timer = new Timer(1, this);
        this.minutes = new ClockDisplay(60);
        this.seconds = new ClockDisplay(60);
        this.miliSeconds = new ClockDisplay(100);
        setTime(0, 0, 0);
        this.highScoreMinutes = 0;
        this.highScoreSeconds = 0;
        this.highScoreMiliSeconds = 0;
    }

    public void setTime(int minute, int second, int miliSecond) {
        this.minutes.setValue(minute);
        this.seconds.setValue(second);
        this.miliSeconds.setValue(miliSecond);
        updateDisplay();
    }

    public void updateDisplay() {
        this.totalTime = this.minutes.getDisplayValue() + ":" + this.seconds.getDisplayValue() + ":"
                + this.miliSeconds.getDisplayValue();
        this.jTClock.setText(this.totalTime);
    }

    public void setHighScore() {
        if ((this.minutes.getValue() <= this.highScoreMinutes || this.highScoreMinutes == 0)
                && (this.seconds.getValue() <= this.highScoreSeconds || this.highScoreSeconds == 0)
                && (this.miliSeconds.getValue() <= this.highScoreMiliSeconds || this.highScoreMiliSeconds == 0)) {
            this.highScoreMinutes = this.minutes.getValue();
            this.highScoreSeconds = this.seconds.getValue();
            this.highScoreMiliSeconds = this.miliSeconds.getValue();
            this.highScore = this.totalTime;
            this.jTHighScore.setText(this.highScore);
            String name = JOptionPane.showInputDialog("New highscore!\nGive your name");
            String type = this.jCGameType.getSelectedItem().toString().toLowerCase();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new Game()).setVisible(true);
            }
        });
    }
}
