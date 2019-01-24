package flow.cells;

import flow.Player;

import javax.swing.*;

public class Bank extends CellItem {
    private int price;
    public Bank(Player owner) {

        super(owner, "Bank", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/bank.png"));
        this.price = 700;
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
        return "Bank{" +
                "price=" + price +
                '}';
    }
}