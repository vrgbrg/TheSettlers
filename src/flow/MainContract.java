package flow;

import flow.cells.CellItem;

import java.util.List;

public interface MainContract {
    interface View {
        void showMap(CellItem[][] table);
        void showTownTable(CellItem[][] townTable);
        void showBattle(Player player, Player opponent, boolean isTable);
        void setSelection(Position position, boolean selection);
        void showSelectionCellData(Position position, boolean selection);
        void updateCell(Position position, CellItem cells);
        void updateTownCell(Position position, CellItem cell);
        int selectFromList(String[] list);
        int selectFromBuildings(String[] list);
        int selectFromTownHall(String[] list);
        void showPlayers(List<Player> players);
        void selectCurrentPlayer(Player player);
        List<String> getPlayerNames();
        void showCurrentCityData(Player player);
        void showCurrentPlayerRoundPoints(Player player);
        void showGameMessage(String text);
        void showCellData(String text);

        void highlightTownHall(Range range, Position center, boolean isTable);
        void highlightPlayer(List<Position> positions);
        void highlightRange(Range range, Position center);
        void highlightAttackableItem(Position position, boolean highlight);
        void removeHighlight();
    }

    interface Presenter {
        void onTableItemClicked(Position position, boolean isTable);
        void init();
        void redrawTable();
        int getCurrentPlayerPeopleStorage();
        int populationBorn(Player player);
        int populationDeath(Player player);
        int taxCalculator(Player player);
        void skipPlayer();
        Player getOpponentPlayer(Position position);

    }

}
