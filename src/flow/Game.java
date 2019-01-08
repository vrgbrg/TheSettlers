package flow;

public class Game {
    private Cell[][] table = new Cell[50][50];
    private Position selectedPosition;

    public void selectItem(Position position) {
        selectedPosition = position;
    }

    public Position getSelectedPosition() {
        return selectedPosition;
    }

    public void addItem(Position position, Cell player) {
        table[position.x][position.y] = player;
    }

    public Cell getCell(Position position) {
        return table[position.x][position.y];
    }

    public void moveSoldier(Position from, Position to) {
        Cell player = table[from.x][from.y];
        Cell cell = table[to.x][to.y];

        if (player != null && cell == null) {
            table[from.x][from.y] = null;
            table[to.x][to.y] = player;
        }
    }

    public void toBuildTownHall(Position to) {
        Cell townHall = new TownHall("V", "Városháza", null, 200, 2000);
        Cell cell = table[to.x][to.y];
        if (cell == null) {
            table[to.x][to.y] = townHall;
        }
    }

    public void toBuildBank(Position to) {
        Cell bank = new Bank("B", "Bank", null, 200);
        Cell cell = table[to.x][to.y];
        if (cell == null) {
            table[to.x][to.y] = bank;
        }
    }

    public void toRecruit(Position to) {
        Cell soldier = new Soldier("K", "Katona", 10);
        Cell cell = table[to.x][to.y];
        if (cell == null) {
            table[to.x][to.y] = soldier;
        }
    }

    public Cell[][] getTable() {
        return table;
    }
}
