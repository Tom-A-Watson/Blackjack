package blackjackpack;

import java.util.Scanner;
import java.util.Date;

/**
 This is the Blackjack class, which contains the main method of this package.The game logic/ 
 rules and lots of other cool add-ons for the game are written here :)
 */

public class Blackjack
{
	private static final double INITIAL_BALANCE = 500.00;

	/**
	 * This is the entry point to the Blackjack game.
	 * @param args The arguments to the program
	 */
	
	public static void main(String[] args) {
		Scanner playerInput = new Scanner(System.in);
		PlayerInput nextPlay = new PlayerInput();
		var today = new Date();                                         
		var time = today.getHours();                                    
		var timeOfDay = "";                                            
		
		timeOfDay = dealerGreeting(time);

		Deck deckOfCards = newDeck();                                      
		Hand playerHand = new Hand();		//Creates the player's hand
		Hand dealerHand = new Hand();       //Creates the dealer's hand

		boolean playerHasLeft = false;		//Becomes true when the player leaves (game loop breaks) 				
		double playerBalance = INITIAL_BALANCE;      //Player's default amount of money which can change
		double playerBet = 0;               //Player's bet which is 0 until they make a bet                                                                
								                                                              
		//Game loop
		//Player can continue playing until their balance drops to 0, or if they choose to leave
		while (playerBalance > 0 && !playerHasLeft) {
			System.out.println("You have £" + playerBalance + ", how much would you like to bet?");
			playerBet = playerInput.nextDouble();


			//Prevents players from betting more money than is in their balance
			if (playerBet > playerBalance) {
				System.out.println("You are not allowed to bet more money than you have! SECURITY!!");
				break;
			}

			//Boolean statement for deciding when it is appropriate to end the round & thus repeat the loop
			boolean endRound = false;

			//Dealing commences
			//Player is dealt 1 card, then the dealer is also dealt 1, this is repeated once.
			dealing(deckOfCards, playerHand, dealerHand);

			//Displays player's hand and hand value
			while (true) {
				showHand(playerHand);

				//Displays dealer's hand
				System.out.println("Dealers hand: " + dealerHand.getCard(0).toString() + " and [Hidden]");

				//Player is given a choice on what move to make
				System.out.println("Would you like to Hit[1] or Stand[2]?");             
				PlayerInput.Answer answer = nextPlay.getAnswer();


				//Player hits
				if (answer == PlayerInput.Answer.HIT) {
					playerHand.drawFrom(deckOfCards);
					System.out.println("\n" + "You drew a " + playerHand.getCard(playerHand.size()-1));

					//Bust if they hit & their hand value is > 21
					if (playerHand.cardValue() > 21) {
						System.out.println("Bust. Your hand's total value is " + playerHand.cardValue() + 
								". Unlucky, mate :/");
						playerBalance -= playerBet;
						endRound = true;
						break;
					}
				}

				//Player stands
				if (answer == PlayerInput.Answer.STAND) {
					break;
				}
			}
			//Reveal dealer's other card
			System.out.println("\n" + "Dealer's cards: " + dealerHand.toString());

			//If the dealer's hand is better than the player's, they win
			if (dealerHand.cardValue() > playerHand.cardValue() && endRound == false) {
				System.out.println("Dealer wins with a hand of " + dealerHand.cardValue() + " to " + playerHand.cardValue() + ". Better luck next time!");
				playerBalance -= playerBet;
				endRound = true;
			}

			//The dealer will always draw another card until they reach a hand value of 17
			while ((dealerHand.cardValue() < 17) && endRound == false) {
				dealerHand.drawFrom(deckOfCards);
				System.out.println("Dealer drew: " + dealerHand.getCard(dealerHand.size() -1).toString() + "\n");
			}

			//Displays dealer's total hand value
			System.out.println("Dealer's hand value: " + dealerHand.cardValue());

			//Determines if the dealer is bust
			if ((dealerHand.cardValue() > 21) && endRound == false) {
				playerBalance += playerBet;
				System.out.println("Dealer is bust with a total hand value of " + dealerHand.cardValue() +
						"! You win!");
				endRound = true;
			}

			//Splits the pot in the case that there is a push (a tie)
			if ((playerHand.cardValue() == dealerHand.cardValue()) && endRound == false) {
				System.out.println("Push! £" + playerBet + " is returned to the player's wallet :D");
				endRound = true;
			}

			var compliment = complimentFor(playerHand);

			//If the player has a better hand than the dealer, they win double their bet amount back
			if ((playerHand.cardValue() > dealerHand.cardValue()) && endRound == false) {
				playerBalance += playerBet;
				System.out.println("You win with a total hand value of " + playerHand.cardValue() +
						"!" + compliment);
				endRound = true;
			} else if (endRound == false) {
				System.out.println("You lose the round, since your hand is valued at " 
						+ playerHand.cardValue() + " :/");
				playerBalance -= playerBet;
				endRound = true;
			}

			//Ends the round by moving the players' and dealers' cards back to the deck
			int nextRoundAnswer = endOfRound(playerInput, deckOfCards, playerHand, dealerHand);

			if (nextRoundAnswer == 1) {				//if the answer is 1, repeat the
				endRound = true;                    //game loop
			} else if (nextRoundAnswer == 2) {
				System.out.println("\n" + "Fair enough, have a good rest of your " + timeOfDay + "!");
				playerInput.close();
				playerHasLeft = true;				//if the answer is 2, close the scanner (end the game)
			}                                       
		}

		//End-of-game statements relative to circumstance:
		if (!playerHasLeft) {                                                               
			if (playerBalance == 0){System.out.println("Game over. You are sadly out of money :(");}
			else if (playerBet > playerBalance){System.out.println("Game over. Security escorted you"
					+ " out of the building");}
		}
		
		if (playerBalance > INITIAL_BALANCE) {
			System.out.println("Good game, enjoy your winnings!");
		} else if (playerBalance < INITIAL_BALANCE) {
			System.out.println("Better luck next time. Come again soon!");
		}
	}

	/**
	 * Defines the sequence for drawing from a deck of cards, as dealt by a dealer
	 * @param deckOfCards to be dealt from
	 * @param playerHand the player to be dealt cards
	 * @param dealerHand the dealer to be dealt cards
	 */
	private static void dealing(Deck deckOfCards, Hand playerHand, Hand dealerHand) {
		playerHand.drawFrom(deckOfCards);
		dealerHand.drawFrom(deckOfCards);
		
		playerHand.drawFrom(deckOfCards);
		dealerHand.drawFrom(deckOfCards);
	}

	/**
	 * Dealer takes cards back and asks player if they would like to play another round
	 * @param playerInput the player's answer to the question
	 * @param deckOfCards the playing deck
	 * @param playerHand cards are returned to the deck
	 * @param dealerHand cards are returned to the deck
	 * @return the player's answer to the question
	 */
	private static int endOfRound(Scanner playerInput, Deck deckOfCards, Hand playerHand, Hand dealerHand) {
		playerHand.returnCardsToDeck(deckOfCards);
		dealerHand.returnCardsToDeck(deckOfCards);
		System.out.println("End of round. Would you like to play another?");            
		System.out.println("Yes[1] | No[2]");                                           
		int nextRoundAnswer = playerInput.nextInt();
		return nextRoundAnswer;
	}

	/**
	 * Displays all of the players cards and their hand value
	 * @param playerHand the cards to be displayed
	 */
	private static void showHand(Hand playerHand) {
		System.out.println("\n" + "Your hand:");
		System.out.println(playerHand.toString());
		System.out.println("Your total hand value is: " + playerHand.cardValue());
	}

	/**
	 * Creates a full deck of cards, and shuffles it
	 * @return the newly created deck of cards
	 */
	private static Deck newDeck() {
		return new Deck();
	}

	/**
	 * Returns a compliment for a players winning hand
	 * @param playerHand the cards in the player's hand
	 * @return the compliment for the given player hand
	 */
	private static String complimentFor(Hand playerHand) {
		var compliment = "";
		if (playerHand.cardValue() == 21) {                         
			compliment = " Spectacular!!";
		} else if (playerHand.cardValue() == 20) {
			compliment = " Fantastic!";
		} else if (playerHand.cardValue() == 19) {
			compliment = " Amazing!";
		} else {	//If player wins with a hand value of <= 18
			compliment = " Nice one :)";          
		}
		return compliment;
	}

	/**
	 * 
	 */
	private static String dealerGreeting(int time) {
		String timeOfDay;
		//Gives the dealer an appropriate greeting depending on the time of day that the game is played
		if (time < 12) {    //12pm (noon)
			timeOfDay = "morning";
		} else if (time < 20) {    //8pm 
			timeOfDay = "afternoon";
		} else {    //Any other times that are not included (8pm to midnight)
			timeOfDay = "evening";
		}

		//Greeting message from the dealer
		System.out.println("Good " + timeOfDay + ", and welcome to Blackjack!");
		return timeOfDay;
	}
}
