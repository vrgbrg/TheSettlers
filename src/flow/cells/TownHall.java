package flow.cells;

import flow.Player;

import javax.swing.*;
import java.awt.image.BufferedImage;


public class TownHall extends CellItem {

    public TownHall(Player owner) {
        super(owner, "Városháza",new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/townhall.png"));

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

