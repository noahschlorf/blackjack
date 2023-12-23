import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<String> cards;
    private boolean hasBlackjack;
    private double bet;

    public Hand() {
        this.cards = new ArrayList<>();
        this.hasBlackjack = false;
    }

    public void addCard(String card) {
        cards.add(card);
        checkForBlackjack();
    }

    public boolean removeCard(String card) {
        return cards.remove(card);
    }

    private void checkForBlackjack() {
        this.hasBlackjack = (cards.size() == 2 && getTotalValue() == 21);
    }

    public List<String> getCards() {
        return cards;
    }

    public boolean hasBlackjack() {
        return hasBlackjack;
    }

    public boolean canSplit() {
        if (cards.size() == 2) {
            String rank1 = cards.get(0).split(" ")[0];
            String rank2 = cards.get(1).split(" ")[0];
            return rank1.equals(rank2);
        }
        return false;
    }

    public boolean canDoubleDown() {
        return cards.size() == 2;
    }

    public void setBet(double betAmount) {
        this.bet = betAmount;
    }

    public double getBet() {
        return bet;
    }

    public void doubleBet() {
        this.bet *= 2;
    }

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

        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }

    public void clearHand() {
        cards.clear();
        hasBlackjack = false; 
    }

    public boolean hasAce() {
        for (String card : cards) {
            if (card.contains("Ace")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String status = "Hand: " + String.join(", ", cards) + " | Value: " + getTotalValue();
        
        if (hasBlackjack) {
            status += " (Blackjack!)";
        }
        status += " | Bet: $" + bet;
        if (canSplit()) {
            status += " | Can Split";
        }
        if (canDoubleDown()) {
            status += " | Can Double Down";
        }
        return status;
    }
}
