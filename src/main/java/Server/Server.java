package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;

    public void listen() {
        try {
            System.out.println("System is running on port 2137...");

            int port = 2137;
            int playerCount = 0;
            var pool = Executors.newFixedThreadPool(6);
            this.serverSocket = new ServerSocket(port);


            while (true) {
                playerCount++;
                Socket socket = serverSocket.accept();
                pool.execute(new Player(socket, playerCount));
            }


        } catch (Exception e) {
            e.getStackTrace();
            System.exit(-1);
        }
    }
}
