import java.util.*;
public class DiceGame {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Let's Start The Dice Game.\n");
        System.out.print("Enter the Number Of Players: ");
        int totalPlayers = sc.nextInt();
        System.out.print("\nEnter the Points Needed To Win: ");
        int pointsAccumulateToWin = sc.nextInt();
        MainGame game = new MainGame(totalPlayers, pointsAccumulateToWin);
        game.letsPlay();
    }
}
