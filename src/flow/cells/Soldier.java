package flow.cells;

import flow.Player;

public class Soldier extends CellItem {
    public Soldier(Player owner) {
        super(owner, "Soldier");
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public int maxStep() {
        return 1;
    }

    @Override
    public boolean canMoveDiagonally() {
        return true;
    }

    @Override
    public int attackRange() {
        return 3;
    }
}
