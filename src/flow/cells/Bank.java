package flow.cells;

import flow.Player;

public class Bank extends CellItem {
    private int price;
    public Bank(Player owner) {

        super(owner, "Bank");
        this.price = 700;
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
        return false;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int attackRange() {
        return 0;
    }
}