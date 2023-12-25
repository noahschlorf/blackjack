public class GameSetup {
    private int numberOfDecks;
    private int numberOfPlayers;
    private boolean dealerHitsSoft17;
    private double balance;
    private double initialBalance;

    public GameSetup(int numberOfDecks, int numberOfPlayers, boolean dealerHitsSoft17, double initialBalance) {
        this.numberOfDecks = numberOfDecks;
        this.numberOfPlayers = numberOfPlayers;
        this.dealerHitsSoft17 = dealerHitsSoft17;
        this.balance = initialBalance;
        this.initialBalance = initialBalance;
    }
    // getters and setters for setting up the game
    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    public void setNumberOfDecks(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isDealerHitsSoft17() {
        return dealerHitsSoft17;
    }

    public void setDealerHitsSoft17(boolean dealerHitsSoft17) {
        this.dealerHitsSoft17 = dealerHitsSoft17;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }
}
