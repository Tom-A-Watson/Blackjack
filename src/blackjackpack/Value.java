package blackjackpack;


/**
 * Enumeration class Value - This is where the values of the cards are defined and
 * initialised.
 * @author Tom Watson
 */
public enum Value
{
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), 
    EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(1);
    
    private int value;
    
    Value(int value) {
        this.value = value;
    }
    
    int getValue() {
        return value;
    }
}
