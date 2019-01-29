package flow;

import flow.cells.CellItem;
import flow.cells.TownHall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame implements MainContract.View {

    private static final ImageIcon BULB_ICON = new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/bulb.png");

    private ActionListener actionListener;

    public static void main(String... args) {
        EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });

    }

    private MainContract.Presenter presenter;

    private JPanel layoutIndex;
    private JPanel root;
    private JPanel layoutHelp;
    private JLabel layoutHelpTitle;
    private JButton backHelpButton;
    private JLabel layoutIndexLogo = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/compass.png"));
    private JLabel layoutIndexTitle;
    private List<String> playerNames = new ArrayList<>();
    private JTextField player1;
    private JTextField player2;
    private JTextField player3;
    private JButton savePlayer;
    private JPanel layoutMap;
    private JPanel layoutTown;
    private JPanel layoutPlayers;
    private JLabel layoutRoundPoints;
    private JLabel layoutTownData;
    private JLabel layoutGameMessageTitle;
    private JLabel layoutGameMessage;
    private JLabel layoutCellDataTitle;
    private JLabel layoutCellData;
    private JButton helpButton;
    private JButton backButton;
    private JButton nextButton;
    private JPanel layoutBattle;
    private JLabel layoutBattleTitle;
    private JLabel battlePlayer1;
    private JLabel battlePlayer2;
    private JLabel battleCounterPlayer1 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/counter.gif"));
    private JLabel battleCounterPlayer2 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/counter.gif"));
    private int battlePointsPlayer1 = 0;
    private int battlePointsPlayer2 = 0;
    private JLabel winner;
    private boolean battleTurn = false;
    Integer randomNumberPlayer1 = random(1, 6);
    Integer randomNumberPlayer2 = random(1, 6);
    private JPanel layoutBattleDestroy;
    private JLabel destroyedPlayer;
    private JButton battleButton;


    public Main() {
        setTitle("The Settlers");
        setSize(1010, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        root = new JPanel();
        root.setLayout(null);
        add(root);

        layoutMap = new JPanel();
        layoutMap.setBounds(10, 0, 780, 780);
        layoutMap.setBackground(new Color(124, 163, 151));
        layoutMap.setLayout(null);
        root.add(layoutMap);

        layoutTown = new JPanel();
        layoutTown.setBounds(10, 0, 780, 780);
        layoutTown.setBackground(new Color(124, 163, 151));
        layoutTown.setLayout(null);
        layoutTown.setVisible(false);
        root.add(layoutTown);

        layoutPlayers = new JPanel();
        layoutPlayers.setBounds(800, 0, 200, 100);
        layoutPlayers.setBackground(new Color(179, 207, 184));
        layoutPlayers.setLayout(new GridLayout(3, 1));
        layoutPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        layoutPlayers.setForeground(Color.white);
        layoutPlayers.setOpaque(true);
        root.add(layoutPlayers);

        layoutRoundPoints = new JLabel();
        layoutRoundPoints.setBounds(800, 110, 200, 50);
        layoutRoundPoints.setBorder(BorderFactory.createLineBorder(new Color(73, 102, 109), 6));
        layoutRoundPoints.setLayout(null);
        layoutRoundPoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        root.add(layoutRoundPoints);

        layoutTownData = new JLabel();
        layoutTownData.setBounds(800, 170, 200, 110);
        layoutTownData.setBorder(BorderFactory.createLineBorder(new Color(73, 102, 109), 6));
        layoutTownData.setLayout(null);
        root.add(layoutTownData);

        layoutCellData = new JLabel();
        layoutCellData.setBounds(800, 310, 200, 180);
        layoutCellData.setBorder(BorderFactory.createLineBorder(new Color(73, 102, 109), 6));
        layoutCellData.setText("Cell data");
        layoutCellData.setLayout(null);
        root.add(layoutCellData);

        layoutCellDataTitle = new JLabel();
        layoutCellDataTitle.setBounds(800, 285, 200, 20);
        layoutCellDataTitle.setFont(new Font("Arial", Font.BOLD, 20));
        layoutCellDataTitle.setText("Cell data");
        layoutCellDataTitle.setLayout(null);
        root.add(layoutCellDataTitle);

        layoutGameMessage = new JLabel();
        layoutGameMessage.setBounds(800, 515, 200, 110);
        layoutGameMessage.setBorder(BorderFactory.createLineBorder(new Color(73, 102, 109), 6));
        layoutGameMessage.setLayout(null);
        root.add(layoutGameMessage);

        layoutGameMessageTitle = new JLabel();
        layoutGameMessageTitle.setBounds(800, 490, 200, 25);
        layoutGameMessageTitle.setFont(new Font("Arial", Font.BOLD, 20));
        layoutGameMessageTitle.setText("Game message");
        layoutGameMessageTitle.setLayout(null);
        root.add(layoutGameMessageTitle);

        helpButton = new JButton();
        helpButton.setBounds(800, 630, 200, 40);
        helpButton.setLayout(null);
        helpButton.setIcon(BULB_ICON);
        helpButton.addActionListener(e -> {

            repaint();
            layoutMap.removeAll();
            layoutMap.add(layoutHelp);
            layoutHelp.setVisible(true);

        });
        root.add(helpButton);

        nextButton = new JButton();
        nextButton.setBounds(800, 680, 200, 40);
        nextButton.setLayout(null);
        nextButton.addActionListener(e -> {

            presenter.skipPlayer();
            repaint();

        });
        nextButton.setText("Next");
        root.add(nextButton);

        backButton = new JButton();
        backButton.setBounds(800, 730, 200, 40);
        backButton.setLayout(null);
        backButton.addActionListener(e -> {

            layoutTown.setVisible(false);
            layoutTown.removeAll();
            presenter.redrawTable();
            layoutMap.setVisible(true);


            repaint();

        });
        backButton.setText("Back");
        root.add(backButton);

        layoutIndex = new JPanel();
        layoutIndex.setBounds(0, 0, 1010, 800);
        layoutIndex.setBackground(new Color(179, 207, 184));
        layoutIndex.setLayout(null);
        layoutIndex.setOpaque(true);
        layoutIndexLogo.setBounds(50, 200, 500, 500);
        layoutIndexTitle = new JLabel("The Settlers");
        layoutIndexTitle.setBounds(250, 0, 500, 200);
        layoutIndexTitle.setFont(new Font("Times New Roman", Font.BOLD, 90));
        layoutIndex.add(layoutIndexTitle);
        layoutIndex.add(layoutIndexLogo);
        add(layoutIndex);

        player1 = new JTextField("Player 1");
        player1.setBounds(560, 330, 370, 50);
        layoutIndex.add(player1);

        player2 = new JTextField("Player 2");
        player2.setBounds(560, 390, 370, 50);
        layoutIndex.add(player2);


        player3 = new JTextField("Player 3");
        player3.setBounds(560, 450, 370, 50);
        layoutIndex.add(player3);

        savePlayer = new JButton("Start");
        savePlayer.setBounds(560, 510, 100, 50);
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

        layoutHelp = new JPanel();
        layoutHelp.setBounds(10, 0, 780, 780);
        layoutHelp.setBackground(new Color(124, 163, 151));
        layoutHelp.setLayout(null);
        layoutHelpTitle = new JLabel("Help");
        layoutHelpTitle.setBounds(50, 0, 500, 200);
        layoutHelpTitle.setFont(new Font("Arial", Font.BOLD, 50));
        layoutHelpTitle.setForeground(Color.white);
        layoutHelp.add(layoutHelpTitle);

        backHelpButton = new JButton();
        backHelpButton.setBounds(800, 730, 200, 40);
        backHelpButton.setLayout(null);
        backHelpButton.addActionListener(e -> {

            presenter.init();

            remove(layoutHelp);
            add(root);
            presenter.redrawTable();
            layoutMap.setVisible(true);


            repaint();

        });
        backHelpButton.setText("Back");
        layoutHelp.add(backHelpButton);

        layoutBattle = new JPanel();
        layoutBattle.setBounds(10, 0, 780, 780);
        layoutBattle.setBackground(new Color(124, 163, 151));
        layoutBattle.setLayout(null);

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
        if (players == null) {
            return null;
        }
        for (String player : players) {
            playerNames.add(player);
        }

        return playerNames;
    }

    public void deletePlayers(Player opponent, List<Player> players) {
        for (int i = 0; i <= players.size(); i++) {
            players.remove(opponent);
        }
    }

    @Override
    public List<String> getPlayerNames() {
        return playerNames;
    }

    @Override
    public void showMap(CellItem[][] table) {
        layoutMap.removeAll();

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                JButton btn = new JButton();

                btn.setActionCommand(i + " " + j);
                btn.addActionListener(actionListener);
                btn.setBounds(j * 19 + 10, i * 19 + 10, 19, 19);
                btn.setFont(new Font("Arial", Font.PLAIN, 7));
                btn.setBackground(new Color(228, 228, 228));
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
                btn.setBackground(new Color(228, 228, 228));
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

    public void showBattle(Player player, Player opponent, List<Player> players, boolean isTable) {
        JLabel battleOnePlayer1 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/1.png"));
        JLabel battleTwoPlayer1 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/2.png"));
        JLabel battleThreePlayer1 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/3.png"));
        JLabel battleFourPlayer1 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/4.png"));
        JLabel battleFivePlayer1 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/5.png"));
        JLabel battleSixPlayer1 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/6.png"));
        JLabel battleOnePlayer2 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/1.png"));
        JLabel battleTwoPlayer2 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/2.png"));
        JLabel battleThreePlayer2 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/3.png"));
        JLabel battleFourPlayer2 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/4.png"));
        JLabel battleFivePlayer2 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/5.png"));
        JLabel battleSixPlayer2 = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/6.png"));

        layoutMap.removeAll();
        layoutMap.add(layoutBattle);
        layoutBattle.setVisible(true);
        layoutBattle.setLayout(null);
        layoutBattleTitle = new JLabel("Battle");
        layoutBattleTitle.setBounds(250, 0, 500, 200);
        layoutBattleTitle.setFont(new Font("Arial", Font.BOLD, 90));
        layoutBattleTitle.setForeground(Color.white);
        layoutBattle.add(layoutBattleTitle);
        battlePlayer1 = new JLabel(player.getName());
        battlePlayer1.setBounds(50, 300, 300, 100);
        battlePlayer1.setFont(new Font("Arial", Font.PLAIN, 50));
        battlePlayer1.setForeground(Color.white);
        battlePlayer2 = new JLabel(opponent.getName());
        battlePlayer2.setBounds(515, 300, 300, 100);
        battlePlayer2.setFont(new Font("Arial", Font.PLAIN, 50));
        battlePlayer2.setForeground(Color.white);
        battleCounterPlayer1.setBounds(20, 400, 250, 250);
        battleCounterPlayer1.setVisible(true);
        layoutBattle.add(battleCounterPlayer1);
        battleCounterPlayer2.setBounds(485, 400, 250, 250);
        battleCounterPlayer2.setVisible(true);
        layoutBattle.add(battleCounterPlayer2);
        battleButton = new JButton(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/swords.png"));
        battleButton.setBounds(350, 500, 50, 50);
        layoutBattle.add(battleButton);
        layoutBattle.add(battlePlayer1);
        layoutBattle.add(battlePlayer2);
        /*
        layoutBattle.add(battleButton);
        JButton battleButtonRefresh = new JButton(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/refresh.png"));
        battleButtonRefresh.setBounds(350, 500, 50, 50);
        */
        boolean finished = false;


        battleButton.addActionListener(e -> {

            if (!battleTurn) {
                randomNumberPlayer1 = random(1, 6);
                randomNumberPlayer2 = random(1, 6);
                battleButton.setIcon(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/refresh.png"));
                battleCounterPlayer1.setVisible(false);
                battleCounterPlayer2.setVisible(false);
                if (randomNumberPlayer1 == 1) {
                    battleOnePlayer1.setVisible(true);
                    battleOnePlayer1.setBounds(20, 400, 250, 250);
                    layoutBattle.add(battleOnePlayer1);
                } else if (randomNumberPlayer1 == 2) {
                    battleTwoPlayer1.setVisible(true);
                    battleTwoPlayer1.setBounds(20, 400, 250, 250);
                    layoutBattle.add(battleTwoPlayer1);
                } else if (randomNumberPlayer1 == 3) {
                    battleThreePlayer1.setVisible(true);
                    battleThreePlayer1.setBounds(20, 400, 250, 250);
                    layoutBattle.add(battleThreePlayer1);
                } else if (randomNumberPlayer1 == 4) {
                    battleFourPlayer1.setVisible(true);
                    battleFourPlayer1.setBounds(20, 400, 250, 250);
                    layoutBattle.add(battleFourPlayer1);
                } else if (randomNumberPlayer1 == 5) {
                    battleFivePlayer1.setVisible(true);
                    battleFivePlayer1.setBounds(20, 400, 250, 250);
                    layoutBattle.add(battleFivePlayer1);
                } else if (randomNumberPlayer1 == 6) {
                    battleSixPlayer1.setVisible(true);
                    battleSixPlayer1.setBounds(20, 400, 250, 250);
                    layoutBattle.add(battleSixPlayer1);
                }

                if (randomNumberPlayer2 == 1) {
                    battleOnePlayer2.setVisible(true);
                    battleOnePlayer2.setBounds(485, 400, 250, 250);
                    layoutBattle.add(battleOnePlayer2);
                } else if (randomNumberPlayer2 == 2) {
                    battleTwoPlayer2.setVisible(true);
                    battleTwoPlayer2.setBounds(485, 400, 250, 250);
                    layoutBattle.add(battleTwoPlayer2);
                } else if (randomNumberPlayer2 == 3) {
                    battleThreePlayer2.setVisible(true);
                    battleThreePlayer2.setBounds(485, 400, 250, 250);
                    layoutBattle.add(battleThreePlayer2);
                } else if (randomNumberPlayer2 == 4) {
                    battleFourPlayer2.setVisible(true);
                    battleFourPlayer2.setBounds(485, 400, 250, 250);
                    layoutBattle.add(battleFourPlayer2);
                } else if (randomNumberPlayer2 == 5) {
                    battleFivePlayer2.setVisible(true);
                    battleFivePlayer2.setBounds(485, 400, 250, 250);
                    layoutBattle.add(battleFivePlayer2);
                } else if (randomNumberPlayer2 == 6) {
                    battleSixPlayer2.setVisible(true);
                    battleSixPlayer2.setBounds(485, 400, 250, 250);
                    layoutBattle.add(battleSixPlayer2);
                }
                battleTurn = true;
            } else {

                battleButton.setIcon(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/swords.png"));
                battleOnePlayer1.setVisible(false);
                battleTwoPlayer1.setVisible(false);
                battleThreePlayer1.setVisible(false);
                battleFourPlayer1.setVisible(false);
                battleFivePlayer1.setVisible(false);
                battleSixPlayer1.setVisible(false);
                battleOnePlayer2.setVisible(false);
                battleTwoPlayer2.setVisible(false);
                battleThreePlayer2.setVisible(false);
                battleFourPlayer2.setVisible(false);
                battleFivePlayer2.setVisible(false);
                battleSixPlayer2.setVisible(false);
                battleCounterPlayer1.setVisible(true);
                layoutBattle.add(battleCounterPlayer1);
                battleCounterPlayer2.setVisible(true);
                layoutBattle.add(battleCounterPlayer2);

                if (randomNumberPlayer1 > randomNumberPlayer2) {
                    battlePointsPlayer1 += 1;
                } else if (randomNumberPlayer2 > randomNumberPlayer1) {
                    battlePointsPlayer2 += 1;
                } else {
                    battlePointsPlayer1 += 0;
                    battlePointsPlayer2 += 0;
                }

                if (battlePointsPlayer2 >= 3) {

                  battlePointsPlayer1 = 0;
                    battlePointsPlayer2 = 0;
                    layoutBattle.removeAll();

                    add(root);
                    root.add(layoutMap);
                    layoutMap.setVisible(true);
                    presenter.redrawTable();


                    battleButton.removeActionListener(battleButton.getActionListeners()[0]);
                    layoutBattle.remove(battleButton);

                    repaint();

                }
                if (battlePointsPlayer1 >= 3) {

                    battlePointsPlayer1 = 0;
                    battlePointsPlayer1 = 0;
                    battlePlayer1.removeAll();
                    battlePlayer2.removeAll();
                    battlePlayer1.setVisible(false);
                    battlePlayer2.setVisible(false);
                    layoutBattleDestroy = new JPanel();
                    layoutBattleDestroy.setBounds(10, 0, 780, 780);
                    layoutBattleDestroy.setBackground(new Color(124, 163, 151));
                    layoutBattleDestroy.setLayout(null);
                    remove(layoutBattle);
                    layoutBattle.setVisible(false);
                    layoutMap.add(layoutBattleDestroy);
                    destroyedPlayer = new JLabel(opponent.getName());
                    destroyedPlayer.setBounds(0, 0, 100, 100);
                    layoutBattleDestroy.setVisible(true);
                    layoutBattleDestroy.add(destroyedPlayer);
                    JLabel destroyedTown = new JLabel(new ImageIcon("/Users/vrgbrg/FlowAcademy/Java/TheSettlers/src/main/resources/destroy.png"));
                    destroyedPlayer.setBounds(200, 0, 500, 200);
                    destroyedPlayer.setFont(new Font("Arial", Font.BOLD, 90));
                    destroyedPlayer.setForeground(Color.white);
                    destroyedTown.setBounds(75, 150, 600, 600);
                    layoutBattleDestroy.setVisible(true);
                    layoutBattleDestroy.add(destroyedTown);
                    layoutBattleDestroy.add(destroyedPlayer);
                    JButton destroyNext = new JButton("Continue");
                    destroyNext.setBounds(250, 700, 200, 50);
                    layoutBattleDestroy.add(destroyNext);

                    deletePlayers(opponent, players);

                    destroyNext.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            battlePointsPlayer1 = 0;
                            battlePointsPlayer1 = 0;
                            System.out.println("opponent3: " + opponent);
                            layoutBattleDestroy.removeAll();
                            destroyNext.removeAll();
                            remove(layoutBattleDestroy);
                            destroyedPlayer.removeAll();
                            destroyedPlayer.setVisible(false);

                            add(root);
                            root.add(layoutMap);
                            layoutMap.setVisible(true);
                            presenter.redrawTable();
                            presenter.cellOwner(opponent);
                            battleButton.removeActionListener(battleButton.getActionListeners()[0]);
                            layoutBattle.remove(battleButton);
                            repaint();

                            gameOver(players);

                        }
                    });


                    repaint();

                }

                battleTurn = false;
            }


        });

    }

    @Override
    public void setSelection(Position position, boolean selection) {
        Component component = layoutMap.getComponent(position.x * 40 + position.y);
        ((JButton) component).setBorder(BorderFactory.createLineBorder(new Color(179, 207, 184)));
    }

    @Override
    public void updateCell(Position position, CellItem cell) {
        JButton btn = (JButton) layoutMap.getComponent(position.x * 40 + position.y);
        btn.setIcon(cell != null ? cell.layoutImage() : null);

    }

    @Override
    public void updateCellDestroy(Position position, CellItem cell) {
        if (cell instanceof TownHall) {
            JButton btn = (JButton) layoutMap.getComponent(position.x * 40 + position.y);
            btn.setIcon(null);
        }
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
                component.setBackground(new Color(124, 163, 151));
            } else {
                component.setBackground(null);
            }
        }

        layoutPlayers.repaint();
    }

    public void showCurrentCityData(Player player) {
        layoutTownData.setText("<html>" + "<b>" + "</b>" + "<b>" + player.getName() + "</b>" + "<b>" + "<br> " + "Gold: " + "</b>" + player.getGold() + "<br>" + "<b>" + "Population: " + "</b>" + String.valueOf(player.getActualPopulation() + " / " + String.valueOf(presenter.getCurrentPlayerPeopleStorage()) + "<br>" + "<b>" + "Born: " + "</b>" + presenter.populationBorn(player) + "<br>" + "<b>" + "Death: " + "</b>" + presenter.populationDeath(player) + "<br>" + "<b>" + "Tax: " + "</b>" + presenter.taxCalculator(player) + "</html>"));
        layoutTownData.repaint();
    }

    public void showCurrentPlayerRoundPoints(Player player) {
        layoutRoundPoints.setText("RoundPoints: " + String.valueOf(player.getRoundPoint()));
        repaint();
    }

    @Override
    public void showGameMessage(String text) {
        layoutGameMessage.setText(text);
        System.out.println(text);
    }

    public void showCellData(String text) {
        layoutCellData.setText(text);
    }

    @Override
    public void showSelectionCellData(Position position, boolean selection) {
       /* Component component = layoutMap.getComponent(position.x * 40 + position.y);

        layoutGameMessage.setText(component.toString());
        */
    }

    @Override
    public void highlightPlayer(List<Position> positions) {
        for (Position position : positions) {
            JButton btn = (JButton) layoutMap.getComponent(position.x * 40 + position.y);

            btn.setBackground(Color.CYAN);
        }
    }

    @Override
    public void highlightRange(Range range, Position center) {
        for (int i = range.topLeft.x; i <= range.bottomRight.x; i++) {
            for (int j = range.topLeft.y; j <= range.bottomRight.y; j++) {
                if (i > -3 && j > -3 && i < 40 && j < 40) {
                    int index = i * 40 + j;
                    ((JButton) layoutMap.getComponent(index))
                            .setBorder(BorderFactory.createLineBorder(new Color(179, 207, 184)));
                }
            }
        }
    }

    @Override
    public void highlightTownHall(Range range, Position center, boolean isTable) {
        for (int i = range.topLeft.x; i <= range.bottomRight.x; i++) {
            for (int j = range.topLeft.y; j <= range.bottomRight.y; j++) {
                if (i > -5 && j > -5 && i < 40 && j < 40) {
                    int index = i * 40 + j;
                    ((JButton) layoutMap.getComponent(index))
                            .setBorder(BorderFactory.createLineBorder(new Color(73, 102, 109)));
                }
            }
        }
        repaint();
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

        btn.setBackground(highlight ? Color.PINK : new Color(228, 228, 228));
    }

    private int random(int min, int max) {
        int random = (int) (Math.random() * (max - min) + 1 + min);
        return random;
    }

    public void gameOver(List<Player> players) {
        Player p = null;

        for(Player player: players){
            p= player;
        }

        if (players.size() == 1) {
            JPanel layoutGameOver = new JPanel();
            layoutGameOver.setBounds(0, 0, 1010, 800);
            layoutGameOver.setBackground(new Color(179, 207, 184));
            layoutGameOver.setLayout(null);
            layoutGameOver.setOpaque(true);
            layoutMap.removeAll();
            layoutGameOver.setBounds(0, 0, 1010, 800);
            layoutGameOver.setBackground(new Color(124, 163, 151));
            layoutGameOver.setLayout(null);
            layoutGameOver.setOpaque(true);
            layoutMap.add(layoutGameOver);
            JLabel theWinner = new JLabel("THE WINNER IS: " + p.getName());
            theWinner.setBounds(100, 300, 700, 200);
            theWinner.setForeground(Color.white);
            theWinner.setFont(new Font("Arial", Font.BOLD, 50));
            layoutGameOver.add(theWinner);
        }
    }

}