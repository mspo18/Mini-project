package networking.chat.server;

import java.util.ArrayList;

//Creating the chat server program

public class ChatServer {

    // Variables are declared, and set (unordered collection of objects) is used to keep track of the names and threads of the connected clients.
	private int port;
	private ArrayList<String> userNames = new ArrayList<>(); // liste af brugernavne
	private ArrayList<UserConnection> userConnections = new ArrayList<>(); // liste af bruger connections

	// Creating the constructor
	public ChatServer(int port) {
		this.port = port;
	}
	
	//
	public void execute() {
		ConnectionListener connectionListener = new ConnectionListener(this, this.port, this.userConnections);
		connectionListener.start();

		//Virker ikke - skulle lytte på socket disconnects
		DisconnectListener disconnectListener = new DisconnectListener(this, this.userConnections);
		disconnectListener.start();
	}
	
	// The portnumber is defined and the server starts up
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntax: java ChatServer <port-number> ");
			System.exit(0);
		}
		int port = Integer.parseInt(args[0]);
//		int port = 8989;
		ChatServer server = new ChatServer(port);
		server.execute();
	}
	
	// Delivers a message from one user to the other users (broadcasting)
	public void broadcast(String message, UserConnection excludeUser) {
		for (UserConnection aUser : userConnections) {
			if (aUser != excludeUser) {
				aUser.sendMessage(message);
			}
		}
	}
	
	// Stores username of the newly connected client
	void addUserName(String userName) {
		userNames.add(userName);
	}
	
	// When a client is disconnected, removes the associated username and UserThread
	public void disconnectUser(UserConnection aUser) {
		boolean removed = userNames.remove(aUser.getUsername());
		if (removed) {
			userConnections.remove(aUser);
			userNames.remove(aUser.getUsername());
			System.out.println("The user " + aUser.getUsername() + " has left the chat room");
		}
	}
	
	// gets the list of usersnames recognized by the server
	public ArrayList<String> getUserNames() {
		return this.userNames;
	}
	
	// Returns true if other users are connected (not count the currently connected user)
	public boolean hasUser() {
		return this.userNames.size() > 0;
	}
}
