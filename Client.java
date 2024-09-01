import java.io.*;
import java.net.*;

/**
 * A simple chat client that connects to a chat server,
 * sends messages to the server, and displays received messages.
 */
public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // Address of the chat server
    private static final int SERVER_PORT = 5555; // Port number of the chat server
    private static String clientName; // To store the client's name

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Read the server's request for the client's name
            System.out.println(in.readLine()); // Server's prompt for name
            clientName = userInput.readLine(); // User enters their name
            out.println(clientName); // Send name to the server

            // Start a new thread to read messages from the server
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage); // Display received messages
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread to read input from the user and send it to the server
            String userMessage;
            while ((userMessage = userInput.readLine()) != null) {
                // Prepend the client's name to the message
                out.println(clientName + ": " + userMessage); // Send user input to the server
            }

        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of an error
        }
    }
}
