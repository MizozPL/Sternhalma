package com.sternhalma.common.games;

/**
 * Statyczna klasa służąca jako rejestr wszystkich gier/ich wariantów.
 */
public class Games {
    /**
     * Prywatny konstrukktor - klasa statyczna.
     */
    private Games() {
    }

    /**
     * Podstawowa wersja gry.
     */
    public static final String BASIC_STERNHALMA = "BASIC_STERNHALMA";
    public static final String STERNHALMA_NO_JUMPS = "STERNHALMA_NO_JUMPS";
    public static final String STERNHALMA_EXTENDED_MOVE_LENGTH = "STERNHALMA_EXTENDED_MOVE_LENGTH";
}
