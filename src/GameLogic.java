import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private CardDeck combinedDeck;
    private Hand[] players;
    private Hand dealer;  
    private boolean dealerHitsSoft17;

    public GameLogic(int numberOfDecks, int numberOfPlayers, boolean dealerHitsSoft17) {
        this.dealerHitsSoft17 = dealerHitsSoft17;
        List<String> allDecksCombined = new ArrayList<>();

        for (int i = 0; i < numberOfDecks; i++) {
            CardDeck deck = new CardDeck();
            allDecksCombined.addAll(deck.getCards());
        }

        combinedDeck = new CardDeck(allDecksCombined);
        combinedDeck.shuffle();

        players = new Hand[numberOfPlayers];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Hand();
            players[i].addCard(combinedDeck.drawCard());
            players[i].addCard(combinedDeck.drawCard());
        }
        dealer = new Hand();
        dealer.addCard(combinedDeck.drawCard());
        dealer.addCard(combinedDeck.drawCard());
    }

    public Hand getPlayerHand(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < players.length) {
            return players[playerIndex];
        }
        return null;
    }

    public String getDealerFirstCard() {
        return dealer.getCards().get(0);
    }

    public Hand getDealerHand() {
        return dealer;
    }

    public String drawCardFromDeck() {
        return combinedDeck.drawCard();
    }

    public void dealerPlay() {
        while (dealer.getTotalValue() < 17 || 
               (dealerHitsSoft17 && dealer.getTotalValue() == 17 && dealer.hasAce())) {
            dealer.addCard(drawCardFromDeck());
        }
    }

}
