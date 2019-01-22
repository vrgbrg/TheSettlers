package flow;

import flow.cells.*;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private Game game;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        game = new Game();
    }

    @Override
    public void init() {
        game.setPlayers(view.getPlayerNames());

        initializeTownHall();

        view.showTable(game.getTable());
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
        CellItem[][] table = game.getTable();
        CellItem cellItem = game.getCellItem(position);

        Player currentPlayer = game.getCurrentPlayer();

        //currentPlayer.drawTownTable();

        Position selectedPosition = game.getSelectedPosition();

        CellItem item = game.getCellItem(position);

        // view.showCurrentPlayerRoundPoints(currentPlayer);
        // view.showCurrentCityData(currentPlayer);

        refreshDataLayout();

        if (currentPlayer.getRoundPoint() <= 0) {
            cityBorn(currentPlayer);
            cityDeath(currentPlayer);
            nextPlayer(position);
            currentPlayer.setRoundPoint(3);
        }

        if (item instanceof TownHall && item.getOwner().equals(currentPlayer)) {
            view.showTownTable(currentPlayer.getTownTable());
            return;
        }

        if (cellItem != null) {
            if (!cellItem.getOwner().equals(currentPlayer)) {
                if (selectedPosition != null) {
                    CellItem selectedItem = game.getCellItem(selectedPosition);
                    if (selectedItem.getOwner().equals(currentPlayer)) {
                        view.showSelectionCellData(selectedPosition, true);
                        if (game.isValidAttack(selectedPosition, position)) {
                            game.removeItem(position);
                            view.updateCell(position, null);
                            removeHighlights();
                            view.setSelection(selectedPosition, false);
                        }
                    }
                }
            } else {
                removeHighlights();
                if (changeItemSelection(position, selectedPosition)) {
                    if (cellItem.getOwner().equals(currentPlayer)) {
                        highlightItemRange(position, cellItem);

                        for (int i = 0; i < 50; ++i) {
                            for (int j = 0; j < 50; ++j) {
                                Position to = new Position(i, j);
                                if (!to.equals(position)) {
                                    view.highlightAttackableItem(to, game.isValidAttack(position, to));
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (selectedPosition != null) {
                CellItem selectedItem = game.getCellItem(selectedPosition);
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


        int population = currentPlayer.getActualPopulation();

    }

    @Override
    public void redrawTable() {
        view.showTable(game.getTable());
    }

    private void highlightItemRange(Position itemPosition, CellItem item) {
        view.removeHighlight();

        Position p1 = new Position(
                Math.max(0, itemPosition.x - item.maxStep()),
                Math.max(0, itemPosition.y - item.maxStep()));
        Position p2 = new Position(
                Math.min(49, itemPosition.x + item.maxStep()),
                Math.min(49, itemPosition.y + item.maxStep()));

        Range range = new Range(p1, p2);
        view.highlightRange(range, item.canMoveDiagonally() ? null : itemPosition);
    }

    private void highlightTownHallRange(Position itemPosition, boolean isTable) {
        view.removeHighlight();

        Position p1 = new Position(
                Math.max(0, itemPosition.x - 5),
                Math.max(0, itemPosition.y - 5));
        Position p2 = new Position(
                Math.min(49, itemPosition.x + 5),
                Math.min(49, itemPosition.y + 5));

        Range range = new Range(p1, p2);
        view.highlightTownHall(range, townHallBorder(game.getTable(), game.getCurrentPlayer()),true);
    }

    private void nextPlayer(Position position) {
        CellItem[][] table = game.getTable();
        Player currentPlayer = game.getCurrentPlayer();
        CellItem cellItem = game.getCellItem(position);
        game.nextPlayer();
        cityTaxCalculator(game.getCurrentPlayer());
        refreshDataLayout();


        Position selectedPosition = game.getSelectedPosition();
        if (selectedPosition != null) {
            //game.deselectItem();
            view.setSelection(selectedPosition, false);
        }
        System.out.println("next kiscica");
        if(hasTypeOfCell(table, "TownHall", currentPlayer)) {
            System.out.println("kiscica");
            highlightTownHallRange(position, true);
        }
    }

    @Override
    public void skipPlayer() {
        CellItem[][] table = game.getTable();
        game.nextPlayer();
        view.selectCurrentPlayer(game.getCurrentPlayer());
        if(hasTypeOfCell(table, "TownHall", game.getCurrentPlayer())) {
            highlightTownHallRange(townHallBorder(table, game.getCurrentPlayer()),  true);
        }
        refreshDataLayout();
        game.getCurrentPlayer().setRoundPoint(3);
    }


    private boolean changeItemSelection(Position position, Position selectedPosition) {
        game.selectItem(position);
        view.setSelection(position, true);

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
        String[] list = {"Építés", "Katona toborzás"};
        String[] townhall = {"Városháza"};
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
                    if(currentPlayer.getRoundPoint() - 2 <= 0) {
                        String text = "Nincs elég pontod!";
                        System.out.println("Nincs elég pontod!");
                        view.showGameMessage(text);
                    }
                }
            }
        }

        if (cellItem != null) {

            game.addItem(position, cellItem);

            view.updateCell(position, cellItem);

            if(hasTypeOfCell(table, "TownHall", currentPlayer)) {
                highlightTownHallRange(position,  true);
            }
        }

    }

    private void addNewTownItem(Position position) {
        String[] buildings = {"Bank", "Kaszárnya", "Fegyverkovács műhely", "Kiképzőhely", "Szállás", "Katonaszállás"};
        CellItem cellItem = null;
        Player currentPlayer = game.getCurrentPlayer();

        int buildingChoice = view.selectFromList(buildings);
        if (buildingChoice == 0) {
            if (currentPlayer.getRoundPoint() - 2 >= 0 && currentPlayer.getGold() - 700 >=0) {
                cellItem = new Bank(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((Bank) cellItem).getPrice());
                refreshDataLayout();
            } else {
                if(currentPlayer.getRoundPoint() - 2 <= 0 && currentPlayer.getGold() - 700 <= 0) {
                    String text = "Nincs elég pontod és aranyad!";
                    System.out.println("Nincs elég pontod és aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getRoundPoint() - 2 <= 0) {
                    String text = "Nincs elég aranyad!";
                    System.out.println("Nincs elég aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getGold() - 700 <= 0){
                    String text = "Nincs elég pontod!";
                    System.out.println("Nincs elég pontod!");
                    view.showGameMessage(text);
                }
            }
        } else if (buildingChoice == 1) {
            if (currentPlayer.getRoundPoint() - 2 >= 0 && currentPlayer.getGold() - 200 >=0) {
                cellItem = new BarrackRoom(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((BarrackRoom) cellItem).getPrice());
                refreshDataLayout();
            } else {
                if(currentPlayer.getRoundPoint() - 2 <= 0 && currentPlayer.getGold() - 200 <= 0) {
                    String text = "Nincs elég pontod és aranyad!";
                    System.out.println("Nincs elég pontod és aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getRoundPoint() - 2 <= 0) {
                    String text = "Nincs elég aranyad!";
                    System.out.println("Nincs elég aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getGold() - 200 <= 0){
                    String text = "Nincs elég pontod!";
                    System.out.println("Nincs elég pontod!");
                    view.showGameMessage(text);
                }
            }
        } else if (buildingChoice == 2) {
            if (currentPlayer.getRoundPoint() - 2 >= 0 && currentPlayer.getGold() - 300 >=0) {
                cellItem = new GunSmith(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((GunSmith) cellItem).getPrice());
                refreshDataLayout();
            } else {
                if(currentPlayer.getRoundPoint() - 2 <= 0 && currentPlayer.getGold() - 300 <= 0) {
                    String text = "Nincs elég pontod és aranyad!";
                    System.out.println("Nincs elég pontod és aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getRoundPoint() - 2 <= 0) {
                    String text = "Nincs elég aranyad!";
                    System.out.println("Nincs elég aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getGold() - 300 <= 0){
                    String text = "Nincs elég pontod!";
                    System.out.println("Nincs elég pontod!");
                    view.showGameMessage(text);
                }
            }
        } else if (buildingChoice == 3) {
            if (currentPlayer.getRoundPoint() - 2 >= 0 && currentPlayer.getGold() - 400 >=0) {
                cellItem = new TrainingRoom(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((TrainingRoom) cellItem).getPrice());
                refreshDataLayout();
            } else {
                if(currentPlayer.getRoundPoint() - 2 <= 0 && currentPlayer.getGold() - 400 <= 0) {
                    String text = "Nincs elég pontod és aranyad!";
                    System.out.println("Nincs elég pontod és aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getRoundPoint() - 2 <= 0) {
                    String text = "Nincs elég aranyad!";
                    System.out.println("Nincs elég aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getGold() - 400 <= 0){
                    String text = "Nincs elég pontod!";
                    System.out.println("Nincs elég pontod!");
                    view.showGameMessage(text);
                }
            }
        } else if (buildingChoice == 4) {
            if (currentPlayer.getRoundPoint() - 2 >= 0 && currentPlayer.getGold() - 100 >=0) {
                cellItem = new LivingQuarters(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((LivingQuarters) cellItem).getPrice());
                refreshDataLayout();
            } else {
                if(currentPlayer.getRoundPoint() - 2 <= 0 && currentPlayer.getGold() - 100 <= 0) {
                    String text = "Nincs elég pontod és aranyad!";
                    System.out.println("Nincs elég pontod és aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getRoundPoint() - 2 <= 0) {
                    String text = "Nincs elég aranyad!";
                    System.out.println("Nincs elég aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getGold() - 100 <= 0){
                    String text = "Nincs elég pontod!";
                    System.out.println("Nincs elég pontod!");
                    view.showGameMessage(text);
                }
            }
        } else if (buildingChoice == 5) {
            if (currentPlayer.getRoundPoint() - 2 >= 0 && currentPlayer.getGold() - 150 >=0) {
                cellItem = new SoldierLivingQuarters(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((SoldierLivingQuarters) cellItem).getPrice());
                refreshDataLayout();
            } else {
                if(currentPlayer.getRoundPoint() - 2 <= 0 && currentPlayer.getGold() - 150 <= 0) {
                    String text = "Nincs elég pontod és aranyad!";
                    System.out.println("Nincs elég pontod és aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getRoundPoint() - 2 <= 0) {
                    String text = "Nincs elég aranyad!";
                    System.out.println("Nincs elég aranyad!");
                    view.showGameMessage(text);
                } else if(currentPlayer.getGold() - 150 <= 0){
                    String text = "Nincs elég pontod!";
                    System.out.println("Nincs elég pontod!");
                    view.showGameMessage(text);

                }

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
                }

            }
        }
        return false;
    }


    private Position townHallBorder(CellItem[][] table, Player player) {
        Position cell = null;

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] instanceof TownHall && table[i][j].getOwner().equals(player)) {
                    cell = new Position(i, j);
                }
            }
        }

        return cell;
    }


    private void cityTaxCalculator(Player player) {

        double t = player.getActualPopulation() * 0.1;
        int tax = (int) t;

        player.changeGold(tax);

    }

    private void cityDeath(Player player) {

        int randomNumber = random(0, 9);

        double d = player.getActualPopulation() * (randomNumber/100);
        int death = (int) d;

        player.populationDecrease(death);

    }

    private void cityBorn(Player player) {

        int randomNumber = random(0, 15);

        double b = player.getActualPopulation() * (randomNumber/100);
        int born = (int) b;

        player.populationIncrease(born);

    }

    private void refreshDataLayout() {
        view.showCurrentCityData(game.getCurrentPlayer());
        view.showCurrentPlayerRoundPoints(game.getCurrentPlayer());
    }

}
