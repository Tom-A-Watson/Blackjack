package blackjackpack;

import java.util.Scanner;

/**
 * This class's sole purpose is to allow for the integer '1' to be equal to hitting,
 * and the integer '2' to be equal to standing.
 * @author Tom Watson
 */
public class PlayerInput
{
    private Scanner answerInput = new Scanner(System.in);
    
    public enum Answer {
        HIT, STAND
    }
    
    public Answer getAnswer() {
        final String nextLine = answerInput.nextLine();
        if ("1".equalsIgnoreCase(nextLine) || "hit".equalsIgnoreCase(nextLine)) return Answer.HIT;
        else if ("2".equalsIgnoreCase(nextLine) || "stand".equalsIgnoreCase(nextLine)) return Answer.STAND;
        else {
            throw new IllegalArgumentException("That's not a valid answer!");
        }
    }
}
