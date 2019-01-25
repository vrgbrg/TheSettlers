package flow.cells;

import flow.Player;

import javax.swing.*;
import java.awt.*;

public abstract class CellItem {

    private final Player owner;
    private String title;
    private ImageIcon image;

    CellItem(Player owner, String title, ImageIcon image) {
        this.owner = owner;
        this.title = title;
        this.image = image;
    }

    public abstract boolean isMovable();
    public abstract boolean canMoveDiagonally();
    public abstract int maxStep();
    public abstract int attackRange();
    public abstract int getPrice();

    public String getTitle() {
        return title;
    }

    public Player getOwner() {
        return owner;
    }

    public ImageIcon layoutImage() {
        return image;
    }

    public String toString() {
        return title.charAt(0) + "" + owner.getName().charAt(0);
    }
}
