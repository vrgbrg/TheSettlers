package flow;

import flow.cells.CellItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame implements MainContract.View {

    private static final ImageIcon SWORD_ICON = new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/resources/swords.png");

    private ActionListener actionListener;

    public static void main(String... args) {
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }

    private MainContract.Presenter presenter;

    private JPanel layoutIndex;
    List<String> playerNames = new ArrayList<>();
    private JTextField player1;
    private JTextField player2;
    private JTextField player3;
    private JButton savePlayer;
    private JPanel layoutMap;
    private JPanel layoutTown;
    private JPanel layoutPlayers;
    private JLabel layoutRoundPoints;
    private JLabel layoutTownData;
    private JLabel layoutGameText;
    private JButton attack;
    private JButton back;
    private JButton next;


    public Main() {
        setTitle("The Settlers");
                setSize(1010, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setLayout(null);
        add(root);


        layoutMap = new JPanel();
        layoutMap.setBounds(10, 0, 780, 780);
        layoutMap.setBackground(new Color(124,163,151));
        layoutMap.setLayout(null);
        root.add(layoutMap);

        layoutTown = new JPanel();
        layoutTown.setBounds(10, 0, 780, 780);
        layoutTown.setBackground(new Color(124,163,151));
        layoutTown.setLayout(null);
        layoutTown.setVisible(false);
        root.add(layoutTown);

        layoutPlayers = new JPanel();
        layoutPlayers.setBounds(800, 0, 200, 100);
        layoutPlayers.setBackground(new Color(179, 207, 184));
        layoutPlayers.setLayout(new GridLayout(3,1));
        layoutPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        layoutPlayers.setForeground(Color.white);
        layoutPlayers.setOpaque(true);
        root.add(layoutPlayers);

        layoutRoundPoints = new JLabel();
        layoutRoundPoints.setBounds(800, 110, 200, 50);
        layoutRoundPoints.setBorder(BorderFactory.createLineBorder(new Color(73, 102,109), 6));
        layoutRoundPoints.setLayout(null);
        layoutRoundPoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        root.add(layoutRoundPoints);

        layoutTownData = new JLabel();
        layoutTownData.setBounds(800, 170, 200, 100);
        layoutTownData.setBorder(BorderFactory.createLineBorder(new Color(73, 102,109), 6));
        layoutTownData.setLayout(null);
        root.add(layoutTownData);

        layoutGameText = new JLabel();
        layoutGameText.setBounds(800,280,200,300);
        layoutGameText.setBorder(BorderFactory.createLineBorder(new Color(73, 102,109), 6));
        layoutGameText.setText("Teszt");
        layoutGameText.setLayout(null);
        root.add(layoutGameText);


        attack = new JButton();
        attack.setBounds(800,590,200,40);
        attack.setLayout(null);
        attack.setIcon(SWORD_ICON);
        attack.addActionListener(e -> {


            repaint();

        });
        root.add(attack);

        next = new JButton();
        next.setBounds(800,640,200,40);
        next.setLayout(null);
        next.addActionListener(e -> {

            presenter.skipPlayer();
            repaint();

        });
        next.setText("Next");
        root.add(next);

        back = new JButton();
        back.setBounds(800,690,200,40);
        back.setLayout(null);
        back.addActionListener(e -> {

            layoutTown.setVisible(false);
            presenter.redrawTable();
            layoutMap.setVisible(true);


            repaint();

        });
        back.setText("Back");
        root.add(back);


        layoutIndex = new JPanel();
        layoutIndex.setBounds(0,0, 1010, 800);
        layoutIndex.setBackground(new Color(179, 207, 184));
        layoutIndex.setLayout(null);
        layoutIndex.setOpaque(true);
        add(layoutIndex);

        player1 = new JTextField();
        player1.setBounds(500,100,300,50);
        layoutIndex.add(player1);

        player2 = new JTextField();
        player2.setBounds(500,160,300,50);
        layoutIndex.add(player2);

        player3 = new JTextField();
        player3.setBounds(500,220,300,50);
        layoutIndex.add(player3);

        savePlayer = new JButton("Start");
        savePlayer.setBounds(500,280,100,50);
        savePlayer.addActionListener(e -> {
           savePlayers(player1.getText(), player2.getText(), player3.getText());

            presenter.init();

            remove(layoutIndex);
            add(root);
            presenter.redrawTable();
            layoutMap.setVisible(true);

            repaint();
        });
        layoutIndex.add(savePlayer);


        actionListener = e -> {

            String[] s = e.getActionCommand().split(" ");

            int x = Integer.valueOf(s[0]);
            int y = Integer.valueOf(s[1]);

            presenter.onTableItemClicked(new Position(x, y), true);

            repaint();

        };

        repaint();

        presenter = new MainPresenter(this);

    }

    public List<String> savePlayers(String... players) {
        if(players == null) {
            return null;
        }
        for(String player: players) {
            playerNames.add(player);
        }

        return playerNames;
    }

    @Override
    public List<String> getPlayerNames() {
        return playerNames;
    }

    @Override
    public void showTable(CellItem[][] table) {
        layoutMap.removeAll();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                JButton btn = new JButton();

                btn.setActionCommand(i + " " + j);
                btn.addActionListener(actionListener);
                btn.setBounds(j * 19 + 10, i * 19 + 10, 19, 19);
                btn.setFont(new Font("Arial", Font.PLAIN, 7));
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setBorder(BorderFactory.createLineBorder(Color.white));
                layoutMap.add(btn);

                btn.setOpaque(true);

                CellItem cells = table[i][j];
                if (cells != null) {
                    btn.setIcon(cells.layoutImage());
                }

            }

        }
    }

    public void showTownTable(CellItem[][] townTable) {
        layoutMap.removeAll();
        layoutTown.setVisible(true);
        for (int i = 0; i < townTable.length; i++) {
            for (int j = 0; j < townTable[i].length; j++) {
                JButton btn = new JButton();

                btn.setActionCommand(i + " " + j);
                btn.addActionListener(e -> {

                    String[] s = e.getActionCommand().split(" ");

                    int x = Integer.valueOf(s[0]);
                    int y = Integer.valueOf(s[1]);

                    presenter.onTableItemClicked(new Position(x, y), false);

                    repaint();

                });
                btn.setBounds(j * 69 + 10, i * 69 + 10, 69, 69);
                btn.setFont(new Font("Arial", Font.PLAIN, 7));
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setBorder(BorderFactory.createLineBorder(Color.white));
                layoutTown.add(btn);

                btn.setOpaque(true);

                CellItem cells = townTable[i][j];
                if (cells != null) {
                    btn.setIcon(cells.layoutImage());
                }

            }

        }
        layoutMap.setVisible(false);
    }


    @Override
    public void setSelection(Position position, boolean selection) {
        Component component = layoutMap.getComponent(position.x * 40 + position.y);

        ((JButton)component).setBorder(BorderFactory.createLineBorder(Color.GREEN));
    }

    @Override
    public void selectionText(Position position, boolean selection) {
        Component component = layoutMap.getComponent(position.x * 40 + position.y);

        layoutGameText.setText(component.toString());
    }

    @Override
    public void updateCell(Position position, CellItem cell) {
        JButton btn = (JButton) layoutMap.getComponent(position.x * 40 + position.y);

        btn.setIcon(cell != null ? cell.layoutImage() : null);
    }

    @Override
    public void updateTownCell(Position position, CellItem cell) {
        JButton btn = (JButton) layoutTown.getComponent(position.x * 11 + position.y);

        btn.setIcon(cell != null ? cell.layoutImage() : null);
    }

    @Override
    public int selectFromList(String[] list) {
        return JOptionPane.showOptionDialog(
                null,
                null,
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
                null,
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
                null,
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
                component.setBackground(new Color(124,163,151 ));
            } else {
                component.setBackground(null);
            }
        }

        layoutPlayers.repaint();
    }

    public void showCurrentCityData(Player player) {
        layoutTownData.setText("<html>" + "<b>" + "</b>" +"<b>" + player.getName() + "</b>" + "<b>" + "<br> "+ "Gold: " + "</b>" + String.valueOf(player.getGold())+ "<br>" + "<b>" + "Population: " + "</b>" + String.valueOf(player.getActualPopulation() + "</html>"));
        player.getGold();
        layoutTownData.repaint();
    }

    public void showCurrentPlayerPoints(Player player) {
        layoutRoundPoints.setText("RoundPoints: " + String.valueOf(player.getRoundPoint()));
        player.getRoundPoint();
    }

    public void showCurrentCell(Player player, CellItem cell) {
        layoutGameText.setText("Cell data: " + cell.getOwner().toString());
        cell.getOwner().toString();
    }


    @Override
    public void highlightRange(Range range, Position center) {
        for (int i = range.topLeft.x; i <= range.bottomRight.x; i++) {
            for (int j = range.topLeft.y; j <= range.bottomRight.y; j++) {
                if (center == null ||
                        center.x == i || center.y == j) {
                    int index = i * 40 + j;
                    ((JButton) layoutMap.getComponent(index))
                            .setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }
        }
    }

    @Override
    public void highlightTownHall(Range range, Position center, boolean isTable) {
        for (int i = range.topLeft.x; i <= range.bottomRight.x; i++) {
            for (int j = range.topLeft.y; j <= range.bottomRight.y; j++) {
                if (center == null ||
                        center.x == i || center.y == j) {
                    int index = i * 40 + j;
                    ((JButton) layoutMap.getComponent(index))
                            .setBorder(BorderFactory.createLineBorder(Color.red));
                }
            }
        }
    }

    @Override
    public void removeHighlight() {
        for (int i = 0; i < layoutMap.getComponentCount(); i++) {
            ((JButton) layoutMap.getComponent(i))
                    .setBorder(BorderFactory.createLineBorder(Color.white));
        }
    }

    public void highlightAttackableItem(Position position, boolean highlight) {
        JButton btn = (JButton) layoutMap.getComponent(position.x * 40 + position.y);

        btn.setBackground(highlight ? Color.PINK : Color.LIGHT_GRAY);
    }
}