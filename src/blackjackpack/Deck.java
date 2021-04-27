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
    //The instance variable is an ArrayList of multiple cards, thus the name 'multipleCards' 
    private ArrayList<Card> deckCards;
    
    //Constructor method
    public Deck() {
        this.deckCards = new ArrayList<Card>(); 
        for (Suit cardSuit : Suit.values()) {
            for (Value cardValue : Value.values()) {                    //Adds a new card
                this.deckCards.add(new Card(cardSuit, cardValue));  	//Every new card that is added contains a value and
                                                                        //a suit
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
                                                                        
        //Sequence for generating a random number
        for (int i = 0; i < noOfCards; i++) {
            randomCardIndex = random.nextInt((this.deckCards.size()-1 - 0) + 1) + 0;
            tempDeck.add(this.deckCards.get(randomCardIndex));      
            this.deckCards.remove(randomCardIndex);                 
        }
        this.deckCards = tempDeck;
    }
    
    //Loop that returns a list of all cards in the players hand on new lines
    public String toString() {
        String cardsInPlayerDeck = "";
        for (Card aCard : this.deckCards) {
            cardsInPlayerDeck += aCard.toString() + "\n";
        }
        return cardsInPlayerDeck;
    }
    
    //Removes 1 card from the shuffled deck before dealing
    public void burnCard(int i) {
        this.deckCards.remove(i);
    }
    
    
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
