import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A simple chat server that handles multiple client connections
 * and broadcasts messages to all connected clients.
 */
public class Server {
    private static final int PORT = 5555; // Port number for the server
    private static Set<ClientHandler> clientHandlers = new HashSet<>(); // Set to keep track of all client handlers

    public static void main(String[] args) {
        System.out.println("Chat server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Continuously accept new client connections
            while (true) {
                new ClientHandler(serverSocket.accept()).start(); // Handle each client in a new thread
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace in case of an error
        }
    }

    /**
     * Handles communication with a single client.
     */
    private static class ClientHandler extends Thread {
        private Socket socket; // Client socket
        private PrintWriter out; // Output stream to send messages to the client
        private BufferedReader in; // Input stream to read messages from the client
        private String clientName; // Client's name
        private String clientAddress; // Client's IP address

        public ClientHandler(Socket socket) {
            this.socket = socket; // Initialize with the client socket
            this.clientAddress = socket.getInetAddress().getHostAddress(); // Get client's IP address
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Ask the client for their name
                out.println("Enter your name:");
                clientName = in.readLine(); // Read client's name
                System.out.println(clientName + " connected from " + clientAddress);

                synchronized (clientHandlers) {
                    clientHandlers.add(this); // Add this client to the set
                }

                String message;
                // Read messages from the client and broadcast them to all clients
                while ((message = in.readLine()) != null) {
                    String formattedMessage = clientName + " (" + clientAddress + "): " + message;
                    System.out.println("Received: " + formattedMessage);
                    synchronized (clientHandlers) {
                        for (ClientHandler handler : clientHandlers) {
                            handler.out.println(formattedMessage); // Send message to all clients
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Print stack trace in case of an error
            } finally {
                try {
                    socket.close(); // Close the client socket
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientHandlers) {
                    clientHandlers.remove(this); // Remove this client from the set
                }
            }
        }
    }
}
