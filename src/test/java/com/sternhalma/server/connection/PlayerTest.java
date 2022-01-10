package com.sternhalma.server.connection;

import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    @Test
    void testSendObject() {
        assertThrows(IllegalStateException.class, () -> (new Player(new Socket())).sendObject("Data"));
    }

    @Test
    void testSendMessage() {
        assertThrows(IllegalStateException.class, () -> (new Player(new Socket())).sendMessage("Data"));
    }
}