package com.sternhalma.replay.replays;

import com.sternhalma.common.games.basicsternhalma.Board;
import com.sternhalma.common.replay.ReplayPacket;
import com.sternhalma.replay.gui.ReplayBasicSternhalmaPanel;
import com.sternhalma.replay.gui.ReplayFrame;
import com.sternhalma.server.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.server.GameHistory;

import javax.swing.*;
import java.util.HashMap;

public class BasicSternhalmaReplay {

    private final ReplayPacket replayPacket;
    private final BasicSternhalma basicSternhalmaInstance;
    private final HashMap<Integer, DummyPlayer> dummyPlayers;
    private final ReplayFrame frame;
    private final ReplayBasicSternhalmaPanel panel;
    private int counter;

    public BasicSternhalmaReplay(ReplayPacket gameHistory, ReplayFrame frame) {
        basicSternhalmaInstance = new BasicSternhalma();
        this.frame = frame;
        this.replayPacket = gameHistory;
        counter = 0;
        dummyPlayers = new HashMap<>();
        panel = new ReplayBasicSternhalmaPanel(this);
        frame.setReplayPanel(panel);
    }

    public void nextAction() {
        if(counter < replayPacket.playerIDs.size() && counter < replayPacket.requests.size()) {
            int playerID = replayPacket.playerIDs.get(counter);
            String request = replayPacket.requests.get(counter);

            System.out.println(playerID + ":" + request);

            if(request.equals(GameHistory.JOIN_REQUEST)) {
                DummyPlayer player = new DummyPlayer();
                dummyPlayers.put(playerID, player);
                basicSternhalmaInstance.joinPlayer(player);
            } else {
                DummyPlayer player = dummyPlayers.get(playerID);
                basicSternhalmaInstance.performAction(player, request);
            }

            counter++;

            int controlNumber = basicSternhalmaInstance.getTurn() % (dummyPlayers.size() > 1 ? dummyPlayers.size() : 2);
            int realTurn = (controlNumber == 0 ? dummyPlayers.size() : controlNumber);
            panel.setTurn(realTurn);

            panel.repaint();
        } else {
            JOptionPane.showMessageDialog(frame, "Replay ended!", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Board getBoard() {
        return basicSternhalmaInstance.getBoard();
    }
}
