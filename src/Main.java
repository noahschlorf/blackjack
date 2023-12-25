import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GameSetup setup = new GameSetup(1, 3, false, 1000);

        PlayerInteraction interaction = new PlayerInteraction(scanner);
        GameDisplay display = new GameDisplay();
        BetManagement betManager = new BetManagement(setup);

        // game loop
        while (true) {
            GameRound round = new GameRound(setup, interaction, display, betManager);
            // plays the game
            round.playRound();
            // if the player is out of money the game is over
            if (betManager.isOutOfBalance()) {
                display.printGameOver();
                break;
            }
            // sees if the player wants to play agian
            if (!interaction.askForAnotherRound()) {
                break;
            }
        }
        // shows the final results
        display.printFinalResults(setup.getInitialBalance(), betManager.getBalance());
        scanner.close();
    }
}
