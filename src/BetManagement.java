public class BetManagement {
    private double balance;
    // gets the balance from the hand
    public BetManagement(GameSetup setup) {
        this.balance = setup.getInitialBalance();
    }
    // gets the balance
    public double getBalance() {
        return balance;
    }
    // adds the amoutn to balance to adjust for betting/wins/loses
    public void adjustBalance(double amount) {
        balance += amount;
    }
    //doubles bet if possible for doubling down
    public boolean adjustBalanceForDoubleDown(double currentBet) {
        if (currentBet * 2 <= balance) {
            adjustBalance(-currentBet);
            return true;
        }
        return false;
    }
    //adjusts for split if possible
    public boolean adjustBalanceForSplit(double betAmount) {
        if (betAmount <= balance) {
            adjustBalance(-betAmount);
            return true;
        }
        return false;
    }
    // calcualtes the payout of a given hand
    public double calculatePayout(Hand playerHand, Hand dealerHand) {
        double betAmount = playerHand.getBet();
        if (playerHand.hasBlackjack()) {
            return dealerHand.hasBlackjack() ? 0 : ((betAmount * 1.5) + betAmount);
        } else if (playerHand.getTotalValue() > 21) {
            return 0;
        } else if (dealerHand.getTotalValue() > 21 || playerHand.getTotalValue() > dealerHand.getTotalValue()) {
            return betAmount * 2;
        } else if (playerHand.getTotalValue() < dealerHand.getTotalValue()) {
            return 0;
        } else {
            return betAmount;
        }
    }
    // returns false if balance is 0 or less
    public boolean isOutOfBalance() {
        return balance <= 0;
    }
}
