package networking.chat.server;

import java.io.*;
import java.net.*;

// This Thread handles the connection for each client, 
//so the server can handle multiple clients at the same time

public class UserConnection extends Thread {
	private Socket socket;
	private ChatServer server;
	private PrintWriter writer;
	private String username;

	// Creating the constructor
	public UserConnection(Socket socket, ChatServer server) {
		this.socket = socket;
		this.server = server;
	}
	
	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
			
			printUser();
			
			String userName = reader.readLine();
			setUsername(userName);
			server.addUserName(userName);
			
			String serverMessage = "New user connected: " + userName;
			server.broadcast(serverMessage, this);
			
			do {
				serverMessage = reader.readLine();
				System.out.println(serverMessage);
				server.broadcast(serverMessage, this);
			} while(!serverMessage.equals(userName + ": " + "bye"));
			server.disconnectUser(this);
			socket.close();

			serverMessage = userName + " has left the server.";
			server.broadcast(serverMessage, this);
			
		} catch (IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	// Sends a list of online users to the newly connected user.
	void printUser() {
		if(server.hasUser()) {
			writer.println("Connected users: " + server.getUserNames());			
		} else {
			writer.println("No other users connected.");
		}
	}
	
	// Sends a message to the client
	void sendMessage(String message) {
		if(writer != null) {
			writer.println(message);
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}
}
