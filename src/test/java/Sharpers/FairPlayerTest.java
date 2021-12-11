package Sharpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FairPlayerTest {

    @Test
    void getScore() {
        var deck = new CardDeck();
        var fairPlayer = new FairPlayer(deck);
        assertEquals(fairPlayer.getScore(), 0);
    }

    @Test
    void setScore() {
        var deck = new CardDeck();
        var fairPlayer = new FairPlayer(deck);
        fairPlayer.setScore(40197592);
        assertEquals(fairPlayer.getScore(), 40197592);
    }

    @Test
    void run() throws InterruptedException {
        var deck = new CardDeck();
        var fairPlayer = new FairPlayer(deck);
        fairPlayer.start();
        Thread.sleep(1000);
        fairPlayer.interrupt();
        fairPlayer.join();
        assertTrue(fairPlayer.getScore() > 0);
    }

    @Test
    void beRobbed() {
        var deck = new CardDeck();
        var fairPlayer = new FairPlayer(deck);
        fairPlayer.setScore(5);
        int stealed = fairPlayer.beRobbed(8);
        assertEquals(stealed, 5);
        assertEquals(fairPlayer.getScore(), 0);
    }
}