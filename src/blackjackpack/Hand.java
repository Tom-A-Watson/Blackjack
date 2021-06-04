package blackjackpack;

import java.util.ArrayList;
import java.util.List;

/**
 * List of cards in the player's or dealer's hand.
 * @author Tom Watson
 */
public class Hand {
	private List<Card> cards = new ArrayList<>();

	/**
	 * Draws a single card from the given deck of cards.
	 * @param deckOfCards the deck from which the card will be taken.
	 */
	public void drawFrom(Deck deckOfCards) {
		cards.add(deckOfCards.draw());
	}

	/**
	 * Returns the total value of the cards in the player's or dealer's hand.
	 * @return the total value of a given hand.
	 */
	public int cardValue() {
		int totalValue = 0;
		int aceCard = 0;

		//Defines the numerical value of each card, with conditions for the Ace, since it can be 1 or 11
		for (Card aCard : this.cards) {
			switch (aCard.getValue()){
			case ACE: aceCard += 1; break;
			default : totalValue += aCard.getValue().getValue();
			}
		}

		//Simple for loop to specify when the Ace should have a value of 1 or 11
		for (int i = 0; i < aceCard; i++) {
			if (totalValue > 10) {                   
				totalValue += 1;                                        //If the total value of the players hand is greater
				//than 10, the value of the Ace is 1
			} else {
				totalValue += 11;                                       //Otherwise, it is 11
			}
		}
		return totalValue;
	}

	/**
	 * Gets a card from a players hand at a given index.
	 * @param index of the card to be shown.
	 * @return the card at the given index.
	 */
	public Card getCard(int index) {
		return this.cards.get(index);
	}

	/**
	 * Returns the number of cards in the player's hand.
	 * @return the size of the hand.
	 */
	public int size() {
		return this.cards.size();
	}
	
	/**
	 * Moves both the players' and dealers' hands back into the deck post-round
	 * @param deck 
	 */
    public void returnCardsToDeck(Deck deck) {
    	while ( !cards.isEmpty() ) {
    		deck.addCard(cards.remove(0));
    	}
    }
    
    /**
     * Loop that returns a list of all cards in the players hand on new lines
     * @return the cards in a given hand.
     */
    public String toString() {
        String cardsInPlayerDeck = "";
        for (Card card : this.cards) {
            cardsInPlayerDeck += card.toString() + "\n";
        }
        return cardsInPlayerDeck;
    }
}
