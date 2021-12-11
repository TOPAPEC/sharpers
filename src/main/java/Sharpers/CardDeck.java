package Sharpers;

import java.util.Random;

public class CardDeck {
    private Random rnd;

    public CardDeck() {
        rnd = new Random();
    }

    public synchronized int getCard() {
        return rnd.nextInt(9) + 1;
    }
}
