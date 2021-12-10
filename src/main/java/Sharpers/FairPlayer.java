package Sharpers;

import java.util.Random;

public class FairPlayer extends PlayerBaseClass {
    private final CardDeck deck;
    private final Random rnd;
    private int score;
    private String name;

    public FairPlayer(CardDeck deck) {
        this.deck = deck;
        score = 0;
        rnd = new Random();
    }

    public synchronized void changeScore(int newScore) {
        this.score = newScore;
    }

    public synchronized int accessScore() {
        return score;
    }

    public void run() {
        while (!this.isInterrupted()) {
            try {
                drawCard();
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
    }

    public synchronized int beRobbed(int amountToSteal) {
        amountToSteal = Math.min(amountToSteal, score);
        changeScore(score - amountToSteal);
        return amountToSteal;
    }

    private void drawCard() throws InterruptedException {
        int scoreDelta = deck.getCard();
        changeScore(score + scoreDelta);
        int timeToSleep = rnd.nextInt(101) + 100;
//        System.out.println("Player gained " + scoreDelta + ". Now he will sleep for " + timeToSleep + " ms");
        Thread.sleep(timeToSleep);
    }
}
