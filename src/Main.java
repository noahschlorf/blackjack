import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /* 
        System.out.print("Enter the number of decks: ");
        int numberOfDecks = scanner.nextInt();

        System.out.print("Enter the number of players: ");
        int numberOfPlayers = scanner.nextInt();

        System.out.println("Should the dealer hit on a soft 17? (yes/no): ");
        scanner.nextLine(); 
        String input = scanner.nextLine();
        boolean dealerHitsSoft17 = input.equalsIgnoreCase("yes");
        */
        int numberOfDecks = 1;
        int numberOfPlayers = 1;
        boolean dealerHitsSoft17 = false;

        GameLogic game = new GameLogic(numberOfDecks, numberOfPlayers, dealerHitsSoft17);

        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Enter the bet you would like to place for player " + (i + 1) + ": ");
            double betAmount = scanner.nextDouble();
            game.getPlayerHand(i).setBet(betAmount);
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            Hand playerHand = game.getPlayerHand(i);
            System.out.println("Player " + (i + 1) + "'s hand: " + playerHand);
            if (playerHand.hasBlackjack()) {
                System.out.println("Player " + (i + 1) + " has Blackjack!");
            } else {
                System.out.println("Player " + (i + 1) + " does not have Blackjack.");
            }
        }

        System.out.println("Dealer's shown card: " + game.getDealerFirstCard());

        scanner.close();
    }
}
