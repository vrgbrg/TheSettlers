package flow;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private Game game;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        game = new Game();

        initializeTable();
        view.showTable(game.getTable());
    }

    private void initializeTable() {
        game.addItem(new Position(0, 0), new Cell("X"));
    }

    @Override
    public void onTableItemClicked(Position position) {
        Cell player = game.getCell(position);

        Position selectedPosition = game.getSelectedPosition();
        if (player != null) {
            game.selectItem(position);

            if (selectedPosition != null) {
                view.setSelection(selectedPosition, false);
            }
            view.setSelection(position, true);
        } else if (view.getToBuild().isSelected()) {
            if (view.getSelectedBuilding().equals("Városháza")) {
                game.toBuildTownHall(position);
                view.updateCell(position, game.getCell(position));
            } else if (view.getSelectedBuilding().equals("Bank")) {
                game.toBuildBank(position);
                view.updateCell(position, game.getCell(position));
            } else if (view.getSoldierRecruitment().isSelected()) {
                game.toRecruit(position);
                view.updateCell(position, game.getCell(position));
            }
        } else {
            if (selectedPosition != null) {
                game.moveSoldier(selectedPosition, position);
                game.selectItem(position);

                view.setSelection(selectedPosition, false);

                view.updateCell(selectedPosition, null);
                view.updateCell(position, game.getCell(position));

                view.setSelection(position, true);
            }
        }
    }
}

