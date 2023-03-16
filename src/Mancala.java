public class Mancala extends Pocket {
    private final Player owner;

    public Mancala(int position, Player owner) {
        this(position, 0, owner);
    }
    public Mancala(int position, int stones, Player owner) {
        super(position, stones);
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
