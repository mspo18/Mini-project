package networking.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WindowUI {

    private JFrame frame;
    private JTextArea textArea;
    private JTextArea outputWindow;
    private ChatClient client;
    private String username;

    /**
     * Create the application.
     */
    public WindowUI(ChatClient client) {
        this.client = client;
        initialize();
    }

    public void updateChat(String message) {
        if(outputWindow != null) {
            String output = String.format("%s \r\n", message);
            outputWindow.append(output);
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        // The full screen
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 700, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
//                    client.shutdown();
                    System.exit(0);
                }
            }
        });

        // Input Window
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        textArea.setBounds(10, 300, 500, 100);
        frame.getContentPane().add(textArea);

        // Output Window
        outputWindow = new JTextArea();
        outputWindow.setFont(new Font("Monospaced", Font.BOLD, 14));
        outputWindow.setEditable(false);
        outputWindow.setBounds(10, 10, 675, 250);
        frame.getContentPane().add(outputWindow);

        // Send button
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        sendButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                updateChat(username + ": " + textArea.getText());
                client.sendMessage(username, textArea.getText());
                textArea.setText(null);
            }
        });
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

            }
        });
        sendButton.setBounds(550, 300, 125, 100);
        frame.getContentPane().add(sendButton);
        frame.setVisible(true);

        username = JOptionPane.showInputDialog(frame,
                "What is your name?", null);
        client.sendUser(username);
    }
}
