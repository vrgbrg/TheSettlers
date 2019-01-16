package flow;

public class Position {
    public final int x;
    public final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Position && ((Position) obj).x == x && ((Position) obj).y == y;
    }
}
