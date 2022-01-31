package com.sternhalma.replay.replays;

import com.sternhalma.server.connection.Player;

import java.net.Socket;

public class DummyPlayer extends Player {
    public DummyPlayer() {
        super(null);
    }

    @Override
    public void run() {
        //do nothing
    }

    @Override
    public synchronized boolean isAlive() {
        return true;
    }

    @Override
    public synchronized void sendObject(Object data) {
        //do nothing
    }

    @Override
    public void sendMessage(String data) {
        //do nothing
    }
}
