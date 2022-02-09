package com.sternhalma.server.games;

import com.sternhalma.server.connection.Player;

/**
 * Interfejs reprezentujący grę (po stronie serwera) przechowywaną w GameManager
 */
public interface Game {
    /**
     * Dołącza gracza o handlerze player do gry.
     * @param player handler gracza
     */
    void joinPlayer(Player player);

    /**
     * Wykonuje akcję określoną odpowiednio spreparowanym Stringiem action przez gracza o handlerze player.
     * @param player handler gracza
     * @param action String określający akcję
     */
    void performAction(Player player, String action);

    boolean isEnded();

    /**
     * Zwraca unikalną nazwę gry - powinna być zapisana w pliku Games.java.
     * @return unikalna nazwa gry
     */
    String getGameName();
}
