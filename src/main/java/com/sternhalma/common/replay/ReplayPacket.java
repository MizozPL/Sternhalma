package com.sternhalma.common.replay;

import java.io.Serializable;
import java.util.List;

public class ReplayPacket implements Serializable {
    public final List<Integer> playerIDs;
    public final List<String> requests;

    public ReplayPacket(List<Integer> playerIDs, List<String> requests){
        this.playerIDs = playerIDs;
        this.requests = requests;
    }
}
