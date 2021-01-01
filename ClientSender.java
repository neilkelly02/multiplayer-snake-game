package net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientSender implements Runnable {

    public DataOutputStream out;

    public ClientSender(DataOutputStream outIn) {
        this.out = outIn;
    }

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        while (true) {
            String sendMessage = scan.nextLine();
            try {
                out.writeUTF(Client.id + ": " + sendMessage);
            } catch (IOException e) {
                break;
            }
        }
        scan.close();
    }
    
}