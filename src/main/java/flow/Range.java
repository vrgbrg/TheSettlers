package flow;

public class Range {
    public final Position topLeft;
    public final Position bottomRight;

    public Range(Position topLeft, Position bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
}