import java.security.InvalidParameterException;

public class GameState {
    private final Board board;
    private Player activeTurn;
    private boolean hasExtraTurn;

    public GameState() {
        this.activeTurn = Player.BOTTOM;
        this.board = new Board();
        this.hasExtraTurn = false;
    }

    public Player getActiveTurn() {
        return activeTurn;
    }

    public void setActiveTurn(Player activeTurn) {
        this.activeTurn = activeTurn;
    }

    public void movePocket(int position) {
        if (position > 12 ||
                position < 0 ||
                position == 6 ||
                (activeTurn == Player.TOP && position < 7) ||
                (activeTurn == Player.BOTTOM && position > 5) ||
                board.getPocket(position).isEmpty()) {
            throw new InvalidParameterException("Invalid pocket position.");
        }
        int stoneCount = board.getPocket(position).getStones();
        board.setPocket(position, new Pocket(position, 0));

        int index = position+1;
        int lastPosition = -1;
        while(stoneCount >= 1) {
            if (index > 13) {
                index = 0;
            }
            Pocket currPocket = board.getPocket(index);
            if (currPocket instanceof Mancala currMancala) {
                if (currMancala.getOwner() == activeTurn) {
                    updatePocket(index, currMancala);
                } else {
                    // Skip mancala if not owned and play on next pocket
                    index++;
                    currPocket = board.getPocket(index);
                    updatePocket(index, currPocket);
                }
            } else {
                updatePocket(index, currPocket);
            }
            stoneCount--;
            lastPosition = index++;
        }

        if (lastPosition == 6 || lastPosition == 13) {
            hasExtraTurn = true;
        }

        // We use 12 instead of 14 because we're skipping mancalas
        int sisterPosition = 12-lastPosition;
        if (board.getPocket(lastPosition).getStones()-1 == 0 &&
                withinActiveTurnBounds(lastPosition) &&
                !board.getPocket(sisterPosition).isEmpty()) {
            int sum = 1;
            int playerMancalaIndex = (activeTurn == Player.TOP) ? 13 : 6;

            board.setPocket(lastPosition, new Pocket(lastPosition, 0));
            sum += board.getPocket(sisterPosition).getStones();
            board.setPocket(sisterPosition, new Pocket(sisterPosition, 0));
            sum += board.getPocket(playerMancalaIndex).getStones();
            board.setPocket(playerMancalaIndex, new Mancala(playerMancalaIndex, sum, activeTurn));
        }
    }

    public boolean hasExtraTurn() {
        return hasExtraTurn;
    }

    public void completeExtraTurn() {
        hasExtraTurn = false;
    }

    public Player checkWin() {
        Player player = board.hasEmptyRow();
        if (player == Player.NEITHER) {
            return Player.NEITHER;
        }
        sumMancala(Player.TOP);
        sumMancala(Player.BOTTOM);
        int topMancalaScore = board.getPocket(13).getStones();
        int bottomMancalaScore = board.getPocket(6).getStones();

        if (topMancalaScore > bottomMancalaScore) {
            return Player.TOP;
        } else if (topMancalaScore < bottomMancalaScore) {
            return Player.BOTTOM;
        } else {
            return Player.BOTH;
        }
    }

    private boolean withinActiveTurnBounds(int position) {
        return switch (activeTurn) {
            case TOP -> position <= 12 && position >= 7;
            case BOTTOM -> position <= 5 && position >= 0;
            default -> false;
        };
    }

    private void sumMancala(Player player) {
        int sum = 0;
        int start = (player == Player.BOTTOM) ? 0 : 7;
        int mancalaIndex = (player == Player.BOTTOM) ? 6 : 13;
        for (int i = start; i < start+6; i++) {
            sum += board.getPocket(i).getStones();
            board.setPocket(i, new Pocket(i, 0));
        }
        board.setPocket(
                mancalaIndex,
                new Mancala(
                        mancalaIndex,
                        board.getPocket(mancalaIndex).getStones() + sum,
                        player));
    }

    private void updatePocket(int position, Pocket pocket) {
        if (pocket instanceof Mancala) {
            board.setPocket(
                    position,
                    new Mancala(
                            position,
                            pocket.getStones()+1,
                            activeTurn));
        } else {
            board.setPocket(
                    position,
                    new Pocket(
                            position,
                            pocket.getStones()+1));
        }
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
