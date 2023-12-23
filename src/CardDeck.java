import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    private List<String> cards;

    public CardDeck() {
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    public CardDeck(List<String> cards) {
        this.cards = new ArrayList<>(cards);
    }

    private void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(rank + " of " + suit);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<String> getCards() {
        return this.cards;
    }

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
