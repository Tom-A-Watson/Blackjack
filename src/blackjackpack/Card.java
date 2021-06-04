package blackjackpack;


/**
 * This is the Card class, which defines the attributes, and returned-value that
 * a card possesses.
 * @author Tom Watson
 */
public class Card
{
  
  private Suit suit;
  private Value value;
  
  /**
   * Card constructor that defines that every card has a suit and value
   * @param suit - the possible suit of the card, using 'Suit' e.g. SPADES
   * @param value - the possible value of the card e.g. FOUR
   */
  public Card(Suit suit, Value value) {
      this.value = value;
      this.suit = suit;
  	}
    
  /**
   * toString method which converts the value and suit to a string, 
   * with the word 'of' in the middle, to make it more realistic
   * E.g. SIX of DIAMONDS
   */
  public String toString() {
      return this.value.toString() + " of " + this.suit.toString();
    }
  
  /**
   * getValue method which gets the actual, numerical value of the card
   * so that hand values can be calculated in the game
   * @return the numerical value of the given card
   */
  public Value getValue() {
      return this.value;
    }
}
