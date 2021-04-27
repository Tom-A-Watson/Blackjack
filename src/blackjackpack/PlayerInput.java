package blackjackpack;

import java.util.Scanner;

/**
 * Write a description of class nextPLay here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PlayerInput
{
    private Scanner answerInput = new Scanner(System.in);
    
    public enum Answer {
        HIT, STAND
    }
    
    public Answer getAnswer() {
        final String nextLine = answerInput.nextLine();
        if ( "1".equalsIgnoreCase(nextLine) || "hit".equalsIgnoreCase(nextLine)) return Answer.HIT;
        else if ( "2".equalsIgnoreCase(nextLine) || "stand".equalsIgnoreCase(nextLine)) return Answer.STAND;
        else {
            throw new IllegalArgumentException("That's not a valid answer!");
        }
    }
}
