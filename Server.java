package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    public ServerSocket serverSocket;
    public Socket socket;
    public DataOutputStream serverOut;
    public DataInputStream serverIn;
    public static final int MAX_USERS = 4;
    public static int playersConnected = 0;
    public static ArrayList<Users> users = new ArrayList<>();
    public static boolean[] wantsToStart = new boolean[MAX_USERS];
    public static Thread gameTickThread = new Thread(new Conductor());
    public static char[] directions = {'w', 'w', 'w', 'w'};

    public Server() throws IOException {
        serverSocket = new ServerSocket(7777);
    }

    public void acceptClientCreateUser() throws IOException {
        while (true) {
            socket = serverSocket.accept();
            System.out.println(socket.getInetAddress());
            serverOut = new DataOutputStream(socket.getOutputStream());
            serverIn = new DataInputStream(socket.getInputStream());
            users.add(new Users(serverOut, serverIn, users, playersConnected));
            Thread thread = new Thread(users.get(playersConnected++));
            thread.start();
        }
    }

    @Override
    public void run() {
        try {
            acceptClientCreateUser();
        } catch (IOException e) {

        }
    }

    public static void refresh() {
        playersConnected = 0;
        users = new ArrayList<>();
        wantsToStart = new boolean[MAX_USERS];
        gameTickThread = new Thread(new Conductor());
    }
    
}