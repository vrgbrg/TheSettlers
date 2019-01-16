package flow.cells;

import flow.Player;

public class LivingQuarters extends CellItem {
    private int price;
    public LivingQuarters(Player owner) {
        super(owner, "Szállás");
        this.price = 100;
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
