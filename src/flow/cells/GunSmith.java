package flow.cells;

import flow.Player;

import javax.swing.*;

public class GunSmith extends CellItem {
    private int price;
    public GunSmith(Player owner) {

        super(owner, "Gun Smith", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/gunsmith.png"));
        this.price = 300;
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
        return "GunSmith{" +
                "price=" + price +
                '}';
    }
}
