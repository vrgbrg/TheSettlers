package flow;

import flow.cells.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private Game game;
    public Set<Position> townHallPosition;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        game = new Game();
        this.townHallPosition = new HashSet<>();
    }

    @Override
    public void init() {
        game.setPlayers(view.getPlayerNames());

        initializeTownHall();

        view.showMap(game.getTable());
        view.showPlayers(game.getPlayers());
        view.selectCurrentPlayer(game.getCurrentPlayer());
        refreshDataLayout();
    }

    private void initializeTownHall() {
        Player player = game.getCurrentPlayer();
        player.addTownItem(new Position(5, 5), new TownHall(player));
    }

    @Override
    public void onTableItemClicked(Position position, boolean isTable) {
        CellItem cellItem = game.getCellItem(position);
        Player currentPlayer = game.getCurrentPlayer();
        Position selectedPosition = game.getSelectedPosition();

        //currentPlayer.drawTownTable();
        refreshDataLayout();

        if (currentPlayer.getRoundPoint() <= 0) {
            nextPlayer();
            townDataChange(game.getCurrentPlayer());
            currentPlayer.setRoundPoint(3);
        }

        if (cellItem instanceof TownHall && cellItem.getOwner().equals(currentPlayer)) {
            view.showTownTable(currentPlayer.getTownTable());
            return;
        }

        if (cellItem != null) {
            if (!cellItem.getOwner().equals(currentPlayer)) {
                if (selectedPosition != null) {
                    CellItem selectedItem = game.getCellItem(selectedPosition);
                    CellItem selectedTownItem = game.getCellItem(selectedPosition);
                    view.showCellData(selectedTownItem.toString());
                    if (selectedItem.getOwner().equals(currentPlayer)) {
                        view.showSelectionCellData(selectedPosition, true);
                        if (game.isValidAttack(selectedPosition, position, true)) {
                            if (cellItem instanceof TownHall && !(cellItem.getOwner().equals(currentPlayer))) {
                                view.showBattle(currentPlayer, getOpponentPlayer(position), game.getPlayerList(), false);
                            } else if (cellItem instanceof Soldier && !(cellItem.getOwner().equals(currentPlayer))) {
                                game.removeItem(position);
                                view.updateCell(position, null);
                                removeHighlights();
                                view.setSelection(selectedPosition, false);
                            }
                        }
                    }
                }
            } else {
                removeHighlights();
                if (changeItemSelection(position, selectedPosition)) {
                    if (cellItem.getOwner().equals(currentPlayer)) {
                        highlightItemRange(position);

                        for (int i = 0; i < 40; ++i) {
                            for (int j = 0; j < 40; ++j) {
                                Position to = new Position(i, j);
                                if (!to.equals(position)) {
                                    view.highlightAttackableItem(to, game.isValidAttack(position, to, true));
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (selectedPosition != null) {
                CellItem selectedItem = game.getCellItem(selectedPosition);
                CellItem selectedTownItem = game.getCellItem(selectedPosition);
                view.showCellData(selectedTownItem.toString());
                if (selectedItem.isMovable() &&
                        game.isValidStep(selectedPosition, position) &&
                        selectedItem.getOwner().equals(currentPlayer)) {
                    moveItem(position, selectedPosition);
                }
            } else {
                if (isTable) {
                    addNewItem(position);
                } else {

                    CellItem townCellItem = currentPlayer.getTownHallCellItem(position);
                    if (townCellItem == null) {
                        addNewTownItem(position);
                    }

                }
            }
        }

    }

    @Override
    public void redrawTable() {
        view.showMap(game.getTable());
    }

    private void highlightItemRange(Position itemPosition) {
        view.removeHighlight();

        Position p1 = new Position(
                Math.max(0, itemPosition.x - 2),
                Math.max(0, itemPosition.y - 2));
        Position p2 = new Position(
                Math.min(39, itemPosition.x + 2),
                Math.min(39, itemPosition.y + 2));

        Range range = new Range(p1, p2);
        view.highlightRange(range, itemPosition);
    }

    private void highlightTownHallRange(Position itemPosition) {
        view.removeHighlight();

        Position p1 = new Position(
                Math.max(0, itemPosition.x - 5),
                Math.max(0, itemPosition.y - 5));
        Position p2 = new Position(
                Math.min(39, itemPosition.x + 5),
                Math.min(39, itemPosition.y + 5));

        Range range = new Range(p1, p2);
        view.highlightTownHall(range, townHallCenter(game.getTable(), game.getCurrentPlayer()), true);
    }

    private void highlightCurrentPlayer() {
        List<Position> currentPlayersItemPositions = game.getCurrentPlayersItemPositions();
        view.highlightPlayer(currentPlayersItemPositions);
    }

    public void nextPlayer() {
        CellItem[][] table = game.getTable();
        Player currentPlayer = game.getCurrentPlayer();
        highlightCurrentPlayer();
        game.nextPlayer();
        game.getCurrentPlayer().setRoundPoint(3);
        refreshDataLayout();


        Position selectedPosition = game.getSelectedPosition();
        if (hasTypeOfCell(table, "TownHall", currentPlayer)) {
            highlightTownHallRange(townHallCenter(table, currentPlayer));
        }
        if (selectedPosition != null) {
            //game.deselectItem();
            view.setSelection(selectedPosition, false);
        }
    }

    @Override
    public void skipPlayer() {
        game.nextPlayer();
        townDataChange(game.getCurrentPlayer());
        redrawTable();
        if (hasTypeOfCell(game.getTable(), "TownHall", game.getCurrentPlayer())) {
            highlightTownHallRange(townHallCenter(game.getTable(), game.getCurrentPlayer()));
        }
        view.selectCurrentPlayer(game.getCurrentPlayer());
        game.getCurrentPlayer().setRoundPoint(3);
        refreshDataLayout();
    }


    private boolean changeItemSelection(Position position, Position selectedPosition) {
        game.selectItem(position);
        view.setSelection(position, true);
        view.showCellData(game.getCellItem(position).toString());
        refreshDataLayout();

        if (selectedPosition != null) {
            view.setSelection(selectedPosition, false);

            if (selectedPosition.equals(position)) {
                game.deselectItem();
                return false;
            }
        }
        return true;
    }

    private void moveItem(Position position, Position selectedPosition) {
        Player currentPlayer = game.getCurrentPlayer();
        game.moveCellItem(selectedPosition, position);
        game.selectItem(position);

        view.setSelection(selectedPosition, false);

        view.updateCell(selectedPosition, null);
        view.updateCell(position, game.getCellItem(position));
        currentPlayer.changeRoundPoint(1);

        view.setSelection(position, true);
        view.showCellData(game.getCellItem(position).toString());
        refreshDataLayout();

        removeHighlights();
    }

    private void removeHighlights() {
        view.removeHighlight();
        Position selectedPosition = game.getSelectedPosition();

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                Position to = new Position(i, j);

                if (selectedPosition == null ||
                        !selectedPosition.equals(to)) {
                    view.highlightAttackableItem(to, false);
                }
            }
        }
    }

    private void addNewItem(Position position) {
        String[] list = {"Buildings", "Soldier"};
        String[] townhall = {"TownHall"};
        CellItem cellItem = null;
        Player currentPlayer = game.getCurrentPlayer();

        CellItem[][] table = game.getTable();
        CellItem[][] townTable = currentPlayer.getTownTable();

        if (!(hasTypeOfCell(table, "TownHall", currentPlayer))) {
            int choiceTownHall = view.selectFromTownHall(townhall);
            if (choiceTownHall == 0) {
                cellItem = new TownHall(currentPlayer);
                refreshDataLayout();
            }
        } else if (hasTypeOfCell(townTable, "BarrackRoom", currentPlayer)) {
            int choice = view.selectFromList(list);
            if (choice == 1) {
                if (currentPlayer.getRoundPoint() - 2 >= 0) {
                    cellItem = new Soldier(currentPlayer);
                    currentPlayer.changeRoundPoint(2);
                    refreshDataLayout();
                } else {
                    if (currentPlayer.getRoundPoint() - 2 <= 0) {
                        view.showGameMessage("Nincs elég pontod!");
                    }
                }
            }
        }

        if (cellItem != null) {

            game.addItem(position, cellItem);

            view.updateCell(position, cellItem);

            if (hasTypeOfCell(table, "TownHall", currentPlayer)) {
                highlightTownHallRange(townHallCenter(table, currentPlayer));
            }
        }

    }

    private void addNewTownItem(Position position) {
        String[] buildings = {"Bank - 700 Gold", "GunSmith - 300 Gold", "TrainingRoom - 400 Gold", "LivingQ - 100 Gold", "Soldier LivingQ - 150 Gold", "Barrack - 200 Gold"};
        String[] buildingsWithoutBarrackRoom = {"Bank -  700 Gold", "GunSmith - 300 Gold", "TrainingRoom - 400 Gold", "LivingQ - 100 Gold", "Soldier LivingQ - 150 Gold"};
        CellItem cellItem = null;
        Player currentPlayer = game.getCurrentPlayer();
        CellItem[][] townTable = currentPlayer.getTownTable();

        int buildingChoice;
        if (hasTypeOfCell(townTable, "GunSmith", currentPlayer)) {
            buildingChoice = view.selectFromList(buildings);
        } else {
            buildingChoice = view.selectFromList(buildingsWithoutBarrackRoom);
        }

        switch (buildingChoice) {
            case 0:
                cellItem = new Bank(currentPlayer);
                break;
            case 1:
                cellItem = new GunSmith(currentPlayer);
                break;
            case 2:
                cellItem = new TrainingRoom(currentPlayer);
                break;
            case 3:
                cellItem = new LivingQuarters(currentPlayer);
                break;
            case 4:
                cellItem = new SoldierLivingQuarters(currentPlayer);
                break;
            case 5:
                cellItem = new BarrackRoom(currentPlayer);
                break;
        }

        if (currentPlayer.getRoundPoint() - 2 >= 0 && currentPlayer.getGold() - cellItem.getPrice() >= 0) {
            currentPlayer.changeRoundPoint(2);
            currentPlayer.changeGold(cellItem.getPrice());
            refreshDataLayout();
        } else {
            if (currentPlayer.getRoundPoint() - 2 <= 0 && currentPlayer.getGold() - cellItem.getPrice() <= 0) {
                view.showGameMessage("Nincs elég pontod és aranyad!");
            } else if (currentPlayer.getRoundPoint() - 2 <= 0) {
                view.showGameMessage("Nincs elég aranyad!");
            } else if (currentPlayer.getGold() - cellItem.getPrice() <= 0) {
                view.showGameMessage("Nincs elég pontod!");
            }
        }

        if (cellItem != null) {
            currentPlayer.addTownItem(position, cellItem);

            view.updateTownCell(position, cellItem);
        }

    }

    private int random(int min, int max) {
        int random = (int) (Math.random() * (max - min) + 1 + min);
        return random;
    }

    private boolean hasTypeOfCell(CellItem[][] table, String building, Player player) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (building.equals("TownHall")) {
                    if (table[i][j] instanceof TownHall && table[i][j].getOwner().equals(player)) {
                        return true;
                    }
                } else if (building.equals("BarrackRoom")) {
                    if (table[i][j] instanceof BarrackRoom && table[i][j].getOwner().equals(player)) {
                        return true;
                    }
                } else if (building.equals("GunSmith")) {
                    if (table[i][j] instanceof GunSmith && table[i][j].getOwner().equals(player)) {
                        return true;
                    }
                } else if (building.equals("Bank")) {
                    if (table[i][j] instanceof Bank && table[i][j].getOwner().equals(player)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }


    private Position townHallCenter(CellItem[][] table, Player player) {
        Position cell = null;

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] instanceof TownHall && table[i][j].getOwner().equals(player)) {
                    cell = new Position(i, j);
                    townHallPosition.add(cell);
                }
            }
        }

        return cell;
    }

    public void cellOwner(Player player) {
        Position cell = null;
        CellItem[][] table = game.getTable();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == null) {
                    view.updateCell(new Position(i, j), null);
                }
                if (table[i][j] != null && table[i][j].getOwner().equals(player)) {
                    cell = new Position(i, j);
                    table[i][j] = null;
                    view.updateCell(cell, table[i][j]);

                }
            }
        }

    }


    private void townDataChange(Player player) {
        int actual = player.getActualPopulation();

        int populationChange = player.getActualPopulation() + populationBorn(game.getCurrentPlayer()) - populationDeath(game.getCurrentPlayer());

        if (hasTypeOfCell(player.getTownTable(), "Bank", player)) {
            player.changeGold((int) ((taxCalculator(game.getCurrentPlayer())) * 1.2));
        } else if (!(hasTypeOfCell(player.getTownTable(), "Bank", player))) {
            player.changeGold(taxCalculator(game.getCurrentPlayer()));
        }

    }

    public int populationDeath(Player player) {
        int actual = player.getActualPopulation();
        int randomNumberDeath = random(0, 9);
        double d = actual * (randomNumberDeath / 100.0);

        int death = (int) d;
        return death;
    }

    public int populationBorn(Player player) {
        int actual = player.getActualPopulation();
        int randomNumberBorn = random(0, 15);
        double b = actual * (randomNumberBorn / 100.0);

        int born = (int) b;
        return born;
    }

    public int taxCalculator(Player player) {
        int actual = player.getActualPopulation();

        double t = actual * 0.1;
        int tax = (int) t;

        return tax;
    }

    public int getCurrentPlayerPeopleStorage() {
        int countLivingQuarter = 0;

        Player currentPlayer = game.getCurrentPlayer();
        for (int i = 0; i < currentPlayer.getTownTable().length; i++) {
            for (int j = 0; j < currentPlayer.getTownTable()[i].length; j++) {
                Position position = new Position(i, j);
                CellItem cellItem = currentPlayer.getTownHallCellItem(position);
                if (cellItem instanceof LivingQuarters) {
                    countLivingQuarter += 1000;
                }
            }
        }
        return 2000 + countLivingQuarter;
    }

    public Player getOpponentPlayer(Position position) {
        System.out.println(game.getCellItem(position).getOwner().getName());
        return game.getCellItem(position).getOwner();
    }


    private void refreshDataLayout() {
        Player currentPlayer = game.getCurrentPlayer();
        getCurrentPlayerPeopleStorage();
        view.showCurrentCityData(currentPlayer);
        view.showCurrentPlayerRoundPoints(currentPlayer);
        System.out.println(townHallPosition);
    }

}
