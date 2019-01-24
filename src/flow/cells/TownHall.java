package flow.cells;

import flow.Player;

import javax.swing.*;
import java.awt.image.BufferedImage;


public class TownHall extends CellItem {
    public final int storageTownHall;

    public TownHall(Player owner) {
        super(owner, "TownHall",new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/townhall.png"));
        this.storageTownHall = 2000;

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

    @Override
    public int attackRange() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String toString() {
        return "TownHall{" +
                "storageTownHall=" + storageTownHall +
                '}';
    }
}

