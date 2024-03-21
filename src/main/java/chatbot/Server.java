package chatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("Servidor Iniciado!");
        ServerSocket serverSocket = new ServerSocket(4000);
        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectou");

        InputStreamReader inputReader = new InputStreamReader(socket.getInputStream());
        PrintStream saida = new PrintStream(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(inputReader);

        Bot bot = new Bot("super", getResourcesPath());
        Chat chatSession = new Chat(bot);

        String x;
        while ((x = reader.readLine()) != null) {
            String response = chatSession.multisentenceRespond(x);
            saida.println("Servidor: " + response);
        }

        serverSocket.close();
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 16);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
