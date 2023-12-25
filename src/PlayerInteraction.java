import java.util.Scanner;

public class PlayerInteraction {
    private Scanner scanner;

    public PlayerInteraction(Scanner scanner) {
        this.scanner = scanner;
    }

    // Modified to include player number and current balance
    public double getBetAmount(int playerNumber, double currentBalance) {
        System.out.println("Player " + (playerNumber+1) + ", enter your bet amount (Balance: $" + currentBalance + "):");
        while (true) {
            double amount = scanner.nextDouble();
            if (amount > 0 && amount <= currentBalance) {
                return amount;
            } else {
                System.out.println("Invalid bet. Please enter a value greater than 0 and less than or equal to your balance.");
            }
        }
    }

    // Modified to include Hand parameter for context-aware choices
    public int getPlayerChoice(Hand playerHand) {
        return scanner.nextInt();
    }

    public boolean askForAnotherRound() {
        System.out.println("Do you want to play another round? (yes/no): ");
        scanner.nextLine(); // Consume the leftover newline
        String input = scanner.nextLine();
        return input.equalsIgnoreCase("yes");
    }

    // Additional methods for displaying specific messages
    public void displayInvalidBetMessage(int playerNumber) {
        System.out.println("Invalid bet amount for player " + playerNumber
                + ". Bet must be greater than 0 and less than or equal to balance.");
    }

    public void displayBustMessage() {
        System.out.println("Sorry, you have busted!");
    }

    public void displayBlackjackMessage(int playerNumber) {
        System.out.println("Congrats, player " + playerNumber + " has a blackjack!");
    }

    public void displayInvalidChoiceMessage() {
        System.out.println("Invalid choice. Please choose again.");
    }

    // Add more methods as needed based on your game's requirements
}
