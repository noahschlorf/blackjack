import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<String> cards;
    private boolean hasBlackjack;
    private double bet;

    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_BLUE = "\u001B[34m";

    // used for keeping one players hand
    public Hand() {
        this.cards = new ArrayList<>();
        this.hasBlackjack = false;
    }
    
    public Hand(List<String> cards) {
        this.cards = new ArrayList<>(cards);
    }

    // adds a card to the hand and checks for blackjack
    public void addCard(String card) {
        cards.add(card);
        checkForBlackjack();
    }

    // removes a card from the players hand
    public String removeCard() {
        String removed = cards.remove(0);
        return removed;
    }

    // checks if the hand is a blackjack
    private void checkForBlackjack() {
        this.hasBlackjack = (cards.size() == 2 && getTotalValue() == 21);
    }

    // gets the cards in the hand
    public List<String> getCards() {
        return cards;
    }

    // returns true if there is a blackjack false otherwise
    public boolean hasBlackjack() {
        return hasBlackjack;
    }

    // determines if the player can split
    public boolean canSplit() {
        if (cards.size() == 2) {
            int rank1 = getValue(cards.get(0).split(" ")[0]);
            int rank2 = getValue(cards.get(1).split(" ")[0]);
            return rank1 == rank2;
        }
        return false;
    }

    public int getValue(String card) {
        String rank = card.split(" ")[0];
        switch (rank) {
            case "Ace":
                return 11;
            case "Jack":
            case "Queen":
            case "King":
                return 10;
            default:
                return Integer.parseInt(rank);
        }
    }

    // determines if the player can double down
    public boolean canDoubleDown() {
        return cards.size() == 2 && !hasBlackjack;
    }

    // used to set the bet size for a given hand
    public void setBet(double betAmount) {
        this.bet = betAmount;
    }

    // returns the current size of a bet for the players hand
    public double getBet() {
        return bet;
    }

    // used for double down
    public void doubleBet() {
        this.bet *= 2;
    }

    // returns the value of the current hand
    public int getTotalValue() {
        int totalValue = 0;
        int aceCount = 0;

        for (String card : cards) {
            String rank = card.split(" ")[0];
            switch (rank) {
                case "Ace":
                    aceCount++;
                    totalValue += 11;
                    break;
                case "Jack":
                case "Queen":
                case "King":
                    totalValue += 10;
                    break;
                default:
                    totalValue += Integer.parseInt(rank);
            }
        }
        // used to account for aces 10/1
        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }

    // clears the hand
    public void clearHand() {
        cards.clear();
        hasBlackjack = false;
    }

    // true if the current hand has an ace
    public boolean hasAce() {
        for (String card : cards) {
            if (card.contains("Ace")) {
                return true;
            }
        }
        return false;
    }

    // returns a string representation of the hand
    public String toString() {
        String status = String.join(", ", cards) + " | Value: " + ANSI_BLUE + getTotalValue() + ANSI_RESET;

        if (hasBlackjack) {
            status += ANSI_GREEN + " (Blackjack!)" + ANSI_RESET;
        }
        status += " | Bet: " + ANSI_YELLOW + "$" + bet + ANSI_RESET;
        return status;
    }
}
