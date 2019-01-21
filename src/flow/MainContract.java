package flow;

import flow.cells.CellItem;

import java.util.List;

public interface MainContract {
    interface View {
        void showTable(CellItem[][] table);
        void showTownTable(CellItem[][] townTable);
        void setSelection(Position position, boolean selection);
        void selectionText(Position position, boolean selection);
        void updateCell(Position position, CellItem cells);
        void updateTownCell(Position position, CellItem cell);
        int selectFromList(String[] list);
        int selectFromBuildings(String[] list);
        int selectFromTownHall(String[] list);
        void showPlayers(List<Player> players);
        void selectCurrentPlayer(Player player);
        void showCurrentCityData(Player player);
        void showCurrentPlayerPoints(Player player);
        void showCurrentCell(Player player, CellItem cell);


        void highlightTownHall(Range range, Position center, boolean isTable);
        void highlightRange(Range range, Position center);
        void highlightAttackableItem(Position position, boolean highlight);
        void removeHighlight();
    }

    interface Presenter {
        void onTableItemClicked(Position position, boolean isTable);
        void redrawTable();
        void skipPlayer();

    }

}
