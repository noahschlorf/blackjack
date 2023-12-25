public class GameDisplay {

    // ANSI color codes for styling the console output
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[91m";
    private final String ANSI_YELLOW = "\u001B[33m";

    public GameDisplay() {
    }

    public void printPlayerHand(int playerNumber, Hand hand) {
        System.out.println("Player " + playerNumber + "'s hand: " + hand + " | Value: " + hand.getTotalValue() + " | Bet: " + ANSI_YELLOW + "$" + hand.getBet() + ANSI_RESET);
    }

    public void printDealerFirstCard(String card) {
        System.out.println(ANSI_RED + "Dealer's shown card: " + card + ANSI_RESET);
    }

    public void printDealerHand(Hand dealerHand) {
        System.out.println("Dealer's hand: " + dealerHand + " | Value: " + dealerHand.getTotalValue());
    }

    public void printBlackjackMessage(int playerNumber) {
        System.out.println(ANSI_GREEN + "Player " + playerNumber + " has Blackjack!" + ANSI_RESET);
    }

    public void printPlayerOptions(Hand hand, int playerNumber) {
        System.out.println("Player " + playerNumber + "'s Options:");
        System.out.println("1: Hit");
        System.out.println("2: Stand");
        if (hand.canDoubleDown()) {
            System.out.println("3: Double Down");
        }
        if (hand.canSplit()) {
            System.out.println("4: Split");
        }
    }

    public void printInsufficientBalanceForDoubleDown() {
        System.out.println(ANSI_RED + "Insufficient balance for Double Down." + ANSI_RESET);
    }

    public void printInsufficientBalanceForSplit() {
        System.out.println(ANSI_RED + "Insufficient balance for Split." + ANSI_RESET);
    }

    public void printInvalidChoiceMessage() {
        System.out.println(ANSI_RED + "Invalid choice. Please choose again." + ANSI_RESET);
    }

    public void printBustMessage() {
        System.out.println(ANSI_RED + "Bust! Over 21." + ANSI_RESET);
    }

    public void print21Message() {
        System.out.println(ANSI_GREEN + "21! Great job!" + ANSI_RESET);
    }

    public void printOutcome(int playerNumber, double payout, Hand playerHand, Hand dealerHand) {
        String result = payout > 0 ? "Win" : "Lose";
        System.out.println("Player " + playerNumber + " " + result + ": Payout $" + payout + " | Player hand value: " + playerHand.getTotalValue() + " | Dealer hand value: " + dealerHand.getTotalValue());
    }

    public void printGameOver() {
        System.out.println(ANSI_RED + "Game Over! You are out of balance." + ANSI_RESET);
    }

    public void printFinalResults(double initialBalance, double finalBalance) {
        System.out.println("Thank you for playing!");
        System.out.println("Your total profit/loss: $" + (finalBalance - initialBalance));
    }

    public void printPlayerTurnEnd(int playerNumber, Hand hand) {
        System.out.println("Player " + playerNumber + "'s turn is over. Here is their final hand: " + hand + " | Value: " + hand.getTotalValue() + " | Bet: " + ANSI_YELLOW + "$" + hand.getBet() + ANSI_RESET);
    }
}
