package networking.chat.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * The ChatClient starts the client program,
 * and connects to a server specified by a hostname/IP address and port number.
 * Once the connection is made, it creates and starts two threads ReadThread and WriteThread.
 */

public class ChatClient {
	private String hostname;
	private int port;
	private Socket socket;
	private PrintWriter writer;
	private String username;

	public ChatClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	
	public void execute() {
		try {
			socket = new Socket(hostname, port);

			System.out.println("Connected to the chat server");

			WindowUI windowUI = new WindowUI(this);
			new ReadThread(socket, this, windowUI).start();

		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}
	}

	public void sendUser(String username) {
		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
			String userMessage = String.format("%s", username);
			writer.println(userMessage);
		} catch (IOException ex ) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void sendMessage(String username, String message) {
		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);
			String userMessage = String.format("%s: %s", username, message);
			writer.println(userMessage);
		} catch (IOException ex ) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// Makes a new client
	public static void main(String[] args) {
		if(args.length < 2) {
			System.out.println("Too few arguments");
			return;
		}

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);

		ChatClient client = new ChatClient(hostname, port); client.execute();

		ChatClient client2 = new ChatClient(hostname, port); client2.execute();
		  
		ChatClient client3 = new ChatClient(hostname, port); client3.execute();
		

	}
}
