package com.sternhalma.server.connection;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void testConstructor() {
        Socket socket = new Socket();
        assertEquals("Socket[unconnected]", socket.toString());
        assertFalse(socket.isConnected());
        assertFalse(socket.isClosed());
    }

    @Test
    void testIsAlive() {
        assertFalse((new Player(new Socket())).isAlive());
    }

    @Test
    void testSendObject() {
        Player player = new Player(new Socket());
        player.sendObject("Data");
        assertFalse(player.isAlive());
    }

    @Test
    void testSendMessage() {
        Player player = new Player(new Socket());
        player.sendMessage("Data");
        assertFalse(player.isAlive());
    }

//    @Test
//    void testSendObject() {
//        assertThrows(IllegalStateException.class, () -> (new Player(new Socket())).sendObject("Data"));
//    }
//
//    @Test
//    void testSendMessage() {
//        assertThrows(IllegalStateException.class, () -> (new Player(new Socket())).sendMessage("Data"));
//    }
}