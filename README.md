# Multithreaded Chat Server

A production-ready multithreaded chat server built with Java socket programming. Implements concurrent client connection handling using TCP/IP protocol with thread-safe message broadcasting to all connected clients.

## Features

- **Multithreaded Architecture**: Each client connection runs on a separate thread for concurrent handling
- **TCP/IP Socket Communication**: Reliable message delivery using Java ServerSocket and Socket APIs
- **Real-time Broadcasting**: Messages are instantly broadcast to all connected clients
- **Thread-Safe Operations**: Synchronized message handling prevents race conditions
- **Error Handling**: Robust exception handling and graceful client disconnection
- **Scalable Design**: Supports multiple simultaneous client connections

## Requirements

- Java Development Kit (JDK) 8 or higher
- Basic knowledge of Java and socket programming

## Files

- `Server.java`: The server-side code that handles client connections and message broadcasting.
- `Client.java`: The client-side code that connects to the server and sends/receives messages.

## Running the Application

## Technical Details

- **Port**: 5555 (configurable)
- **Protocol**: TCP/IP
- **Threading Model**: One thread per client connection
- **Message Format**: Plain text
- **Connection Management**: Automatic cleanup on client disconnect

## Architecture

The server uses a multi-threaded model where:
1. Main thread listens for incoming connections on ServerSocket
2. Each accepted client spawns a new ClientHandler thread
3. ClientHandler threads manage individual client I/O operations
4. Messages are broadcast to all connected clients using thread-safe collections

### 1. Compile the Code

Open a terminal or command prompt and navigate to the directory containing the Java files. Compile the server and client code using the following commands:

```sh
javac Server.java
javac Client.java
```

### 2. Start the Server

Run the server in one terminal:

```sh
java Server
```

You should see the message: `Chat server started...`.

### 3. Start the Client(s)

Open another terminal (or multiple terminals for multiple clients) and run the client:

```sh
java Client
```

The client will connect to the chat server at `localhost` on port `5555`. You can start typing messages in the client terminal, and they will be broadcast to all connected clients.

## Usage

- **Server**: Accepts incoming client connections and broadcasts messages.
- **Client**: Connects to the server, sends messages, and displays received messages.

## Example

After starting the server and one or more clients, messages sent from any client will appear in the terminal of all connected clients. 

For example:

```
Client A: Hello everyone!
Client B: Hello Client A!
```

Both messages will be displayed in the terminals of all connected clients.

## Contributing

If you have suggestions or improvements, feel free to fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
