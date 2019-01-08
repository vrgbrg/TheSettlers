package flow;

import javax.swing.*;

public interface MainContract {
    interface View {
        void showTable(Cell[][] table);
        void setSelection(Position position, boolean selection);
        void updateCell(Position position, Cell player);
        JRadioButton getToBuild();
        JRadioButton getSoldierRecruitment();
        String getSelectedBuilding();
    }

    interface Presenter {
        void onTableItemClicked(Position position);
    }
}
