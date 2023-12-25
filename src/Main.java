import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameSetup setup = new GameSetup(1, 3, false, 1000);

        PlayerInteraction interaction = new PlayerInteraction(scanner);
        GameDisplay display = new GameDisplay();
        BetManagement betManager = new BetManagement(setup);

        while (true) {
            GameRound round = new GameRound(setup, interaction, display, betManager);
            round.playRound();

            if (betManager.isOutOfBalance()) {
                display.printGameOver();
                break;
            }

            if (!interaction.askForAnotherRound()) {
                break;
            }
        }

        display.printFinalResults(setup.getInitialBalance(), betManager.getBalance());
        scanner.close();
    }
}
