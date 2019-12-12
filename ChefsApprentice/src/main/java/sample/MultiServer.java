package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiServer {

    public static final int PORT = 4444;

    public void runServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = null;
        System.out.println("Server running...");
        while (true){
            try {
                socket = serverSocket.accept();
            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("Accepting new socket...");
            new ServerThread(socket).start();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MultiServer s = new MultiServer();
        s.runServer();
    }
}
