package databse;

import com.sternhalma.server.connection.Player;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public class GameHistory {

    public GameHistory() {

    }
@Transient
private static final String JOIN_REQUEST = "JOIN";
    @Transient
    private int counter = 0;
    @Transient
    private HashMap<Player, Integer> players = new HashMap<>();
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "replayID", nullable = false)
    private Integer replayID;

    @ElementCollection
    private List<Integer> playerIDs = new ArrayList<>();
    @ElementCollection
    private List<String> requests = new ArrayList<>();


    public void putAction(Player player, String request) {
        if (players.containsKey(player)) {
            playerIDs.add(players.get(player));
            requests.add(request);
        }
    }

    public void putPlayerJoin(Player player) {
        if (!players.containsKey(player)) {
            players.put(player, counter);
            playerIDs.add(counter);
            requests.add(JOIN_REQUEST);
            counter++;
        }
    }

    public Integer getId() {
        return replayID;
    }
}