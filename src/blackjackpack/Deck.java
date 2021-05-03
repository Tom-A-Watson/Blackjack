package blackjackpack;

import java.util.ArrayList;
import java.util.Random;

/**
 This is the Deck class, which defines the number of cards in a standard deck
 which is 4 sets of every value (1 set for each suit), and also includes
 the method for shuffling, burning (removing) a card, 
 */
public class Deck
{
    //The instance variable is an ArrayList of multiple cards 
    private ArrayList<Card> deckCards;
    
    /**
     * Constructor method that creates a full deck of cards
     */
    public Deck() {
        this.deckCards = new ArrayList<Card>(); 
        for (Suit cardSuit : Suit.values()) {
            for (Value cardValue : Value.values()) {                    //Adds a new card
                this.deckCards.add(new Card(cardSuit, cardValue));  	
                                                                        //for every suit that there is (4 * 13)
            }
        }
        shuffle();
    }
    
    /**
     * Method that shuffles the deck
     */
    private void shuffle() {
        ArrayList<Card> tempDeck = new ArrayList<Card>();               
        Random random = new Random();                                   
        int randomCardIndex = 0;                                        
        int noOfCards = this.deckCards.size();                      
                                                                        
        //Sequence for generating a random number of cards to be removed
        for (int i = 0; i < noOfCards; i++) {
            randomCardIndex = random.nextInt((this.deckCards.size()-1 - 0) + 1) + 0;
            tempDeck.add(this.deckCards.remove(randomCardIndex));               
        }
        this.deckCards = tempDeck;
    }
    
    /**
     * Returns a list of all cards in the players hand to strings
     */
    public String toString() {
        String cardsInPlayerDeck = "";
        for (Card aCard : this.deckCards) {
            cardsInPlayerDeck += aCard.toString() + "\n";
        }
        return cardsInPlayerDeck;
    }
    
    /**
     * Removes an index of cards from the original deck
     * @param i - index of cards to be removed
     */
    public void removeCard(int i) {
        this.deckCards.remove(i);
    }
    
    /**
     * Adds a card to the original deck
     * @param addCard
     */
    public void addCard(Card addCard) {
        this.deckCards.add(addCard);
    }
    
    /**
     * Draws a card from the deck.
     * @return the card drawn.
     */
    public Card draw() {
        return deckCards.remove(0);
    }
}
