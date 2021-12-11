/*
 * File describes class of player that can't steal score from others.
 */

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

    public synchronized void setScore(int newScore) {
        this.score = newScore;
    }

    public synchronized int getScore() {
        return score;
    }

    /**
     * Starts process of drawing card for player.
     */
    public void run() {
        while (!this.isInterrupted()) {
            try {
                drawCard();
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
    }

    /**
     * Lets sharpers steal points from player.
     * @param amountToSteal number of points to steal.
     * @return stealed number (may be lesser than intended number).
     */
    public synchronized int beRobbed(int amountToSteal) {
        amountToSteal = Math.min(amountToSteal, score);
        setScore(score - amountToSteal);
        return amountToSteal;
    }

    /**
     * Player gets one card from [1;10] range.
     * @throws InterruptedException if thread was interrupted unexpectedly.
     */
    private void drawCard() throws InterruptedException {
        int scoreDelta = deck.getCard();
        setScore(score + scoreDelta);
        int timeToSleep = rnd.nextInt(101) + 100;
//        System.out.println("Player gained " + scoreDelta + ". Now he will sleep for " + timeToSleep + " ms");
        Thread.sleep(timeToSleep);
    }
}
