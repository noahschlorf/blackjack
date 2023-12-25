import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private CardDeck combinedDeck;
    private List<Hand> players;
    private Hand dealer;
    private boolean dealerHitsSoft17;

    // sets up the game logic
    public GameLogic(int numberOfDecks, int numberOfPlayers, boolean dealerHitsSoft17) {
        this.dealerHitsSoft17 = dealerHitsSoft17;
        List<String> allDecksCombined = new ArrayList<>();
        // makes a "big" deck of cards given the input params
        for (int i = 0; i < numberOfDecks; i++) {
            CardDeck deck = new CardDeck();
            allDecksCombined.addAll(deck.getCards());
        }
        // combines the deck into one and shuffles
        combinedDeck = new CardDeck(allDecksCombined);
        combinedDeck.shuffle();
        // Initialize the players list and draw 2 cards for all players to start the
        // game
        players = new ArrayList<>(); // Initialized as ArrayList
        for (int i = 0; i < numberOfPlayers; i++) {
            Hand playerHand = new Hand();
            /*
             * playerHand.addCard("8 of Diamonds");
             * playerHand.addCard("8 of Hearts");
             */
            playerHand.addCard(combinedDeck.drawCard());
            playerHand.addCard(combinedDeck.drawCard());

            players.add(playerHand); // Add the hand to the list
        }
        // makes the dealer's hand
        dealer = new Hand();
        dealer.addCard(combinedDeck.drawCard());
        dealer.addCard(combinedDeck.drawCard());
    }

    // returns a player's hand
    public Hand getPlayerHand(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < players.size()) { // Use size() for List
            return players.get(playerIndex); // Use get() for List
        }
        return null;
    }

    // returns the dealers first card
    public String getDealerFirstCard() {
        return dealer.getCards().get(0);
    }

    // gets the dealers hand
    public Hand getDealerHand() {
        return dealer;
    }

    // draws a card from the deck
    public String drawCardFromDeck() {
        return combinedDeck.drawCard();
    }

    // logic used to play for the dealer
    public void dealerPlay() {
        while (dealer.getTotalValue() < 17 ||
                (dealerHitsSoft17 && dealer.getTotalValue() == 17 && dealer.hasAce())) {
            dealer.addCard(drawCardFromDeck());
        }
    }
    // adds a card to the players hand
    public void hit(Hand hand) {
        hand.addCard(drawCardFromDeck());
    }
    // returns the dark of cards
    public List<String> getDeckCards() {
        return combinedDeck.getCards();
    }
    // gets a players index properly 
    public void addHandAfterCurrent(int currentPlayerIndex, Hand newHand) {
        players.add(currentPlayerIndex + 1, newHand);
    }
    // retunrs how many players are in the game
    public int getNumberOfPlayers() {
        return players.size();
    }

}
