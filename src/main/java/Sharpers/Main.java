package Sharpers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    private static void printGreeting() {
        System.out.println("Welcome to the 21 game!\n" +
                "Choose mode:\n" +
                "1. Standard two sharpers vs player\n" +
                "2. Custom number of sharpers and players");
    }

    private static int parseGameMode(Scanner scanner) {
        int gameMode = -1;
        while (gameMode == -1) {
            try {
                int parsed = Integer.parseInt(scanner.nextLine());
                if (parsed == 1 || parsed == 2) {
                    gameMode = parsed;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Wrong number format. You should enter 1 or 2.");
            }
        }
        return gameMode;
    }

    private static void printPlayersScores(PlayerBaseClass[] players) {
        System.out.println("Time to announce scores!");
        for (int i = 0; i < players.length; ++i) {
            System.out.println("Player " + i + " has score " + players[i].accessScore());
        }
    }

    private static void printWinner(PlayerBaseClass[] players) {
        int[] sortedIndices = IntStream.range(0, players.length).boxed()
                .sorted((i, j) -> players[i].accessScore() - players[j].accessScore())
                .mapToInt(ele -> ele).toArray();
        System.out.println("The winner is player " + sortedIndices[sortedIndices.length - 1] + "!");
    }

    private static void printPlayerNumInputTip() {
        System.out.println("Enter number of fair players and number of sharpers separated by space.\n" +
                "Sum of numbers should not be greater than 100 (and you should consider your hardware limitations too).");
    }

    private static void parsePlayerCount() {

    }

    private static void runFirstGameMode() throws InterruptedException {
        CardDeck deck = new CardDeck();
        FairPlayer fairPlayer = new FairPlayer(deck);
        PlayerBaseClass[] players = new PlayerBaseClass[]{fairPlayer,
                new Sharper(deck, new FairPlayer[]{fairPlayer}),
                new Sharper(deck, new FairPlayer[]{fairPlayer})};
        for (var player : players) {
            player.start();
        }
        Thread.sleep(10000);
        for (var player : players) {
            player.interrupt();
        }
        for (var player : players) {
            player.join();
        }
        printPlayersScores(players);
        printWinner(players);
    }

    private static void runSecondGameMode() {
        CardDeck deck = new CardDeck();

    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        printGreeting();
        int gameMode = parseGameMode(scanner);
        if (gameMode == 1) {
            runFirstGameMode();
        }
    }

}
