package flow.cells;

import flow.Player;

import javax.swing.*;

public class Soldier extends CellItem {
    private int power;
    public Soldier(Player owner) {
        super(owner, "Soldier", new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/soldier.png"));
        this.power = 10;
    }

    public int getPower() {
        return power;
    }

    public int doublePower() {
        return power = power*2;
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
        return 5;
    }
}
