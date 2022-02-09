package com.sternhalma.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Integer> {
    @Query("SELECT DISTINCT replayID FROM GameHistory"
    )
    List<Integer> selectReplayIDs();
}