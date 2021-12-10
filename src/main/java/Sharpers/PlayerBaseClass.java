package Sharpers;

public abstract class PlayerBaseClass extends Thread {
    private int score;

    public abstract void changeScore(int newScore);

    public abstract int accessScore();
}
