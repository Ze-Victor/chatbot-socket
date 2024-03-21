package chatbot;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4000);
        Scanner scanner = new Scanner(System.in);
        PrintStream saida = new PrintStream(socket.getOutputStream());

        ClientThread clientThread = new ClientThread(socket);
        clientThread.start();

        System.out.print("Cliente: ");
        String teclado = scanner.nextLine();
        saida.println(teclado);

        while (true) {
            while (!clientThread.hasReceivedNewMessage()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(clientThread.getLastMessage());
            
            System.out.print("Cliente: ");
            teclado = scanner.nextLine();
            if (teclado.equals("Sair")) {
                break;
            }
            saida.println(teclado);
        }

        saida.close();
        scanner.close();
        socket.close();
    }
}
