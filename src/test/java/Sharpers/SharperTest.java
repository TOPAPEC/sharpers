package Sharpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SharperTest {
    @Test
    void getScore() {
        var deck = new CardDeck();
        var sharper = new Sharper(deck, new FairPlayer[0]);
        assertEquals(sharper.getScore(), 0);
    }

    @Test
    void setScore() {
        var deck = new CardDeck();
        var sharper = new Sharper(deck, new FairPlayer[0]);
        sharper.setScore(40197592);
        assertEquals(sharper.getScore(), 40197592);
    }

    @Test
    void run() throws InterruptedException {
        var deck = new CardDeck();
        var fairPlayer = new FairPlayer(deck);
        var sharper = new Sharper(deck, new FairPlayer[]{fairPlayer});
        sharper.start();
        fairPlayer.start();
        Thread.sleep(1000);
        sharper.interrupt();
        fairPlayer.interrupt();
        fairPlayer.join();
        sharper.join();
        assertTrue(sharper.getScore() > 0);
    }
}