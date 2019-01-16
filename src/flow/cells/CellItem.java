package flow.cells;

import flow.Player;

public abstract class CellItem {

    private final Player owner;
    private String title;

    CellItem(Player owner, String title) {
        this.owner = owner;
        this.title = title;
    }

    public abstract boolean isMovable();
    public abstract int maxStep();
    public abstract boolean canMoveDiagonally();
    public abstract int attackRange();

    public String getTitle() {
        return title;
    }

    public Player getOwner() {
        return owner;
    }

    public String toString() {
        return title.charAt(0) + "" + owner.getName().charAt(6);
    }
}
