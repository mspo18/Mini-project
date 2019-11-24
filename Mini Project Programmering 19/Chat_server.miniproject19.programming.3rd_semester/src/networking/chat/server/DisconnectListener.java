package networking.chat.server;

import java.util.ArrayList;

public class DisconnectListener extends Thread {

    private ChatServer server;
    private ArrayList<UserConnection> userConnections;

    public DisconnectListener(ChatServer server, ArrayList<UserConnection> userConnections) {
        this.server = server;
        this.userConnections = userConnections;
    }

    public void run() {
        while (true) {
//            for (UserConnection user : userConnections) {
//                if(!user.isAlive()) {
//                    disconnect(user);
//                }
//            }
        }
    }

    private void disconnect(UserConnection aUser) {
        server.disconnectUser(aUser);
    }
}
