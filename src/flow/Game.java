package flow;

import flow.cells.CellItem;
import flow.cells.TownHall;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();
    private CellItem[][] table = new CellItem[40][40];
    // private CellItem[][] townTable = new CellItem[11][11];
    private Position selectedPosition;

    private int currentPlayerIndex = 0;

    public Game() {
    }

    public void setPlayers(List<String> playerNames) {
        for (String player : playerNames) {
            players.add(new Player(player));
        }
    }

    public void selectItem(Position position) {
        selectedPosition = position;
    }

    public void deselectItem() {
        selectedPosition = null;
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void addItem(Position position, CellItem cells) {
        table[position.x][position.y] = cells;
    }



    public CellItem getCellItem(Position position) {
        return table[position.x][position.y];
    }


    public void moveCellItem(Position from, Position to) {
        CellItem cellItem = table[from.x][from.y];
        CellItem cell = table[to.x][to.y];

        if (cellItem != null && cell == null) {
            table[from.x][from.y] = null;
            table[to.x][to.y] = cellItem;
        }
    }

    public CellItem[][] getTable() {
        return table;
    }


    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isValidStep(Position from, Position to) {
        CellItem c = getCellItem(from);

        return c != null &&
                (from.x == to.x || from.y == to.y) &&
                Math.abs(from.x - to.x) <= c.maxStep() &&
                Math.abs(from.y - to.y) <= c.maxStep();
    }

    public boolean isValidAttack(Position from, Position to) {
        CellItem c1 = getCellItem(from);
        CellItem c2 = getCellItem(to);

        return c1 != null && c2 != null &&
                !c1.getOwner().equals(c2.getOwner()) &&
                Math.abs(from.x - to.x) <= c1.attackRange() &&
                Math.abs(from.y - to.y) <= c1.attackRange();
    }

    public boolean isValidBorder(Position from, Position to) {
        CellItem c1 = getCellItem(from);
        CellItem c2 = getCellItem(to);

        return c1 != null && c2 != null &&
                !c1.getOwner().equals(c2.getOwner()) &&
                Math.abs(from.x - to.x) <= 11 &&
                Math.abs(from.y - to.y) <= 11;
    }

    public void removeItem(Position position) {
        table[position.x][position.y] = null;
    }
}
