package flow.cells;

import flow.Player;

import javax.swing.*;

public class TrainingRoom extends CellItem {
    private int price;

    public TrainingRoom(Player owner) {
        super(owner, "Kiképzőhely", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/townhall.png"));
        this.price = 400;
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
