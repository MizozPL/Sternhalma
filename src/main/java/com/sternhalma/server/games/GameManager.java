package com.sternhalma.server.games;

import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.server.GameHistory;
import com.sternhalma.server.GameHistoryRepository;
import com.sternhalma.server.connection.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Klasa zarządzająca wszystkimi istniejącymi grami na serwerze. Singleton.
 */
public class GameManager {

    /**
     * Instancja klasy.
     */
    private static final GameManager instance = new GameManager();
    /**
     * Mapa kodów gier i ich instancji.
     */
    private final HashMap<String, Game> games;

    private final HashMap<String, GameHistory> gameHistories;

    private final HashSet<String> savedGames;

    public static GameHistoryRepository gameHistoryRepository;

    /**
     * Prywatny konstruktor (singleton).
     */
    private GameManager() {
        games = new HashMap<>();
        gameHistories = new HashMap<>();
        savedGames = new HashSet<>();
    }

    /**
     * Zwraca instancję klasy.
     *
     * @return instancja klasy
     */
    public static GameManager getInstance() {
        return instance;
    }

    /**
     * Tworzy nową grę o danym kodzie i typie.
     *
     * @param player   handler twórcy gry
     * @param gameID   id gry, dowolny unikalny string
     * @param gameType typ gry (z Games.java)
     */
    public synchronized void createGame(Player player, String gameID, String gameType) {
        if (games.containsKey(gameID)) {
            player.sendMessage(NetworkMessages.GAME_WITH_ID_EXISTS);
            return;
        }
        Game game = GameFactory.createGameInstance(gameType);
        if (game == null) {
            player.sendMessage(NetworkMessages.BAD_GAME_TYPE);
            return;
        }
        games.put(gameID, game);
        gameHistories.put(gameID, new GameHistory());
        player.sendMessage(NetworkMessages.GAME_CREATED);
    }

    /**
     * Wykonuje akcję action przez gracza o handlerze player na grze gameID.
     *
     * @param player handler gracza wykonującego akcję
     * @param gameID id gry, na której akcja jest wykonana
     * @param action odpowiednio spreparowany String akcji
     */
    public synchronized void performAction(Player player, String gameID, String action) {
        if (games.containsKey(gameID)) {
            games.get(gameID).performAction(player, action);
            gameHistories.get(gameID).putAction(player, action);
            if (!savedGames.contains(gameID) && games.get(gameID).isEnded()) {
                savedGames.add(gameID);
                gameHistoryRepository.save(gameHistories.get(gameID));
                //TODO: Zapis do bazy danych ^ chyba git;

            }

            return;
        }
        player.sendMessage(NetworkMessages.GAME_WITH_ID_DOES_NOT_EXIST);
    }

    /**
     * Dołącza gracza o handlerze player do gry gameID.
     *
     * @param player handler gracza
     * @param gameID id gry
     */
    public synchronized void joinGame(Player player, String gameID) {
        if (games.containsKey(gameID)) {
            games.get(gameID).joinPlayer(player);
            gameHistories.get(gameID).putPlayerJoin(player);
            return;
        }
        player.sendMessage(NetworkMessages.GAME_WITH_ID_DOES_NOT_EXIST);
    }

    public synchronized GameHistory getGameReplay(int replayID) {
        Optional<GameHistory> gameHistory = gameHistoryRepository.findById(replayID);
        return gameHistory.orElse(null);
    }

    public synchronized List<Integer> getAllReplayIDs() {
        return gameHistoryRepository.selectReplayIDs();
    }

}
