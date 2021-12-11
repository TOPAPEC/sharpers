package Sharpers;

public abstract class PlayerBaseClass extends Thread {
    private int score;

    public abstract void setScore(int newScore);

    public abstract int getScore();
}
