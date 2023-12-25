import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfDecks = 2;
        int numberOfPlayers = 3;
        boolean dealerHitsSoft17 = false;
        double balance = 1000;
        double initalBal = balance;
        while(true){
        // game start

        /* 
        System.out.print("Enter the number of decks: ");
        int numberOfDecks = scanner.nextInt();

        System.out.print("Enter the number of players: ");
        int numberOfPlayers = scanner.nextInt();

        System.out.println("Should the dealer hit on a soft 17? (yes/no): ");
        scanner.nextLine(); 
        String input = scanner.nextLine();
        boolean dealerHitsSoft17 = input.equalsIgnoreCase("yes");
        */

        GameLogic game = new GameLogic(numberOfDecks, numberOfPlayers, dealerHitsSoft17);

        for (int i = 0; i < numberOfPlayers; i++) {
            /*System.out.print("Enter the bet you would like to place for player " + (i + 1) + ": ");
            double betAmount = scanner.nextDouble();
            */
            double betAmount = 10;
            if (betAmount > 0 && betAmount <= balance) {
                game.getPlayerHand(i).setBet(betAmount);
                balance -= betAmount; 
            } else {
                System.out.println("Invalid bet amount for player " + (i + 1) + ". Bet must be greater than 0 and less than or equal to balance.");
            }
        }
        System.out.println("\u001B[32mBalance is: $" + balance + "\u001B[0m");
        // prints out the player hands and checks for blackjack
        printPlayerHandsAndCheckBlackjack(game, numberOfPlayers);
        // shows one dealer card
        System.out.println("\u001B[91mDealer's shown card: " + game.getDealerFirstCard() + "\u001B[0m");

        //starts the game
        int currentPlayer = 0;
        int i = 0;
        while (currentPlayer < game.getNumberOfPlayers()) {
            Hand playerHand = game.getPlayerHand(currentPlayer);
            boolean turnEnded = false;
            if (playerHand.hasBlackjack()){
                System.out.println("Congrats, player " + (i + 1) + " has a blackjack");
                turnEnded = true;
                currentPlayer++;
            }
            while (!turnEnded) {
                System.out.println("Player " + (i + 1) + "'s hand: " + playerHand);
                boolean canSplit = playerHand.canSplit();
                boolean canDoubleDown = playerHand.canDoubleDown();
                
                System.out.println("Player " + (i + 1) + ", choose an action:");
                System.out.println("1: Hit");
                System.out.println("2: Stand");
                if(canDoubleDown){
                    System.out.println("3: Double Down");
                }
                if (canSplit) {
                    System.out.println( "4: Split");
                }

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        game.hit(playerHand);
                        if(playerHand.getTotalValue()>21){
                            System.out.println("Sorry you have busted!");
                            turnEnded = true; 
                        }
                        if(playerHand.getTotalValue()==21){
                            System.out.println("\u001B[92m21!\u001B[0m");
                            turnEnded = true;
                        }
                        break;
                    case 2:
                        turnEnded = true; 
                        break;
                    case 3:
                        if (canDoubleDown) {
                            balance -= playerHand.getBet();
                            playerHand.setBet(playerHand.getBet()*2);
                            game.hit(playerHand);
                            turnEnded = true;
                            break;
                        }
                    case 4:
                        if (canSplit) {
                            String splitCard = playerHand.removeCard();
                            Hand newHand = new Hand();
                            newHand.addCard(splitCard); 
                            newHand.setBet(playerHand.getBet()); // Sets the players bet to the new hand
                            balance -= newHand.getBet();
                            game.hit(newHand); // Add a card to the new hand
                            game.hit(playerHand); // Add a card to the existing hand
                            game.addHandAfterCurrent(i, newHand); // Insert the new hand after the current hand
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                }
                System.out.println("Player " + (i + 1) + "'s hand: " + playerHand);
                if (turnEnded) {
                    currentPlayer++;
                }
            }
            i++;
        }

        /*List<String> deckCards = game.getDeckCards();
        System.out.println("Deck Cards:");
        for (String card : deckCards) {
            System.out.println(card);
        }        scanner.close();
        */
        game.dealerPlay();
        Hand dealerHand = game.getDealerHand();
        System.out.println("Dealer's final hand: " + dealerHand);
        
        for (i = 0; i < game.getNumberOfPlayers(); i++) {
            Hand playerHand = game.getPlayerHand(i);
            double betAmount = playerHand.getBet();
            System.out.println("Player " + (i+1) +" bet = " + playerHand.getBet());
            double payout = 0;
        
            // Calculate payout based on the outcome
            if (playerHand.hasBlackjack()) {
                if (dealerHand.hasBlackjack()) {
                    payout = betAmount;
                } else {
                    payout = betAmount + 1.5 * betAmount;
                }
            } else if (playerHand.getTotalValue() > 21) {
                payout = 0;
            } else if (dealerHand.getTotalValue() > 21 || playerHand.getTotalValue() > dealerHand.getTotalValue()) {
                payout = betAmount + betAmount;
            } else if (playerHand.getTotalValue() < dealerHand.getTotalValue()) {
                payout = 0;
            } else {
                payout = betAmount;
            }
        
            balance += payout;
            System.out.println("Player " + (i + 1) + " outcome: " + (payout > betAmount ? "Win" : "Lose"));
            System.out.println("Player " + (i + 1) + " new balance: $" + balance);
        }
        
        if (balance <= 0) {
            System.out.println("You are out of balance. Game over.");
            break;
        }

        System.out.println("Do you want to play another round? (yes/no): ");
        scanner.nextLine();
        String playAgain = scanner.nextLine();
        if (!playAgain.equalsIgnoreCase("yes")) {
            break;
        }
    }
        System.out.println("Thank you for playing!");
        System.out.println("Your total profit/loss was: $" + (balance-initalBal));
        scanner.close();
    }

    public static void printPlayerHandsAndCheckBlackjack(GameLogic game, int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            Hand playerHand = game.getPlayerHand(i);
            System.out.println("Player " + (i + 1) + "'s hand: " + playerHand);
            if (playerHand.hasBlackjack()) {
                System.out.println("Player " + (i + 1) + " has Blackjack!");
            }
        }
    }
}