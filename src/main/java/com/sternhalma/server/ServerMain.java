package com.sternhalma.server;

import com.sternhalma.server.connection.Server;
import com.sternhalma.server.games.GameManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Klasa parsująca adres i port dla serwera oraz go uruchamiająca.
 */
@SpringBootApplication
public class ServerMain {

    /**
     * Domyślny port serwera.
     */
    public static int PORT = 25000;
    /**
     * Domyślny adres serwera.
     */
    public static String ADDRESS = "localhost";

    /**
     * Punkt wejściowy serwera.
     *
     * @param args - opcjonalne port(args[0]) i adres(args[1])
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                PORT = Integer.parseInt(args[1]);
                ADDRESS = args[0];
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        SpringApplication.run(ServerMain.class, args);
        Server server = new Server(ADDRESS, PORT);
        server.start();
    }

    @Bean
    public CommandLineRunner run(GameHistoryRepository gameHistoryRepository) {
        return (args -> {
            GameManager.gameHistoryRepository = gameHistoryRepository;
        });
    }
}