public class Pocket {
    public static final int DEFAULT_STONES = 4;

    private final int position;
    private int stones;


    public Pocket(int position) {
        this.position = position;
        this.stones = DEFAULT_STONES;
    }

    public Pocket(int position, int stones) {
        this.position = position;
        this.stones = stones;
    }

    public int getStones() {
        return stones;
    }

    public boolean isEmpty() {
        return stones == 0;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }

    public int getPosition() {
        return position;
    }
}
