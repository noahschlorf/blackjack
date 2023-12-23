import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private CardDeck combinedDeck;
    private Hand[] players;
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
        // draws 2 cards for all players to start the game
        players = new Hand[numberOfPlayers];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Hand();
            players[i].addCard(combinedDeck.drawCard());
            players[i].addCard(combinedDeck.drawCard());
        }
        // makes the dealers hand
        dealer = new Hand();
        dealer.addCard(combinedDeck.drawCard());
        dealer.addCard(combinedDeck.drawCard());
    }
    // returns a players hand
    public Hand getPlayerHand(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < players.length) {
            return players[playerIndex];
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

}
