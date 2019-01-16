package flow;

import flow.cells.CellItem;
import flow.cells.TownHall;

import java.util.List;

public interface MainContract {
    interface View {
        void showTable(CellItem[][] table);
        void setSelection(Position position, boolean selection);
        void updateCell(Position position, CellItem cells);
        int selectFromList(String[] list);
        int selectFromBuildings(String[] list);
        int selectFromTownHall(String[] list);
        void showPlayers(List<Player> players);
        void selectCurrentPlayer(Player player);
        void showCurrentPlayerGold(Player player);
        void showCurrentPlayerPoints(Player player);
        void showCitizenNumber(Player player);

        void highlightTownHall(Range range, Position center);
        void highlightRange(Range range, Position center);
        void highlightAttackableItem(Position position, boolean highlight);
        void removeHighlight();
    }

    interface Presenter {
        void onTableItemClicked(Position position);
    }

    interface Action {

    }
}
