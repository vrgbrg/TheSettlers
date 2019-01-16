package flow.cells;

import flow.Player;

public class TownHall extends CellItem {
    public TownHall(Player owner) {

        super(owner, "Városháza");
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public int maxStep() {
        return 0;
    }

    @Override
    public boolean canMoveDiagonally() {
        return true;
    }

    @Override
    public int attackRange() {
        return 0;
    }
}

