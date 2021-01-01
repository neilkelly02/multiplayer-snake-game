package game;

import java.awt.Color;

public class Player {

    private int number;
    private char direction;
    private int locationX;
    private int locationY;
    private Color color;

    public Player(int id) {
        this.number = id+1;
        if (this.number == 1) this.setColor(new Color(0, 255, 153));
        else if (this.number == 2) this.setColor(new Color(255, 102, 255));
        else if (this.number == 3) this.setColor(new Color(0, 153, 255));
        else if (this.number == 4) this.setColor(new Color(255, 255, 102));
    }

    public static void startingPositions(Player[] players) {
        double[] theta = new double[4];
        if (players.length == 2) {
            theta[0] = Math.PI;
            theta[1] = 0;
        }
        else if (players.length == 3) {
            theta[0] = (Math.PI / 2);
            theta[1] = (7 * (Math.PI / 6));
            theta[2] = (11 * (Math.PI / 6));
        }
        else if (players.length == 4) {
            theta[0] = (Math.PI / 2);
            theta[1] = Math.PI;
            theta[2] = 3 * (Math.PI / 2);
            theta[3] = 0;
        }
        for (int i = 0; i < players.length; i++) {
            double t = theta[i];
            players[i].setLocationX((int)((0.25 * Runner.GRID_SIZE)*Math.cos(t)) + (int)(0.5 * Runner.GRID_SIZE));
            players[i].setLocationY((int)((0.25 * Runner.GRID_SIZE)*Math.sin(t)) + (int)(0.5 * Runner.GRID_SIZE));
        }
    }

    public int getNumber() {
        return this.number;
    }

    public char getDirection() {
        return this.direction;
    }

    public void setDirection(char directionIn) {
        this.direction = directionIn;
    }

    public static boolean invalidDirection(char directionIn, char directionIn2) {
        if (directionIn == 'w' && directionIn2 == 's') {
            return true;
        }
        else if (directionIn == 'a' && directionIn2 == 'd') {
            return true;
        }
        else if (directionIn == 's' && directionIn2 == 'w') {
            return true;
        }
        else if (directionIn == 'd' && directionIn2 == 'a') {
            return true;
        }
        else {
            return false;
        }
    }

    public int getLocationX() {
        return this.locationX;
    }

    public int getLocationY() {
        return this.locationY;
    }

    public void setLocationX(int locationXIn) {
        this.locationX = locationXIn;
    }

    public void setLocationY(int locationYIn) {
        this.locationY = locationYIn;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color colorIn) {
        this.color = colorIn;
    }

    public String toString() {
        return "" + number + "\n" + direction + "\n" + locationX + "\n" + locationY + "\n" + color;
    }
    
}
