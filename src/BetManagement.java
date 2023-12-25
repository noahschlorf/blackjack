public class BetManagement {
    private double balance;

    public BetManagement(GameSetup setup) {
        this.balance = setup.getInitialBalance();
    }

    public double getBalance() {
        return balance;
    }

    public void adjustBalance(double amount) {
        balance += amount;
    }

    public boolean adjustBalanceForDoubleDown(double currentBet) {
        if (currentBet * 2 <= balance) {
            adjustBalance(-currentBet);
            return true;
        }
        return false;
    }

    public boolean adjustBalanceForSplit(double betAmount) {
        if (betAmount <= balance) {
            adjustBalance(-betAmount);
            return true;
        }
        return false;
    }

    public double calculatePayout(Hand playerHand, Hand dealerHand) {
        double betAmount = playerHand.getBet();
        if (playerHand.hasBlackjack()) {
            return dealerHand.hasBlackjack() ? 0 : ((betAmount * 1.5)  + betAmount);
        } else if (playerHand.getTotalValue() > 21) {
            return 0;
        } else if (dealerHand.getTotalValue() > 21 || playerHand.getTotalValue() > dealerHand.getTotalValue()) {
            return betAmount*2;
        } else if (playerHand.getTotalValue() < dealerHand.getTotalValue()) {
            return 0;
        } else {
            return betAmount;
        }
    }

    public boolean isOutOfBalance() {
        return balance <= 0;
    }
}
