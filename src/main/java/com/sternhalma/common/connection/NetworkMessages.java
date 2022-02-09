package com.sternhalma.common.connection;

/**
 * Klasa służąca za rejestr wiadomości (komunikatów i żądań) dla klienta i serwera.
 */
public class NetworkMessages {
    public static final String JOIN_GAME = "JOIN_GAME";
    public static final String GAME_FULL = "GAME_FULL";
    public static final String PLAYER_ALREADY_IN_GAME = "PLAYER_ALREADY_IN_GAME";
    public static final String CREATE_GAME = "CREATE_GAME";
    public static final String PERFORM_ACTION = "PERFORM_ACTION";
    public static final String INVALID_ACTION = "INVALID_ACTION";
    public static final String BAD_REQUEST = "BAD_REQUEST";
    public static final String GAME_WITH_ID_EXISTS = "GAME_WITH_ID_EXISTS";
    public static final String GAME_WITH_ID_DOES_NOT_EXIST = "GAME_WITH_ID_DOES_NOT_EXIST";
    public static final String BAD_GAME_TYPE = "BAD_GAME_TYPE";
    public static final String GAME_CREATED = "GAME_CREATED";

    //BASIC_STERNHALMA
    public static final String BOARD_UPDATE = "BOARD_UPDATE";
    public static final String END_TURN = "END_TURN";
    public static final String MOVE = "MOVE";
    public static final String WRONG_TURN = "WRONG_TURN";
    public static final String WAITING_FOR_MORE_PLAYERS = "WAITING_FOR_MORE_PLAYERS";
    public static final String BAD_PIECE = "BAD_PIECE";
    public static final String WINNER = "WINNER";
    public static final String GAME_END = "GAME_END";
    public static final String GAME_END_PLAYER_DISCONNECTED = "GAME_END_PLAYER_DISCONNECTED";

    //REPLAYS
    public static final String LIST_REPLAYS = "LIST_REPLAYS";
    public static final String GET_REPLAY = "GET_REPLAY";

    /**
     * Prywatny konstruktor.
     */
    private NetworkMessages() {
    }
}
