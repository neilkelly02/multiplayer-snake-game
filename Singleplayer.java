package game;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Singleplayer {

    public static int tickSpeed = 200;
    public static char direction = 'w';
    public static int totalPlayers;

    public Singleplayer(int totalPlayersIn) {
        totalPlayers = totalPlayersIn;
        gameTick.start();
    }

    public static Timer gameTick = new Timer(tickSpeed, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < totalPlayers; i++) {
                try {
                    if (Board.getLosers()[i] == false) {
                        if (i == 0) Board.move(i, direction);
                        else Board.move(i, SingleplayerAI.zackAlg2(i, Board.getPlayers()[i].getLocationX(), Board.getPlayers()[i].getLocationY(), '0', '0', 2).getValue());
                    }
                } catch (Exception e1) {

                }
            }
            if (Board.terminate() == true) {
                gameTick.stop();
                new Runner(1);
            }
            try {
                Board.playSound("src/sound/tick.wav");
            } catch (Exception e1) {

            }
            Runner.gPanel.repaint();
        }

    });

}