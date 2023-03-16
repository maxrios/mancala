import java.util.List;

public class Board {
    public static final int DEFAULT_POCKETS = 14;
    private final Pocket[] pockets;

    public Board() {
        this.pockets = new Pocket[DEFAULT_POCKETS];
        for(int i = 0; i < DEFAULT_POCKETS; i++) {
            if (i == 6 || i == 13) {
                pockets[i] = new Mancala(i, (i == 6) ? Player.BOTTOM : Player.TOP);
            } else {
                pockets[i] = new Pocket(i);
            }
        }
    }

    public Pocket getPocket(int position) {
        return pockets[position];
    }

    public void setPocket(int position, Pocket pocket) {
        this.pockets[position] = pocket;
    }

    public Player hasEmptyRow() {
        if (pockets[0].isEmpty() &&
                pockets[1].isEmpty() &&
                pockets[2].isEmpty() &&
                pockets[3].isEmpty() &&
                pockets[4].isEmpty() &&
                pockets[5].isEmpty()) {
            return Player.BOTTOM;
        } else if (pockets[7].isEmpty() &&
                pockets[8].isEmpty() &&
                pockets[9].isEmpty() &&
                pockets[10].isEmpty() &&
                pockets[11].isEmpty() &&
                pockets[12].isEmpty()) {
            return Player.TOP;
        }
        return Player.NEITHER;
    }


    @Override
    public String toString() {
        String board =
                """
                      13   12   11   10   9    8
                     ┌─┐  ┌─┐  ┌─┐  ┌─┐  ┌─┐  ┌─┐
                     │%s│  │%s│  │%s│  │%s│  │%s│  │%s│
                     └─┘  └─┘  └─┘  └─┘  └─┘  └─┘
                  ┌%s┐                            ┌%s┐
               14 │%s│                            │%s│ 7
                  └%s┘                            └%s┘
                     ┌─┐  ┌─┐  ┌─┐  ┌─┐  ┌─┐  ┌─┐
                     │%s│  │%s│  │%s│  │%s│  │%s│  │%s│
                     └─┘  └─┘  └─┘  └─┘  └─┘  └─┘
                      1    2    3    4    5    6
                """;
        return String.format(
                board,
                // Top pockets
                pockets[12].getStones(),
                pockets[11].getStones(),
                pockets[10].getStones(),
                pockets[9].getStones(),
                pockets[8].getStones(),
                pockets[7].getStones(),
                // Mancalas
                (pockets[13].getStones() > 9) ? "──" : "─",
                (pockets[6].getStones() > 9) ? "──" : "─",
                pockets[13].getStones(),
                pockets[6].getStones(),
                (pockets[13].getStones() > 9) ? "──" : "─",
                (pockets[6].getStones() > 9) ? "──" : "─",
                // Bottom pockets
                pockets[0].getStones(),
                pockets[1].getStones(),
                pockets[2].getStones(),
                pockets[3].getStones(),
                pockets[4].getStones(),
                pockets[5].getStones());
    }
}
