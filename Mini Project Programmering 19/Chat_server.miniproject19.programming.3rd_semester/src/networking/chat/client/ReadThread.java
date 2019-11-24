package networking.chat.client;

import java.io.*;
import java.net.*;


/*
 * This thread is responsible for reading the server's input and printing it
 * to the console 
 * It runs in an infinite loop until the client disconnects from the server
 */

public class ReadThread extends Thread {
	private BufferedReader reader;
	private Socket socket;
	private ChatClient client;
	private WindowUI windowUI;

	// Creating the constructor
	public ReadThread(Socket socket, ChatClient client, WindowUI windowUI) {
		this.socket = socket;
		this.client = client;
		this.windowUI = windowUI;

		try {
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException ex) {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				String response = reader.readLine();
				if(response==null) {
					socket.close();
					break;
				}
				System.out.println("\n" + response);

				// Prints the username after displaying the server's message
				System.out.println(response);

				windowUI.updateChat(response);
			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			}
		}
	}
}
