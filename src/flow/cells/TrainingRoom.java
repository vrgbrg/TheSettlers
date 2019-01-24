package flow.cells;

import flow.Player;

import javax.swing.*;

public class TrainingRoom extends CellItem {
    private int price;

    public TrainingRoom(Player owner) {
        super(owner, "Training room", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/trainingroom.png"));
        this.price = 400;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean canMoveDiagonally() {
        return false;
    }

    @Override
    public int maxStep() {
        return 0;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int attackRange() {
        return 0;
    }

    @Override
    public String toString() {
        return "TrainingRoom{" +
                "price=" + price +
                '}';
    }
}
