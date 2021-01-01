package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Runner {

    public static final int FRAME_SIZE = 720;
    public static final int GRID_SIZE = 45;
    public static final int BAR_SIZE = 22;
    public static JFrame gFrame;
    public static JPanel gPanel;
    // Replay Mode Variables
    public static boolean multiplayer = true;
    public static boolean isHost = true;
    public static String hostsIP;
    public static int aiCount;

    //Game Startup Menu
    public Runner() {
        JFrame frame = new JFrame("Surround Menu");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        String[] amountOfAI = { "1", "2", "3" };
        JComboBox<String> selectAICount = new JComboBox<String>(amountOfAI);
        JLabel enterAI = new JLabel("Enter Amount of AI:");
        JButton single = new JButton("Single Player");
        single.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                multiplayer = false;
                int num = Integer.parseInt(selectAICount.getSelectedItem().toString());
                aiCount = num;
                Player[] players = new Player[num + 1];
                for (int i = 0; i < num + 1; i++) {
                    players[i] = new Player(i);
                }
                new Runner(players);
                new Singleplayer(players.length);
                frame.dispose();
            }

        });
        JButton host = new JButton("Host");
        JLabel hostIP = new JLabel("");
        JButton join = new JButton("Join");
        JTextField joinIP = new JTextField("Enter IP to Join");
        host.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                multiplayer = true;
                try {
                    net.Server s = new net.Server();
                    Thread server = new Thread(s);
                    server.start();
                    new net.Client("localhost");
                    hostIP.setText(InetAddress.getLocalHost().getHostAddress());
                } catch (IOException e1) {

                }
                // host.removeActionListener(this);
                // join.removeActionListener(this);
            }

        });
        join.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                multiplayer = true;
                try {
                    new net.Client(joinIP.getText());
                    hostsIP = joinIP.getText();
                } catch (IOException e1) {

                }
                // join.removeActionListener(this);
                // host.removeActionListener(this);
            }

        });
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    net.Client.out.writeUTF(net.Client.id + ": " + "start");
                    frame.dispose();
                } catch (Exception e1) {

                }
            }

        });
        panel.add(single);
        panel.add(enterAI);
        panel.add(selectAICount);
        panel.add(host);
        panel.add(hostIP);
        panel.add(join);
        panel.add(joinIP);
        panel.add(start);
        frame.setVisible(true);
    }

    //Game
    public Runner(Player[] players) {
        gFrame = new JFrame("Surround");
        gFrame.setSize(FRAME_SIZE, FRAME_SIZE + BAR_SIZE);
        gFrame.setLocationRelativeTo(null);
        gFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gPanel = new Board(players);
        gFrame.addKeyListener(new KeyboardListener());
        gFrame.add(gPanel);
        gFrame.setVisible(true);
    }

    //Game Over Menu
    public Runner(int x) {
        gFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JFrame frame = new JFrame("Game Over");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        JLabel replayLabel = new JLabel("Replay:");
        JLabel connectingLabel = new JLabel("");
        JButton replay = new JButton("Replay");
        replay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gFrame.dispose();
                if (multiplayer == true) {
                    if (isHost == true) {
                        try {
                            new net.Client("localhost");
                        } catch (IOException e1) {

                        }
                    } else if (isHost == false) {
                        try {
                            new net.Client(hostsIP);
                        } catch (IOException e1) {

                        }
                    }
                    connectingLabel.setText("Connecting...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e1) {

                    }
                    try {
                        net.Client.out.writeUTF(net.Client.id + ": " + "start");
                    } catch (IOException e1) {

                    }
                } else {
                    int num = aiCount;
                    Player[] players = new Player[num + 1];
                    for (int i = 0; i < num + 1; i++) {
                        players[i] = new Player(i);
                    }
                    new Runner(players);
                    new Singleplayer(players.length);
                }
                frame.dispose();
            }

        });
        JLabel menuLabel = new JLabel("Menu:");
        JButton menu = new JButton("Menu");
        menu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gFrame.dispose();
                new Runner();
                frame.dispose();
            }
            
        });
        panel.add(replayLabel);
        panel.add(replay);
        panel.add(connectingLabel);
        panel.add(menuLabel);
        panel.add(menu);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Runner();
    }
    
}