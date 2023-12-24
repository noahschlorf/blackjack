import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
        int numberOfDecks = 1;
        int numberOfPlayers = 1;
        boolean dealerHitsSoft17 = false;
        double balance = 1000;

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
        for (int i = 0; i < numberOfPlayers; i++) {
            Hand playerHand = game.getPlayerHand(i);
            System.out.println("Player " + (i + 1) + "'s hand: " + playerHand);
            
            boolean turnEnded = false;
            if (playerHand.hasBlackjack()){
                System.out.println("Congrats, player " + (i + 1) + " has a blackjack");
                turnEnded = true;
            }
            while (!turnEnded) {
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
                        break;
                    case 2:
                        turnEnded = true; 
                        break;
                    case 3:
                        if (canDoubleDown) {
                            playerHand.doubleBet();
                            turnEnded = true;
                            break;
                        }
                        case 4:
                        if (canSplit) {
                            String splitCard = playerHand.removeCard();
                            Hand newHand = new Hand();
                            newHand.addCard(splitCard); 
                            game.hit(newHand);
                            game.hit(playerHand);
                            numberOfPlayers++; 
                            turnEnded = true; 
                        }
                        break;
                    
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                }
                if (!turnEnded) {
                    System.out.println("Player " + (i + 1) + "'s hand: " + game.getPlayerHand(i));
                }
            }

        }
        List<String> deckCards = game.getDeckCards();
        System.out.println("Deck Cards:");
        for (String card : deckCards) {
            System.out.println(card);
        }        scanner.close();

        game.dealerPlay();
        Hand dealerHand = game.getDealerHand();
        System.out.println("Dealer's final hand: " + dealerHand);
        
        for (int i = 0; i < numberOfPlayers; i++) {
            Hand playerHand = game.getPlayerHand(i);
            double betAmount = playerHand.getBet();
            double payout = 0;
        
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