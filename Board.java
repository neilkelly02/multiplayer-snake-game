package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.awt.Color;

public class Board extends JPanel {

    private static final long serialVersionUID = 1L;

    private static int[][] grid;
    private static final int BOX_SIZE = Runner.FRAME_SIZE / Runner.GRID_SIZE;
    private static Player[] players;
    private static Color[] playerColors;
    private static boolean[] losers;

    public Board(Player[] playersIn) {
        grid = new int[Runner.GRID_SIZE][Runner.GRID_SIZE];
        players = playersIn;
        Player.startingPositions(players);
        playerColors = new Color[players.length];
        losers = new boolean[players.length];
        for (int j = 0; j < Runner.GRID_SIZE; j++) {
            grid[j][0] = -1;
            grid[j][Runner.GRID_SIZE - 1] = -1;
            grid[0][j] = -1;
            grid[Runner.GRID_SIZE - 1][j] = -1;
        }
        for (int i = 0; i < players.length; i++) {
            grid[players[i].getLocationX()][players[i].getLocationY()] = i+1;
        }
        copyColors();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        for (int i = 0; i < Runner.GRID_SIZE; i++) {
            for (int j = 0; j < Runner.GRID_SIZE; j++) {
                if (grid[i][j] == -1) {
                    //g2D.setColor(new Color(0, 0, 204));
                    g2D.setColor(new Color(0, 0, 102));
                    g2D.fillRect(i * BOX_SIZE, j * BOX_SIZE, BOX_SIZE, BOX_SIZE);
                } 
                else if (grid[i][j] != 0) {
                    g2D.setColor(playerColors[grid[i][j] - 1]);
                    g2D.fillRect(i * BOX_SIZE, j * BOX_SIZE, BOX_SIZE, BOX_SIZE);
                }
            }
        }
    }

    public static void move(int id, char direction) throws Exception {

        Player p = players[id];
        int px = p.getLocationX();
        int py = p.getLocationY();
        int n = p.getNumber();
        if (Player.invalidDirection(direction, p.getDirection())) {
            direction = p.getDirection();
        }
        p.setDirection(direction);
        if (direction == 'w') {
            grid[px][py] = n;
            p.setLocationY(py - 1);
            losers[id] = checkLoss(px, py - 1);
            grid[px][py - 1] = n;
        } else if (direction == 'a') {
            grid[px][py] = n;
            p.setLocationX(px - 1);
            losers[id] = checkLoss(px - 1, py);
            grid[px - 1][py] = n;
        } else if (direction == 's') {
            grid[px][py] = n;
            p.setLocationY(py + 1);
            losers[id] = checkLoss(px, py + 1);
            grid[px][py + 1] = n;
        } else if (direction == 'd') {
            grid[px][py] = n;
            p.setLocationX(px + 1);
            losers[id] = checkLoss(px + 1, py);
            grid[px + 1][py] = n;
        }

    }

    public static boolean checkLoss(int x, int y) throws Exception {
        if (grid[x][y] != 0) {
            playSound("src/sound/collision.wav");
            return true;
        }
        return false;
    }

    public static boolean terminate() {
        int losersCount = 0;
        for (int i = 0; i < losers.length; i++) {
            if (losers[i] == true) {
                losersCount++;
            }
        }
        if (losersCount == losers.length - 1) return true;
        return false;
    }

    public static boolean[] getLosers() {
        return losers;
    }

    public static int[][] getGrid() {
        return grid;
    }

    public static Player[] getPlayers() {
        return players;
    }

    public static void copyColors() {
        for (int i = 0; i < players.length; i++) {
            playerColors[i] = players[i].getColor();
        }
    }

    public static void playSound(String soundFile) throws Exception {
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
    
}
