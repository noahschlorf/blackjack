import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private List<String> cards;
    // Constructor for the game deck
    public CardDeck() {
        this.cards = new ArrayList<>();
        initializeDeck();
    }
    
    public CardDeck(List<String> cards) {
        this.cards = new ArrayList<>(cards);
    }
    // used to initalize the deck
    private void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(rank + " of " + suit);
            }
        }
    }
    // shuffles the deck of cards
    public void shuffle() {
        Collections.shuffle(cards);
    }
    // gets all the cards from a said deck
    public List<String> getCards() {
        return this.cards;
    }
    // draws a card off the top of the deck and removes it from the deck
    public String drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }
    public String toString() {
        return String.join("\n", cards);
    }
}
