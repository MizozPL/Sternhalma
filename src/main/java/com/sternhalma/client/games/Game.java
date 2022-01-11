package com.sternhalma.client.games;

import com.sternhalma.client.connection.Client;

/**
 * Interfejs reprezentujący grę (po stronie klienta).
 */
public interface Game {

    /**
     * Inicjalizuje grę i przekazuje sterowanie.
     * @param client handler do komunikacji z serwerem.
     */
    void init(Client client);
}
