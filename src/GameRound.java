public class GameRound {
    private GameSetup setup;
    private PlayerInteraction interaction;
    private GameDisplay display;
    private BetManagement betManager;
    private GameLogic game;

    public GameRound(GameSetup setup, PlayerInteraction interaction, GameDisplay display, BetManagement betManager) {
        this.setup = setup;
        this.interaction = interaction;
        this.display = display;
        this.betManager = betManager;
        this.game = new GameLogic(setup.getNumberOfDecks(), setup.getNumberOfPlayers(), setup.isDealerHitsSoft17());
    }

    public void playRound() {
        // Set bets for each player
        setPlayerBets();

        // Display player hands and dealer's first card
        displayPlayerHands();
        displayDealerFirstCard();

        // Player turns
        for (int i = 0; i < game.getNumberOfPlayers(); i++) {
            playPlayerTurn(i);
        }

        // Dealer's turn
        game.dealerPlay();
        display.printDealerHand(game.getDealerHand());

        // Calculate and display outcomes
        calculateOutcomes();
    }
    //used to set up the player bets
    private void setPlayerBets() {
        for (int i = 0; i < setup.getNumberOfPlayers(); i++) {
            double betAmount = interaction.getBetAmount(i, betManager.getBalance());
            game.getPlayerHand(i).setBet(betAmount);
            betManager.adjustBalance(-betAmount);
        }
    }
    // prints the players hadns
    private void displayPlayerHands() {
        for (int i = 0; i < game.getNumberOfPlayers(); i++) {
            Hand playerHand = game.getPlayerHand(i);
            display.printPlayerHand(i + 1, playerHand);
        }
    }
    // shows the dealers first card
    private void displayDealerFirstCard() {
        display.printDealerFirstCard(game.getDealerFirstCard());
    }
    // used for playing a players turn
    private void playPlayerTurn(int playerIndex) {
        
        Hand playerHand = game.getPlayerHand(playerIndex);
        CardDeck remainingDeck = game.getCardDeck();
        boolean turnEnded = false;
        
        display.printPlayerHand(playerIndex + 1, playerHand);
        display.printExpectedTotalAfterHit(playerHand, remainingDeck);

        if (playerHand.hasBlackjack()) {
            display.printBlackjackMessage(playerIndex + 1);
            turnEnded = true;
        }
        // actual game loop
        while (!turnEnded) {
            //display.printRemainingDeck(remainingDeck);
            display.printPlayerOptions(playerHand, playerIndex + 1);
            int choice = interaction.getPlayerChoice(playerHand);
            // gives the player the options available 
            switch (choice) {
                case 1: // Hit
                    game.hit(playerHand);
                    turnEnded = checkBustOr21(playerHand);
                    break;
                case 2: // Stand
                    turnEnded = true;
                    break;
                case 3: // Double Down
                    if (playerHand.canDoubleDown()) {
                        double currentBet = playerHand.getBet();
                        if (betManager.adjustBalanceForDoubleDown(currentBet)) {
                            playerHand.doubleBet();
                            game.hit(playerHand);
                            checkBustOr21(playerHand);
                            turnEnded = true;
                        } else {
                            display.printInsufficientBalanceForDoubleDown();
                        }
                    }
                    break;
                case 4: // Split
                    if (playerHand.canSplit()) {
                        performSplit(playerIndex, playerHand);
                    }
                    break;
                default:
                    display.printInvalidChoiceMessage();
            }
            if (!turnEnded) {
                display.printPlayerHand(playerIndex + 1, playerHand);
            }

        }
        // Here
        display.printPlayerTurnEnd(playerIndex + 1, playerHand);
    }
    // does the split operation by addint a new hand a pop a card
    private void performSplit(int playerIndex, Hand originalHand) {
        String splitCard = originalHand.removeCard();
        Hand newHand = new Hand();
        newHand.addCard(splitCard);
        newHand.setBet(originalHand.getBet());

        if (betManager.adjustBalanceForSplit(newHand.getBet())) {
            game.hit(newHand); // Add a card to the new hand
            game.hit(originalHand); // Add a card to the existing hand
            game.addHandAfterCurrent(playerIndex, newHand); // Insert the new hand after the current hand
        } else {
            display.printInsufficientBalanceForSplit();
            // Reverting the split due to insufficient balance
            originalHand.addCard(splitCard);
        }
    }
    // cchecks if player has busted or has 21
    private boolean checkBustOr21(Hand hand) {
        if (hand.getTotalValue() > 21) {
            display.printBustMessage();
            return true;
        } else if (hand.getTotalValue() == 21) {
            display.print21Message();
            return true;
        }
        return false;
    }
    // calculates the outcome of all the games
    private void calculateOutcomes() {
        Hand dealerHand = game.getDealerHand();
        for (int i = 0; i < game.getNumberOfPlayers(); i++) {
            Hand playerHand = game.getPlayerHand(i);
            double payout = betManager.calculatePayout(playerHand, dealerHand);
            betManager.adjustBalance(payout);
            display.printOutcome(i + 1, payout, playerHand, dealerHand);
        }
    }
}
