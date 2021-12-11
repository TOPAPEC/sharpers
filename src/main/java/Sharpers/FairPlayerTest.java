package Sharpers;

import static org.junit.jupiter.api.Assertions.*;

class FairPlayerTest {

    @org.junit.jupiter.api.Test
    void accessScore() {
        var deck = new CardDeck();
        var fairPlayer = new FairPlayer(deck);
        assertEquals(fairPlayer.accessScore(), );
    }

    @org.junit.jupiter.api.Test
    void changeScore() {

    }



    @org.junit.jupiter.api.Test
    void run() {
    }

    @org.junit.jupiter.api.Test
    void beRobbed() {
    }
}