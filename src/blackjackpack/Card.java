package blackjackpack;


/**
 This is the Card class, which defines the attributes, and returned-value that
 a card posesses.
 */

public class Card
{
  
  private Suit suit;
  private Value value;
  
  //Defines that every card has a suit and value
  public Card(Suit suit, Value value) {
      this.value = value;
      this.suit = suit;
    }
    
  //Whatever the value & suit of the card is, return them with 'of' in the middle.
  //E.g. "SIX of DIAMONDS"
  public String toString() {
      return this.value.toString() + " of " + this.suit.toString();
    }
  
    
  public Value getValue() {
      return this.value;
    }
}
