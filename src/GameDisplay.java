import java.util.List;

public class GameDisplay {

    // ANSI color codes for styling the console output
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[91m";

    public GameDisplay() {
    }

    public void printPlayerHand(int playerNumber, Hand hand) {
        System.out.println("Player " + playerNumber + "'s hand: " + hand);
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
        String result;
    
        if (playerHand.getTotalValue() > 21) {
            result = "Lose"; // Player busts
        } else if (dealerHand.getTotalValue() > 21) {
            result = "Win"; // Dealer busts
        } else if (playerHand.getTotalValue() > dealerHand.getTotalValue()) {
            result = "Win"; // Higher value without busting
        } else if (playerHand.getTotalValue() < dealerHand.getTotalValue()) {
            result = "Lose"; // Lower value without busting
        } else {
            result = "Tie"; // Equal values
        }
    
        System.out.println("Player " + playerNumber + " " + result + ": Payout $" + payout 
                           + " | Player hand value: " + playerHand.getTotalValue() 
                           + " | Dealer hand value: " + dealerHand.getTotalValue());
    }
    
    

    public void printGameOver() {
        System.out.println(ANSI_RED + "Game Over! You are out of balance." + ANSI_RESET);
    }

    public void printFinalResults(double initialBalance, double finalBalance) {
        System.out.println("Thank you for playing!");
        System.out.println("Your total profit/loss: $" + (finalBalance - initialBalance));
    }

    public void printPlayerTurnEnd(int playerNumber, Hand hand) {
        System.out.println("Player " + playerNumber + "'s turn is over. Here is their final hand: " + hand);
    }

    public void printExpectedTotalAfterHit(Hand playerHand, CardDeck remainingDeck) {
        double expectedTotal = GameLogic.calculateEV(playerHand, remainingDeck);
        System.out.println("Expected Total Value After a Hit: " + String.format("%.2f", expectedTotal));
    }

    public void printRemainingDeck(CardDeck remainingDeck) {
        List<String> remainingCards = remainingDeck.getCards();
        System.out.println("Remaining cards in the deck:");
        for (String card : remainingCards) {
            System.out.println(card);
        }
    }
}
