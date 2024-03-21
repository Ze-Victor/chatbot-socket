package chatbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private String lastMessage;
    private boolean newMessageReceived;

    public ClientThread(Socket socket) {
        this.socket = socket;
        this.lastMessage = null;
        this.newMessageReceived = false;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(inputReader);

            String x;
            while ((x = reader.readLine()) != null) {
                synchronized (this) {
                    lastMessage = x;
                    newMessageReceived = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean hasReceivedNewMessage() {
        return newMessageReceived;
    }

    public synchronized String getLastMessage() {
        newMessageReceived = false;
        return lastMessage;
    }
}
