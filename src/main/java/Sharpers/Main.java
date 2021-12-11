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
            String playerStatus = players[i].getClass().getSimpleName();
            System.out.println(playerStatus + " " + i + " has score " + players[i].accessScore());
        }
    }

    private static void printWinner(PlayerBaseClass[] players) {
        // Get indices of player array sorted by score.
        int[] sortedIndices = IntStream.range(0, players.length).boxed()
                .sorted((i, j) -> players[i].accessScore() - players[j].accessScore())
                .mapToInt(ele -> ele).toArray();
        String playerStatus = players[sortedIndices.length - 1].getClass().getSimpleName();
        System.out.println("The winner is " + playerStatus + " " + sortedIndices[sortedIndices.length - 1] + "!");
    }

    private static void printPlayerNumInputTip() {
        System.out.println("Enter number of fair players and number of sharpers separated by space.\n" +
                "First number should be not less than 1 and sum of numbers should not be greater" +
                " than 100 (and you should consider your hardware limitations too).");
    }

    private static int[] parsePlayerCount(Scanner scanner) {
        int[] playerCount = new int[]{-1, -1};
        while (playerCount[0] == -1 && playerCount[1] == -1) {
            try {
                var input = scanner.nextLine().split(" ");
                int fairCountParsed = Integer.parseInt(input[0]);
                int sharperCountParsed = Integer.parseInt(input[1]);
                if (!(fairCountParsed < 1 || fairCountParsed + sharperCountParsed > 100)) {
                    playerCount[0] = fairCountParsed;
                    playerCount[1] = sharperCountParsed;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Wrong input format. \n" +
                        "First number should be not less than 1 and sum of numbers should not be greater" +
                        " than 100 (and you should consider your hardware limitations too). Try again.");
            }
        }
        return playerCount;
    }

    private static void runGameRound(PlayerBaseClass[] players) throws InterruptedException {
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
    }

    private static void runFirstGameMode() throws InterruptedException {
        CardDeck deck = new CardDeck();
        FairPlayer fairPlayer = new FairPlayer(deck);
        PlayerBaseClass[] players = new PlayerBaseClass[]{fairPlayer,
                new Sharper(deck, new FairPlayer[]{fairPlayer}),
                new Sharper(deck, new FairPlayer[]{fairPlayer})};
        runGameRound(players);
        printPlayersScores(players);
        printWinner(players);
    }

    private static void runSecondGameMode(Scanner scanner) throws InterruptedException {
        CardDeck deck = new CardDeck();
        printPlayerNumInputTip();
        var playerNum = parsePlayerCount(scanner);
        int fairPlayerNum = playerNum[0];
        int sharperNum = playerNum[1];
        FairPlayer[] fairPlayers = new FairPlayer[fairPlayerNum];
        Sharper[] sharpers = new Sharper[sharperNum];
        for (int i = 0; i < fairPlayerNum; ++i) {
            fairPlayers[i] = new FairPlayer(deck);
        }
        for (int i = 0; i < sharperNum; ++i) {
            sharpers[i] = new Sharper(deck, fairPlayers);
        }
        PlayerBaseClass[] players = new PlayerBaseClass[fairPlayerNum + sharperNum];
        for (int i = 0; i < fairPlayerNum + sharperNum; ++i) {
            if (i < fairPlayerNum) {
                players[i] = fairPlayers[i];
            }
            else {
                players[i] = sharpers[i - fairPlayerNum];
            }
        }
        runGameRound(players);
        printPlayersScores(players);
        printWinner(players);
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        printGreeting();
        int gameMode = parseGameMode(scanner);
        if (gameMode == 1) {
            runFirstGameMode();
        }
        else {
            runSecondGameMode(scanner);
        }
        System.out.println("Game ended successfully!");
    }

}
