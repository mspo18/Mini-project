package networking.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionListener extends Thread {

    private ChatServer server;
    private int port;
    private ArrayList<UserConnection> userConnections;

    public ConnectionListener(ChatServer server, int port, ArrayList<UserConnection> userConnections) {
        this.server = server;
        this.port = port;
        this.userConnections = userConnections;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                // New user is added
                UserConnection newUser = new UserConnection(socket, server);
                userConnections.add(newUser);
                newUser.start();
            }


        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }

    }
}
