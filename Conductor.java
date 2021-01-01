package net;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;

public class Conductor implements Runnable {

    public static int tickSpeed = 200;

    @Override
    public void run() {
        gameTick.start();
    }

    public Timer gameTick = new Timer(tickSpeed, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < Server.users.size(); i++) {
                Server.directions[i] = Server.users.get(i).direction;
            }
            for (int i = 0; i < Server.users.size(); i++) {
                for (int j = 0; j < Server.users.size(); j++) {
                    try {
                        Server.users.get(j).serverOut.writeUTF(i + " " + Server.directions[i]);
                    } catch (Exception e1) {

                    }
                }
            }
            if (game.Board.terminate() == true) {
                for (int i = 0; i < Server.users.size(); i++) {
                    try {
                        Server.users.get(i).serverOut.writeUTF(i + " restart");
                    } catch (IOException e1) {

                    }
                }
                gameTick.stop();
            }
        }

    });

}
