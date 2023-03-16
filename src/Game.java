import java.security.InvalidParameterException;
import java.util.Scanner;

public class Game {
    private static Scanner input = new Scanner(System.in);
    private static GameState game;

    public static void main(String[] args) {
        game = new GameState();
        int turn = 1;
        boolean bothPlayed = true;


        while(game.checkWin() == Player.NEITHER) {
            System.out.println(
                    String.format(
                            """
                            Round %s
                            %s %s
                            
                            %s
                                 
                            """
                    ,
                            turn,
                            game.getActiveTurn(),
                            ((game.hasExtraTurn()) ?
                                "(EXTRA TURN)" :
                                ""),
                            game.toString()));

            if (game.hasExtraTurn()) {
                game.completeExtraTurn();
            }
            takeTurn();
            if (!game.hasExtraTurn()) {
                swapTurn();
                bothPlayed = !bothPlayed;
            }

            if (!bothPlayed) {
                turn++;
            }

            System.out.println("───────────────────────────────────────");
        }

        System.out.println(game.toString());

        System.out.println(String.format("Woohoo! %s player won!", game.checkWin()));
        input.close();
    }

    private static void takeTurn() {
        int position = -1;
        boolean hasMoved = false;
        while(!hasMoved) {
            System.out.print("Pick a pocket: ");
            try {
                position = input.nextInt();
                game.movePocket(position-1);
                hasMoved = true;
            } catch (InvalidParameterException e) {
                System.out.println("Uh oh. That wasn't a valid pocket.");
            }
        }
    }

    private static void swapTurn() {
        game.setActiveTurn(
                (game.getActiveTurn() == Player.TOP) ?
                        Player.BOTTOM :
                        Player.TOP);
    }
}