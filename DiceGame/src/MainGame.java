
import java.util.*;

public class MainGame {
    ArrayList<Player> players;
    int totalPlayers;
    int pointsAccumulateToWin;

    MainGame(int totalPlayers, int pointsAccumulateToWin) {
        this.totalPlayers = totalPlayers;
        this.pointsAccumulateToWin = pointsAccumulateToWin;
    }

    public void createGame() {
        players = new ArrayList<Player>();
        ArrayList<Integer> playerIds = new ArrayList<Integer>();
        for (int i = 0; i < totalPlayers; i++) {
            playerIds.add(i + 1);
        }
        Collections.shuffle(playerIds);
        for (int i = 0; i < totalPlayers; i++) {
            String playerId = "Player-" + playerIds.get(i);
            Player player = new Player(playerId);
            players.add(player);
        }
    }

    public void letsPlay() {
        createGame();
        int diceRoll = 0, remainingPlayers = totalPlayers;
        while (remainingPlayers >= 1) {
            for (int i = 0; i < totalPlayers; i++) {
                Player currentPlayer = players.get(i);
                if (currentPlayer.playerState == 0) {
                    displayMessage(currentPlayer.playerId + " due to consecutive ones you can't play this turn", true);
                    currentPlayer.playerCanPlayNextTurn();
                    continue;
                } else if (currentPlayer.playerState == 3) {
                    continue;
                }
                displayMessage(currentPlayer.playerId + " Its Your turn to play.", true);
                diceRoll = rollTheDice();
                currentPlayer.increaseScore(diceRoll);
                if (currentPlayer.score >= pointsAccumulateToWin) {
                    remainingPlayers--;
                    currentPlayerHasWon(currentPlayer, remainingPlayers);
                    continue;
                } else if (diceRoll == 6) {
                    displayMessage("Woah. You Rolled a 6. You Get Another Chance to Roll", true);
                    diceRoll = rollTheDice();
                    currentPlayer.increaseScore(diceRoll);
                    if (currentPlayer.score >= pointsAccumulateToWin) {
                        remainingPlayers--;
                        currentPlayerHasWon(currentPlayer, remainingPlayers);
                        continue;
                    }
                } else if (diceRoll == 1) {
                    currentPlayer.playerState = (currentPlayer.playerState == 2) ? 0 : 2;
                }
                displayMessage("*****Here is the current Rank Table*****", true);
                printRankTable();
            }
        }
        displayMessage("Game Over. Thanks For Playing.", true);
        printFinalRankTable();

    }

    public int rollTheDice() {
        char inputChar;
        Scanner in = new Scanner(System.in);
        displayMessage("Press r to roll the dice->", false);
        inputChar = in.next().charAt(0);
        while (inputChar != 'r') {
            displayMessage("Invalid Input. Press r to roll the dice->", false);
            inputChar = in.next().charAt(0);
        }

        int diceValue;
        Random rand = new Random();
        do {
            diceValue = rand.nextInt(7);
        } while (diceValue == 0);
        displayMessage("Dice rolled is: " + diceValue, true);
        return diceValue;
    }

    public void printRankTable() {
        
        ArrayList<Player> wonPlayers = new ArrayList<Player>();
        ArrayList<Player> playingPlayers = new ArrayList<Player>();
        for (int i = 0; i < totalPlayers; i++) {
            Player player = players.get(i);
            if (player.playerState == 3)
                wonPlayers.add(player);
            else
                playingPlayers.add(player);
        }
        sortOnRank(wonPlayers);
        sortOnScore(playingPlayers);

        int rank = 1;
        displayMessage("Players Already Won:", true);
        for (int i = 0; i < wonPlayers.size(); i++) {
            Player player = wonPlayers.get(i);
            displayMessage("Rank:" + rank + " -> " + player.playerId, false);
            rank++;
        }

        displayMessage("Players Currently Playing:", true);
        for (int i = 0; i < playingPlayers.size(); i++) {
            Player player = playingPlayers.get(i);
            displayMessage("Rank:" + rank + " -> " + player.playerId + ", Score: " + player.score, false);
            rank++;
        }
        displayMessage("********************************", true);
    }

    public void printFinalRankTable() {
        displayMessage("***** Here is the final Rank Table ********", true);
        ArrayList<Player> wonPlayers = new ArrayList<Player>();
        for (int i = 0; i < totalPlayers; i++) {
            Player player = players.get(i);
            if (player.playerState == 3)
                wonPlayers.add(player);
        }
        sortOnRank(wonPlayers);

        int rank = 1;

        for (int i = 0; i < wonPlayers.size(); i++) {
            Player player = wonPlayers.get(i);
            displayMessage("Rank:" + rank + " -> " + player.playerId, false);
            rank++;
        }
        displayMessage("********************************", true);
    }

    public void displayMessage(String message, boolean lineChange) {
        if(lineChange)
        System.out.println("\n" + message);
        else
        System.out.println(message);
    }

    public void currentPlayerHasWon(Player player, int remainingPlayers) {
        displayMessage("Congratulations! " + player.playerId + " You Have Won", true);
        player.playerHasWon();
        player.rank = totalPlayers - remainingPlayers;
        displayMessage("*****Here is the current Rank Table*****", true);
        printRankTable();
    }

    public void sortOnScore(ArrayList<Player> list) {
        Collections.sort(list, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                if (p1.score > p2.score)
                    return -1;
                else
                    return 1;
            }
        });
    }

    public void sortOnRank(ArrayList<Player> list) {
        Collections.sort(list, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                if (p1.rank < p2.rank)
                    return -1;
                else
                    return 1;
            }
        });
    }
}
