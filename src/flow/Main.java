package flow;

import flow.cells.CellItem;
import flow.cells.TownHall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class Main extends JFrame implements MainContract.View {

    private ActionListener actionListener;

    public static void main(String... args) {
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    private MainContract.Presenter presenter;

    private JPanel layoutButtons;
    private JPanel layoutPlayers;
    private JPanel sideBar;
    private JLabel currentPlayerGoldLabel;
    private JLabel roundPointLabel;
    private JLabel citizenLabel;

    public Main() {
        setTitle("The Settlers");
        setSize(1400, 750);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setLayout(null);
        add(root);

        layoutButtons = new JPanel();
        layoutButtons.setBounds(0, 0, 725, 750);
        layoutButtons.setBackground(Color.gray);
        layoutButtons.setLayout(null);
        root.add(layoutButtons);

        sideBar = new JPanel();
        sideBar.setBounds(740, 0, 200, 750);
        sideBar.setBackground(Color.gray);
        sideBar.setLayout(new GridLayout(5, 1));
        root.add(sideBar);

        layoutPlayers = new JPanel();
        layoutPlayers.setBounds(0, 0, 100, 100);
        layoutPlayers.setBackground(Color.lightGray);
        layoutPlayers.setLayout(new GridLayout(3, 1));
        sideBar.add(layoutPlayers);

        roundPointLabel = new JLabel();
        roundPointLabel.setBounds(0, 110, 100, 100);
        roundPointLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        roundPointLabel.setBackground(Color.lightGray);
        sideBar.add(roundPointLabel);

        citizenLabel = new JLabel();
        citizenLabel.setBounds(0, 220, 100, 100);
        citizenLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        citizenLabel.setBackground(Color.lightGray);
        sideBar.add(citizenLabel);

        currentPlayerGoldLabel = new JLabel();
        currentPlayerGoldLabel.setBounds(0, 330, 100, 100);
        currentPlayerGoldLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        currentPlayerGoldLabel.setBackground(Color.lightGray);
        sideBar.add(currentPlayerGoldLabel);

        actionListener = e -> {

            String[] s = e.getActionCommand().split(" ");

            int x = Integer.valueOf(s[0]);
            int y = Integer.valueOf(s[1]);

            presenter.onTableItemClicked(new Position(x, y));

            repaint();
        };

        repaint();

        presenter = new MainPresenter(this);

    }

    @Override
    public void showTable(CellItem[][] table) {
        layoutButtons.removeAll();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                JButton btn = new JButton();

                btn.setActionCommand(i + " " + j);
                btn.addActionListener(actionListener);
                btn.setBounds(j * 14 + 12, i * 14 + 12, 14, 14);
                btn.setFont(new Font("Arial", Font.PLAIN, 7));
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setBorder(BorderFactory.createLineBorder(Color.white));
                layoutButtons.add(btn);

                btn.setOpaque(true);

                CellItem cells = table[i][j];
                if (cells != null) {
                    btn.setText(cells.toString());
                }

            }

        }
    }

    @Override
    public void setSelection(Position position, boolean selection) {
        Component component = layoutButtons.getComponent(position.x * 50 + position.y);

        ((JButton)component).setBorder(BorderFactory.createLineBorder(Color.GREEN));
    }

    @Override
    public void updateCell(Position position, CellItem cell) {
        JButton btn = (JButton) layoutButtons.getComponent(position.x * 50 + position.y);

        btn.setText(cell != null ? cell.toString() : null);
    }

    @Override
    public int selectFromList(String[] list) {
        return JOptionPane.showOptionDialog(
                null,
                "Select",
                "Action",
                0,
                JOptionPane.PLAIN_MESSAGE,
                null,
                list,
                null);
    }

    @Override
    public int selectFromBuildings(String[] list) {
        return JOptionPane.showOptionDialog(
                null,
                "Select",
                "Buildings",
                0,
                JOptionPane.PLAIN_MESSAGE,
                null,
                list,
                null);
    }

    public int selectFromTownHall(String[] list) {
        return JOptionPane.showOptionDialog(
                null,
                "Select",
                "TownHall",
                0,
                JOptionPane.PLAIN_MESSAGE,
                null,
                list,
                null);
    }

    @Override
    public void showPlayers(List<Player> players) {
        layoutPlayers.removeAll();

        for (Player player : players) {
            layoutPlayers.add(new Label(player.getName()));
        }

    }

    @Override
    public void selectCurrentPlayer(Player player) {
        for (int i = 0; i < layoutPlayers.getComponentCount(); i++) {
            Label component = (Label) layoutPlayers.getComponent(i);

            if (component.getText().equals(player.getName())) {
                component.setBackground(Color.GREEN);
            } else {
                component.setBackground(null);
            }
        }

        layoutPlayers.repaint();
    }

    public void showCurrentPlayerGold(Player player) {
        currentPlayerGoldLabel.setText("Gold: " + String.valueOf(player.getGold()));
        player.getGold();
        currentPlayerGoldLabel.repaint();
    }

    public void showCurrentPlayerPoints(Player player) {
        roundPointLabel.setText("RoundPoints: " + String.valueOf(player.getRoundPoint()));
        player.getRoundPoint();
    }

    public void showCitizenNumber(Player player) {
        citizenLabel.setText("Population: " +String.valueOf(player.getActualPopulation()));
        citizenLabel.repaint();
    }

    @Override
    public void highlightRange(Range range, Position center) {
        for (int i = range.topLeft.x; i <= range.bottomRight.x; i++) {
            for (int j = range.topLeft.y; j <= range.bottomRight.y; j++) {
                if (center == null ||
                        center.x == i || center.y == j) {
                    int index = i * 50 + j;
                    ((JButton) layoutButtons.getComponent(index))
                            .setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }
        }
    }

    @Override
    public void highlightTownHall(Range range, Position center) {
        for (int i = range.topLeft.x; i <= range.bottomRight.x; i++) {
            for (int j = range.topLeft.y; j <= range.bottomRight.y; j++) {
                if (center == null ||
                        center.x == i || center.y == j) {
                    int index = i * 50 + j;
                    ((JButton) layoutButtons.getComponent(index))
                            .setBorder(BorderFactory.createLineBorder(Color.red));
                }
            }
        }
    }

    @Override
    public void removeHighlight() {
        for (int i = 0; i < layoutButtons.getComponentCount(); i++) {
            ((JButton) layoutButtons.getComponent(i))
                    .setBorder(BorderFactory.createLineBorder(Color.white));
        }
    }

    public void highlightAttackableItem(Position position, boolean highlight) {
        JButton btn = (JButton) layoutButtons.getComponent(position.x * 50 + position.y);

        btn.setBackground(highlight ? Color.PINK : Color.LIGHT_GRAY);
    }
}