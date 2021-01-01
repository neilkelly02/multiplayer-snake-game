package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public Socket socket;
    public static DataInputStream in;
    public static DataOutputStream out;
    public static int id;
    public static game.Player[] players;

    public Client(String ip) throws UnknownHostException, IOException {
        try {
            socket = new Socket(ip, 7777);
        } catch (Exception e) {
            socket = new Socket("localhost", 7777);
        }
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        Thread recieve = new Thread(new ClientReciever(in));
        recieve.start();
        Thread send = new Thread(new ClientSender(out));
        send.start();
    }
    
}