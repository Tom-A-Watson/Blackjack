package blackjackpack;

import java.util.Scanner;
import java.util.Date;

/**
 This is the Blackjack class, which is the main method of this package.The game logic/ 
 rules and lots of other cool add-ons for the game are written here :)
 */

public class Blackjack
{
	public static void main(String[] args) {
		Scanner playerInput = new Scanner(System.in);
		PlayerInput nextPlay = new PlayerInput();
		var today = new Date();                                         
		var time = today.getHours();                                    
		var timeOfDay = "";                                            
		
		timeOfDay = printGreeting(time);

		Deck deckOfCards = new Deck();                                 
		deckOfCards.createFullDeck();                                   
		deckOfCards.shuffleDeck();                                      
		Deck playerHand = new Deck();				//Creates a hand for the player
		Deck dealerHand = new Deck();               //Creates a hand for the dealer

		
		boolean playerHasLeft = false; 				//Becomes true when the player leaves (game loop breaks) 
		double playerBalance = 500.00;              //Player's default amount of money which can change
		double playerBet = 0;                       //Player's bet which is defined as 0 until they make                                                                 
													//a bet                                                               
		//Game loop                                                    
		while (playerBalance > 0 && !playerHasLeft) {     //Player can continue playing as long as their balance is above 0
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
			playerHand.draw(deckOfCards);
			dealerHand.draw(deckOfCards);

			playerHand.draw(deckOfCards);
			dealerHand.draw(deckOfCards);

			//Displays player's hand and hand value
			while (true) {
				System.out.println("\n" + "Your hand:");
				System.out.println(playerHand.toString());
				System.out.println("Your total hand value is: " + playerHand.cardValue());

				//Displays dealer's hand
				System.out.println("Dealers hand: " + dealerHand.getCard(0).toString() + " and [Hidden]");

				//Player is given a choice on what move to make
				System.out.println("Would you like to Hit[1] or Stand[2]?");             
				PlayerInput.Answer answer = nextPlay.getAnswer();


				//Player hits
				if (answer == PlayerInput.Answer.HIT) {
					playerHand.draw(deckOfCards);
					System.out.println("\n" + "You drew a " + playerHand.getCard(playerHand.sizeOfDeck()-1));

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
				System.out.println("Dealer wins with a hand of " + dealerHand.cardValue() + " to " 
						+ playerHand.cardValue() + ". Better luck next time!");
				playerBalance -= playerBet;
				endRound = true;
			}

			//The dealer will always draw another card until they reach a hand value of 17
			while ((dealerHand.cardValue() < 17) && endRound == false) {
				dealerHand.draw(deckOfCards);
				System.out.println("Dealer drew: " + dealerHand.getCard(dealerHand.sizeOfDeck()
						-1).toString() + "\n");
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

			//If the player has a stronger hand than the dealer, they win double their bet amount back
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

			//Ends the round by moving the players' and dealers' hands back to the deck
			playerHand.moveHandsToDeck(deckOfCards);
			dealerHand.moveHandsToDeck(deckOfCards);
			System.out.println("End of round. Would you like to play another?");            
			System.out.println("Yes[1] | No[2]");                                           
			int nextRoundAnswer = playerInput.nextInt();

			if (nextRoundAnswer == 1) {				//if the answer is 1, repeat the
				endRound = true;                    //game loop
			} else if (nextRoundAnswer == 2) {
				System.out.println("\n" + "Fair enough, have a good rest of your " + timeOfDay + "!");
				playerInput.close();
				playerHasLeft = true;				//if the answer is 2, close the
			}                                       //scanner (end the game)
		}

		//End-of-game statements relative to circumstance:
		if (!playerHasLeft) {                                                               
			if (playerBalance == 0){System.out.println("Game over. You are sadly out of money :(");}
			else if (playerBet > playerBalance){System.out.println("Game over. Security escorted you"
					+ " out of the building");}
		}   
	}

	private static String complimentFor(Deck playerHand) {
		//Variable 'compliment' is left undefined as it will change depending on the player's hand value
		var compliment = "";

		//Different compliments relative to the 3 best possible hand values in the game
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

	private static String printGreeting(int time) {
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
