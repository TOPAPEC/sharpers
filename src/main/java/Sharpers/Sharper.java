/*
 * File describes player that can still from others.
 */

package Sharpers;

import java.util.Random;

public class Sharper extends PlayerBaseClass {
    private final CardDeck deck;
    private final FairPlayer[] fairPlayersList;
    private final Random rnd;
    private int score;
    private String name;

    public Sharper(CardDeck deck, FairPlayer[] fairPlayersList) {
        this.deck = deck;
        score = 0;
        this.fairPlayersList = fairPlayersList;
        rnd = new Random();
    }

    public synchronized void setScore(int newScore) {
        this.score = newScore;
    }

    public synchronized int getScore() {
        return score;
    }

    /**
     * Starts process of drawing cards and stealing points.
     */
    public void run() {
        while (!this.isInterrupted()) {
            try {
                if (rnd.nextDouble() < 0.4) {
                    stealCard();
                } else {
                    drawCard();
                }
            } catch (InterruptedException e) {
                currentThread().interrupt();
            }
        }
    }

    /**
     * Steals card from random fair player.
     * @throws InterruptedException if thread was interrupted unexpectedly.
     */
    private void stealCard() throws InterruptedException {
        int playerToStealFrom = rnd.nextInt(fairPlayersList.length);
        int amountToSteal = rnd.nextInt(9);
        int stolenScore = fairPlayersList[playerToStealFrom].beRobbed(amountToSteal);
        setScore(score + stolenScore);
        int timeToSleep = rnd.nextInt(121) + 180;
//        System.out.println("Sharper steals " + stolenScore + ". Now he will sleep for " + timeToSleep + " ms");
        Thread.sleep(timeToSleep);
    }

    /**
     * Player gets one card from [1;10] range.
     * @throws InterruptedException if thread was interrupted unexpectedly.
     */
    private void drawCard() throws InterruptedException {
        int scoreDelta = deck.getCard();
        setScore(score + scoreDelta);
        int timeToSleep = rnd.nextInt(101) + 100;
//        System.out.println("Sharper gained " + scoreDelta + ". Now he will sleep for " + timeToSleep + " ms");
        Thread.sleep(timeToSleep);
    }
}
