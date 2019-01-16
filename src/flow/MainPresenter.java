package flow;

import flow.cells.*;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private Game game;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        game = new Game();


        view.showTable(game.getTable());
        view.showPlayers(game.getPlayers());
        view.selectCurrentPlayer(game.getCurrentPlayer());
        view.showCurrentPlayerGold(game.getCurrentPlayer());
        view.showCurrentPlayerPoints(game.getCurrentPlayer());
        view.showCitizenNumber(game.getCurrentPlayer());

    }

    @Override
    public void onTableItemClicked(Position position) {
        CellItem[][] table = game.getTable();
        CellItem cellItem = game.getCellItem(position);
        Player currentPlayer = game.getCurrentPlayer();

        Position selectedPosition = game.getSelectedPosition();

        if (cellItem != null) {
            if (!cellItem.getOwner().equals(currentPlayer)) {
                if (selectedPosition != null) {
                    CellItem selectedItem = game.getCellItem(selectedPosition);

                    if (selectedItem.getOwner().equals(currentPlayer)) {
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
                addNewItem(position);
            }
        }

        if (currentPlayer.getRoundPoint() <= 0) {
            currentPlayer.changeGold(currentPlayer.getActualPopulation() * 0.1);
            nextPlayer();
            highlightTownHallRange(townHallBorder(table, currentPlayer));
        }

        view.showCurrentPlayerPoints(currentPlayer);
        view.showCurrentPlayerGold(currentPlayer);

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

    private void highlightTownHallRange(Position itemPosition) {
        view.removeHighlight();

        CellItem item = game.getCellItem(itemPosition);

        Position p1 = new Position(
                Math.max(0, itemPosition.x - 11),
                Math.max(0, itemPosition.y - 11));
        Position p2 = new Position(
                Math.min(49, itemPosition.x + 11),
                Math.min(49, itemPosition.y + 11));

        Range range = new Range(p1, p2);
        view.highlightTownHall(range, item.canMoveDiagonally() ? null : itemPosition);
    }

    private void nextPlayer() {
        game.nextPlayer();
        view.selectCurrentPlayer(game.getCurrentPlayer());
        view.showCurrentPlayerGold(game.getCurrentPlayer());


        Position selectedPosition = game.getSelectedPosition();
        if (selectedPosition != null) {
            game.deselectItem();
            view.setSelection(selectedPosition, false);
        }
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
        String[] buildings = {"Bank", "Kaszárnya", "Fegyverkovács műhely", "Kiképzőhely", "Szállás", "Katonaszállás"};
        String[] townhall = {"Városháza"};
        CellItem cellItem = null;
        Player currentPlayer = game.getCurrentPlayer();

        CellItem[][] table = game.getTable();

        if (!(hasTypeOfCell(table, "TownHall", currentPlayer))) {
            int choiceTownHall = view.selectFromTownHall(townhall);
            if (choiceTownHall == 0) {
                cellItem = new TownHall(currentPlayer);
                currentPlayer.changeRoundPoint(2);
            }
        } else if (hasTypeOfCell(table, "BarrackRoom", currentPlayer)) {
            int choice = view.selectFromList(list);
            if (choice == 1) {
                cellItem = new Soldier(currentPlayer);
                currentPlayer.changeRoundPoint(2);
            } else if (choice == 0) {
                int buildingChoice = view.selectFromList(buildings);
                if (buildingChoice == 0) {
                    cellItem = new Bank(currentPlayer);
                    currentPlayer.changeRoundPoint(2);
                    currentPlayer.changeGold(((Bank) cellItem).getPrice());
                    // currentPlayer.setGold();
                } else if (buildingChoice == 1) {
                    cellItem = new BarrackRoom(currentPlayer);
                    currentPlayer.changeRoundPoint(2);
                    currentPlayer.changeGold(((BarrackRoom) cellItem).getPrice());
                } else if (buildingChoice == 2) {
                    cellItem = new GunSmith(currentPlayer);
                    currentPlayer.changeRoundPoint(2);
                    currentPlayer.changeGold(((GunSmith) cellItem).getPrice());
                } else if (buildingChoice == 3) {
                    cellItem = new TrainingRoom(currentPlayer);
                    currentPlayer.changeRoundPoint(2);
                    currentPlayer.changeGold(((TrainingRoom) cellItem).getPrice());
                } else if (buildingChoice == 4) {
                    cellItem = new LivingQuarters(currentPlayer);
                    currentPlayer.changeRoundPoint(2);
                    currentPlayer.changeGold(((LivingQuarters) cellItem).getPrice());
                } else if (buildingChoice == 5) {
                    cellItem = new SoldierLivingQuarters(currentPlayer);
                    currentPlayer.changeRoundPoint(2);
                    currentPlayer.changeGold(((SoldierLivingQuarters) cellItem).getPrice());
                }
            }
        } else {
            int buildingChoice = view.selectFromList(buildings);
            if (buildingChoice == 0) {
                cellItem = new Bank(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((Bank) cellItem).getPrice());
                // currentPlayer.setGold();
            } else if (buildingChoice == 1) {
                cellItem = new BarrackRoom(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((BarrackRoom) cellItem).getPrice());
            } else if (buildingChoice == 2) {
                cellItem = new GunSmith(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((GunSmith) cellItem).getPrice());
            } else if (buildingChoice == 3) {
                cellItem = new TrainingRoom(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((TrainingRoom) cellItem).getPrice());
            } else if (buildingChoice == 4) {
                cellItem = new LivingQuarters(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((LivingQuarters) cellItem).getPrice());
            } else if (buildingChoice == 5) {
                cellItem = new SoldierLivingQuarters(currentPlayer);
                currentPlayer.changeRoundPoint(2);
                currentPlayer.changeGold(((SoldierLivingQuarters) cellItem).getPrice());
            }
        }


        if (cellItem != null) {
            game.addItem(position, cellItem);

            view.updateCell(position, cellItem);
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

        return  cell;
    }

}
