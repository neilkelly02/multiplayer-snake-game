package net;

import java.io.DataInputStream;
import java.io.IOException;

import game.Runner;

public class ClientReciever implements Runnable {

    DataInputStream in;
    int amountOfUsers;

    public ClientReciever(DataInputStream inIn) {
        this.in = inIn;
    }

    public void run() {
        assignId();
        checkForStart();
        try {
            setup();
        } catch (IOException e) {

        }
        update();
    }

    public void assignId() {
        try {
            Client.id = Integer.parseInt(in.readUTF());
        } catch (Exception e) {

        }
    }

    public void checkForStart() {
        while (true) {
            try {
                String message = in.readUTF();
                if (message.equals("start")) {
                    break;
                }
            } catch (Exception e) {

            }
        }
    }

    public void setup() throws IOException {
        amountOfUsers = in.readInt();
        game.Player[] players = new game.Player[amountOfUsers];
        for (int i = 0; i < amountOfUsers; i++) {
            players[i] = new game.Player(i);
            players[i].setDirection('w');
        }
        new game.Runner(players);
    }

    public void update() {
        while (true) {
            try {
                String json = in.readUTF();
                if (json.substring(2).equals("restart")) {
                    if (Runner.isHost == true) Server.refresh();
                    new game.Runner(1);
                }
                else {
                    int id = Integer.parseInt(json.substring(0, 1));
                    char direction = json.charAt(2);
                    if (game.Board.getLosers()[id] == false) game.Board.move(id, direction);
                    try {
                        game.Board.playSound("src/sound/tick.wav");
                    } catch (Exception e) {
                        
                    }
                    game.Runner.gPanel.repaint();
                }
            }
            catch (Exception e) {

            }
        }
    }

}