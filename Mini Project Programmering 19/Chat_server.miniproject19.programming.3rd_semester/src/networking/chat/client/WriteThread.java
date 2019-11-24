package networking.chat.client;

import java.io.*;
import java.net.*;

/*
 * This thread is responsible for reading the user's input and send it
 * to the server.
 * It runs in an infinite loop until the user types "bye" to quit.
 */

public class WriteThread extends Thread{
	private PrintWriter writer;
	private Socket socket;
	private ChatClient client;
	private String username;

	public WriteThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;
		this.username = username;

		try {
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);			
		} catch (IOException ex ) {
			System.out.println("Error getting output stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void run() {
		writer.println(this.username);

		String text;

		do {
			text = this.username;
		} while (!text.equals("bye"));

		try {
			socket.close();
		} catch (IOException ex) {
			System.out.println("Error writing to server : " + ex.getMessage());
			
		}
	}
	
}
